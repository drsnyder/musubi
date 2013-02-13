(ns musubi.spam.bayesian-test
  (:use [clojure.test])
  (:require (musubi.spam [bayesian :refer :all])
            [midje.sweet :refer :all]))

(facts classificaton 
       (classification 0.6) => :spam
       (classification 0.4) => :ham
       (classification 0.5) => :unsure)


