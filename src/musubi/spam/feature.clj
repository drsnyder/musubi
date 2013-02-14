(ns musubi.spam.feature)

(defprotocol Feature
  "A spam feature abstraction."
  (id [this])
  (ham-score [this])
  (inc-ham-score [this])
  (spam-score [this])
  (inc-spam-score [this]))


(extend clojure.lang.PersistentHashMap
  Feature
  {:id             (fn [this] (:id this))
   :ham-score      (fn [this] (:ham-score this))
   :inc-ham-score  (fn [this] (update-in this [:ham-score] inc))
   :spam-score     (fn [this] (:spam-score this))
   :inc-spam-score (fn [this] (update-in this [:spam-score] inc))})


(defn new-feature 
  ([word ham-score spam-score]
   (hash-map :id word :ham-score ham-score :spam-score spam-score))
  ([id]
   (new-feature id 0 0)))


(defmulti persistance (fn [x & [y]] x))
