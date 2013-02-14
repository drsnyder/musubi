(ns musubi.spam.bayesian
  (:require [musubi.spam.feature :refer (inc-ham-score inc-spam-score)]
    [musubi.spam.feature.word :refer (extract-features)]))

(def config (atom {:max-ham-score 0.4
                   :min-spam-score 0.6}))

(declare score)

(defn classification [score]
  (cond 
    (<= score (:max-ham-score @config)) :ham
    (>= score (:min-spam-score @config)) :spam
    :else :unsure))

(defn classify [text]
  (classification (score (extract-features text))))

(defn increment-count 
  [type feature store]
  (condp = type
    :ham (store (inc-ham-score feature))
    :spam (store (inc-spam-score feature))
    feature))

(defn train [type text persistance]
  (when-let [features (map #(increment-count type % (partial persistance :store))
                           (extract-features text persistance))]
    (persistance type)
    features))


  

