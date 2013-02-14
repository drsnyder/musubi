(ns musubi.spam.feature.word
  (:require [musubi.spam.feature :refer :all]))

(defn extract-words
  "Naive word extraction using a regex."
  [text]
  (distinct (re-seq #"[a-zA-Z]{3,}" text)))

(defn extract-features 
  [text persistance]
  (map (fn [word]
         (if-let [feature (persistance :lookup word)]
           feature
           (persistance :store (new-feature word))))
       (extract-words text)))



