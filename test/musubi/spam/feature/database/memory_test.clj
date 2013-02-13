(ns musubi.spam.feature.database.memory-test
  (:use [clojure.test])
  (:require (musubi.spam.feature [word :refer :all])
            (musubi.spam.feature.database [memory :refer :all])
            [midje.sweet :refer :all]))

(fact
  (let [f (new-feature "viagra")]
    ((store "viagra" f) "viagra") => f
    (fetch "viagra") => f))

