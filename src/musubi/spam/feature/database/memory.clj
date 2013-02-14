(ns musubi.spam.feature.database.memory
  (:require [musubi.spam.feature :refer (id persistance)]))

(def feature-db (atom {}))

(def training-db (atom {}))

(defn inc-spams []
  (swap! training-db update-in [:total-spams] (fnil inc 0)))

(defn inc-hams []
  (swap! training-db update-in [:total-hams] (fnil inc 0)))


(defn clear-feature-db []
  (reset! feature-db {}))

(defn clear-training-db []
  (reset! feature-db {}))

(defn lookup [id]
  (get @feature-db id))

(defn store [feature]
  (and 
    (swap! feature-db assoc (id feature) feature)
    (lookup (id feature))))

(defn inc-totals [type]
  (condp = type 
    :ham (inc-hams)
    :spam (inc-spams)
    nil))

(defmethod persistance :lookup [f id] 
  (lookup id))

(defmethod persistance :store [f feature] 
  (store feature))

(defmethod persistance :ham [type] 
  (inc-hams))

(defmethod persistance :spam [type] 
  (inc-spams))

