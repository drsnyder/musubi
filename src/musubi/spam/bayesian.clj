(ns musubi.spam.bayesian
  (:require [musubi.spam.feature :refer (spam-score ham-score inc-ham-score inc-spam-score)]
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

(defn spam-probability
  [feature total-spams total-hams]
  (let [spam-frequency (/ (spam-score feature) total-spams)
        ham-frequency (/ (ham-score feature) total-hams)]
    (/ spam-frequency
       (+ spam-frequency ham-frequency))))


(defn bayesian-spam-probability 
  [feature persistance &{:keys [assumed-probability weight] :or {assumed-probability 0.5 weight 1}}]
  (let [basic-probability (spam-probability feature (persistance :total-spams) (persistance :total-hams))
        data-points (+ (spam-score feature) (ham-score feature))]
    (/ (+ (* weight assumed-probability)
          (* data-points basic-probability))
       (+ weight data-points))))
  

(defn untrained? [feature]
  (and (= (spam-score feature) 0)
       (= (ham-score feature) 0)))

(defn accumulate-feature-stats
  [m spam-prob ham-prob]
  (-> m
      (update-in [:spam-probs] (fnil conj []) spam-prob)
      (update-in [:ham-probs] (fnil conj []) ham-prob)
      (update-in [:total] (fnil inc 0))))

(declare fisher)

(defn score [features persistance]
  (let [stats (reduce (fn [acc feature]
                        (when (not (untrained? feature))
                          (let [spam-prob (bayesian-spam-probability feature persistance)]
                            (accumulate-feature-stats acc spam-prob (- 1.0 spam-prob)))))
                      features)
        h (- 1 (fisher (:spam-probs stats) (:total stats)))
        s (- 1 (fisher (:ham-probs stats) (:total stats)))]
    (/ (+ (- 1 h) s) 2.0)))

(declare inverse-chi-square)

(defn fisher [probs total]
  (inverse-chi-square
    (* -2 (Math/log (reduce * probs)))
    (* 2 total)))

(defn inverse-chi-square [value degrees-of-freedom]
  (assert (even? degrees-of-freedom))
  (let [m (/ value 2.0)]
    (min
      (reduce +
        (reductions * (Math/exp (- m)) (for [i (range 1 (/ degrees-of-freedom 2))] (/ m i))))
      1.0)))

    
