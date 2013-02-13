(ns musubi.spam.feature.database.memory)

(def feature-db (atom {}))

(defn store [id feature]
  (swap! feature-db assoc id feature))

(defn fetch [id]
  (get @feature-db id))

