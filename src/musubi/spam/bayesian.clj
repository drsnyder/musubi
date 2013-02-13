(ns musubi.spam.bayesian)

(def config (atom {:max-ham-score 0.4
                   :min-spam-score 0.6}))

(declare score)


(defn classification [score]
  (cond 
    (<= score (:max-ham-score @config)) :ham
    (>= score (:min-spam-score @config)) :spam
    :else :unsure))

(defn classify [store-fn text]
  (classification (score (extract-features store-fn text))))


