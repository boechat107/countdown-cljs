(ns countdown.db
  (:require [clojure.spec :as s]))

;; spec of app-db
(s/def ::greeting string?)
(s/def ::counter (s/and integer? #(>= % 0)))
(s/def ::app-db
  (s/keys :req-un [::counter]))

;; initial state of app-db
(def app-db {:counter 10})
