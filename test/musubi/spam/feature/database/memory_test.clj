(ns musubi.spam.feature.database.memory-test
  (:use [clojure.test])
  (:require (musubi.spam.feature.database [memory :refer :all])
            [musubi.spam.feature :refer (new-feature)]
            [midje.sweet :refer :all]))

(fact
  (against-background (before :checks clear-feature-db))
  (let [f (new-feature "viagra")]
    (store f) => f
    (lookup "viagra") => f))

