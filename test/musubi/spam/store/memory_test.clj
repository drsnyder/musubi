(ns musubi.spam.store.memory-test
  (:use [clojure.test])
  (:require [musubi.spam.store :refer :all]
            (musubi.spam.store [memory :refer :all])
            [midje.sweet :refer :all]))


(clear-db)

(facts storage
  (let [o {:something 1}]
    (db-store "key" o) => o
    (db-lookup "key") => o))

(clear-counters)

(facts counters
       (counter-inc :total-this) => 1
       (counter-inc :total-this) => 2)

(clear-db)
(clear-counters)

(facts storage
       (let [s (new-store)
             o {:other-thing 2}]
         (store s "key" o) => o
         (lookup s "key") => o))

(facts counters
       (let [s (new-store)]
         (inc-counter s :something-that-goes-up) => 1
         (get-counter s :something-that-goes-up) => 1
         (inc-counter s :something-that-goes-up) => 2
         (get-counter s :something-that-goes-up) => 2))
            
         




