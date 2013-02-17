(ns musubi.spam.store.memory
  (:require [musubi.spam.store :refer :all]))

(def ^:private db (atom {}))

(def ^:private counters (atom {}))

(defn get-db []
  @db)

(defn clear-db []
  (reset! db {}))

(defn db-lookup [id]
  (get @db id))

(defn db-store [id feature]
  (swap! db assoc id feature)
  (db-lookup id))

(defn get-counters []
  @counters)

(defn clear-counters []
  (reset! counters {}))

(defn counter-lookup [id]
  (get @counters id 0))

(defn counter-inc [id]
  (swap! counters update-in [id] (fnil inc 0))
  (counter-lookup id))


(deftype MemoryStore []
  Store
  (store [this id object] 
    (db-store id object))
  (lookup [this id]
    (db-lookup id))
  (inc-counter [this id]
    (counter-inc id))
  (get-counter [this id]
    (counter-lookup id)))

(defn new-store []
  (MemoryStore.))

