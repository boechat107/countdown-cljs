(ns countdown.subs
  (:require [re-frame.core :refer [reg-sub]]))

(reg-sub
 :get-counter
 (fn [db _] (:counter db)))

(reg-sub
 :get-running-flag
 (fn [db _] (:flag-running? db)))
