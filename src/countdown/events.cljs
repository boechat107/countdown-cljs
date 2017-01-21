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

(def dec-counter-event
  {:ms 1000 :dispatch [:dec-counter]})

;; Handles the start of the countdown by registering future effects with
;; (`:dispatch-later`) to decrease the db's counter. In addition, it changes
;; the running state to `true`.
(reg-event-fx
 :start-countdown
 (fn [{db :db :as cofx} _]
   ;; Effects are returned only if the countdown is not running.
   (when-not (:flag-running? db)
     {:db (assoc db :flag-running? true)
      :dispatch-later [dec-counter-event]})))

;; Updates the db's counter by decreasing its value and dispatching another
;; `:dec-counter` event, if the counter is still positive. When the counter
;; reaches zero, a `:stop-countdown` event is scheduled to be dispatched.
(reg-event-fx
 :dec-counter
 (fn [{db :db} _]
   (when (:flag-running? db)
     (let [counter (:counter db)]
       (if (pos? counter)
         {:db (assoc db :counter (dec counter))
          :dispatch-later [dec-counter-event]}
         {:dispatch [:stop-countdown]})))))

;; Updates `:flag-running?` to false.
(reg-event-db
 :stop-countdown
 (fn [db _]
   (assoc db :flag-running? false)))
