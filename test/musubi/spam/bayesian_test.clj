(ns musubi.spam.bayesian-test
  (:use [clojure.test])
  (:require (musubi.spam [bayesian :refer :all])
            [musubi.spam.feature :refer :all]
            [musubi.spam.feature.database.memory :refer :all]
            [midje.sweet :refer :all]))

(facts classificaton 
       (classification 0.6) => :spam
       (classification 0.4) => :ham
       (classification 0.5) => :unsure)

(facts train
       (against-background (before :checks clear-feature-db)
                           (before :checks clear-training-db))
       (train :spam "this is spam" persistance) => truthy)


(facts spam-probability
       (against-background (before :checks clear-feature-db)
                           (before :checks clear-training-db))
       (spam-probability (lookup "spam") 1 1) => 1
       (provided (lookup "spam") => (new-feature "spam" 1 0))
       (bayesian-spam-probability (lookup "spam") persistance) => 0.75
       (provided (lookup "spam") => (new-feature "spam" 1 0)
                 (persistance :total-spams) => 1
                 (persistance :total-hams) => 1))

(facts untrained?
       (untrained? (new-feature "spam")) => true
       (untrained? (new-feature "spam" 1 0)) => false
       (untrained? (new-feature "spam" 0 1)) => false
       (untrained? (new-feature "spam" 1 1)) => false)

