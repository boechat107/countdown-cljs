(ns countdown.events
  (:require
   [re-frame.core :refer [reg-event-db reg-event-fx after]]
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
;; (`:dispatch-later`). These effects are events to decrease the db's counter.
(reg-event-fx
 :countdown-start
 (fn [cofx _]
   {:dispatch-later (map #(array-map :ms (* 1000 %)
                                     :dispatch [:dec-counter])
                         (range 1 11))}))

;; Updates the db's counter by decreasing its value.
(reg-event-db
 :dec-counter
 (fn [db _]
   (update db :counter dec)))
