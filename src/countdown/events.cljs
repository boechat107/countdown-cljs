(ns countdown.events
  (:require
   [re-frame.core :as rf :refer [reg-event-db reg-event-fx after]]
   [clojure.spec :as s]
   [countdown.db :as db :refer [app-db]]))

;; -- Interceptors ------------------------------------------------------------
;;
;; See https://github.com/Day8/re-frame/blob/master/docs/Interceptors.md
;;
(defn check-and-throw
  "Throw an exception if db doesn't have a valid spec."
  [spec db [event]]
  (when-not (s/valid? spec db)
    (let [explain-data (s/explain-data spec db)]
      (throw (ex-info (str "Spec check after " event " failed: " explain-data)
                      explain-data)))))

(def validate-spec
  (if goog.DEBUG
    (after (partial check-and-throw ::db/app-db))
    []))

;; ## Event Handlers

;; DB initialization, very simple.
(reg-event-db
 :initialize-db
 validate-spec
 (fn [_ _]
   app-db))

;; Handles the start of the countdown by registering future effects with
;; (`:dispatch-later`) to decrease the db's counter. In addition, it changes
;; the running state to `true`.
(reg-event-fx
 :countdown-start
 (fn [{db :db :as cofx} _]
   ;; Effects are returned only if the countdown is not running.
   (when-not (:flag-running? db)
     {:db (assoc db :flag-running? true)
      ;; Schedule the dispatching of multiple `:dec-counter` events and a single
      ;; one `:flag-running->false` at the end.
      :dispatch-later (let [last-mult (:counter db)]
                        (conj (map #(array-map :ms (* 1000 %)
                                               :dispatch [:dec-counter])
                                   (range 1 (inc last-mult)))
                              {:ms (* 1000 last-mult)
                               :dispatch [:flag-running->false]}))})))

;; Updates the db's counter by decreasing its value (but never less than zero).
(reg-event-db
 :dec-counter
 (fn [db _]
   (let [counter (:counter db)]
     (if (pos? counter)
       (assoc db :counter (dec counter))
       db))))

;; Updates `:flag-running?` to false.
(reg-event-db
 :flag-running->false
 (fn [db _]
   (assoc db :flag-running? false)))
