(ns musubi.spam.feature.word
  (:require [musubi.spam.feature :refer :all]
            [musubi.spam.store :refer :all]))

(defn extract-words
  "Naive word extraction using a regex."
  [text]
  (distinct (re-seq #"[a-zA-Z]{3,}" text)))

(defn extract-features 
  [^:musubi.spam.store s text]
  (map (fn [word]
         (if-let [feature (lookup s word)]
           feature
           (store s word (new-feature word))))
       (extract-words text)))



