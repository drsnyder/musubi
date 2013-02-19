(ns musubi.spam.bayesian-test
  (:use [clojure.test])
  (:require (musubi.spam [bayesian :refer :all] 
                         [feature :refer :all]
                         [store :refer :all])
            [musubi.spam.feature.word :refer (extract-features)]
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
         (train s "this is spam" :spam) => truthy
         (train s "this is ham" :ham) => truthy))

(facts spam-probability
       (spam-probability (new-feature "spam" 1 1) 1 1) => 1/2
       (bayesian-spam-probability (new-feature "spam" 1 1) 1 1) => 0.5)

(facts untrained?
       (untrained? (new-feature "spam")) => true
       (untrained? (new-feature "spam" 1 0)) => false
       (untrained? (new-feature "spam" 0 1)) => false
       (untrained? (new-feature "spam" 1 1)) => false)


(facts score
       (let [s (new-store)]
         (features->score (extract-features s "are you spammy?") 1 1) => 0.5
         (train s "viagra pills" :spam) => truthy
         (train s "headphones"   :ham) => truthy
         (features->score (extract-features s "a message with viagra") (get-counter s :total-spams) (get-counter s :total-hams)) => 0.75
         (score s "a message with viagra") => 0.75))


(clear-db)
(clear-counters)
