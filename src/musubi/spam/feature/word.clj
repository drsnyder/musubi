(ns musubi.spam.feature.word)


(defn new-feature 
  ([word ham-score spam-score]
   (hash-map :word word :ham-score ham-score :spam-score spam-score))
  ([word]
   (new-feature word 0 0)))

(defn spam-score [feature]
  (:spam-score feature))

(defn ham-score [feature]
  (:ham-score feature))

(defn word [feature]
  (:word feature))

(defn id [feature]
  (word feature))


(defn extract-words
  "Naive word extraction using a regex."
  [text]
  (distinct (re-seq #"[a-zA-Z]{3,}" text)))

(defn extract-features 
  [store-fn text]
  (map #(store-fn % (new-feature %)) (extract-words text)))


