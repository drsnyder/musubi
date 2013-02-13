(ns musubi.spam.feature.word-test
  (:use [clojure.test])
  (:require (musubi.spam.feature [word :refer :all])
            (musubi.spam.feature.database [memory :refer (store fetch)])
            [midje.sweet :refer :all]))

(facts new
       (new-feature "viagra") => truthy
       (word (new-feature "test")) => "test"
       (spam-score (new-feature "test")) => 0
       (ham-score (new-feature "test")) => 0)

(facts extract-words 
       (extract-words "this has a few words") => (list "this" "has" "few" "words")
       (extract-words "this has a few words words") => (list "this" "has" "few" "words"))

(facts extract-features
       (extract-features store "this has a few words") => seq?
       (id (fetch "this")) => "this")

