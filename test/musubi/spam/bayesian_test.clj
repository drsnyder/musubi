(ns musubi.spam.bayesian-test
  (:use [clojure.test])
  (:require (musubi.spam [bayesian :refer :all] 
                         [feature :refer :all]
                         [store :refer :all])
            [musubi.spam.store.memory :refer :all]
            [midje.sweet :refer :all]))

(facts classificaton 
       (classification 0.6) => :spam
       (classification 0.4) => :ham
       (classification 0.5) => :unsure)

(facts increment-count
       (let [s (new-store)
             f (new-feature "spam")]
         (increment-count s :spam f) => (inc-spam-score f)))

(clear-db)
(clear-counters)

(facts train
       (let [s (new-store)]
         (train s "this is spam" :spam) => truthy))

(clear-db)
(clear-counters)


(facts spam-probability
       (let [s (new-store)]
         (spam-probability (new-feature "spam") 1 1) => 1
         (bayesian-spam-probability s (new-feature "spam")) => 0.75
         (provided (get-counter s :total-spams) => 1
                   (get-counter s :total-hams) => 1)))

(facts untrained?
       (untrained? (new-feature "spam")) => true
       (untrained? (new-feature "spam" 1 0)) => false
       (untrained? (new-feature "spam" 0 1)) => false
       (untrained? (new-feature "spam" 1 1)) => false)

