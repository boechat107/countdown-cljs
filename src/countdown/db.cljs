(ns countdown.db
  (:require [clojure.spec :as s]))

;; ## Spec of db

(s/def ::counter (s/and integer? #(>= % 0)))
(s/def ::flag-running? boolean?)
(s/def ::app-db
  (s/keys :req-un [::counter ::flag-running?]))

;; initial state of app-db
(def app-db {:counter 10
             :flag-running? false})
