(ns musubi.spam.feature.word-test
  (:use [clojure.test])
  (:require 
    [musubi.spam.feature :refer :all]
    [musubi.spam.feature.word :refer :all]
    [musubi.spam.store :refer :all]
    [musubi.spam.store.memory :refer :all]
    [midje.sweet :refer :all]))



(facts new
       (new-feature "viagra") => truthy
       (id (new-feature "test")) => "test"
       (spam-score (new-feature "test")) => 0
       (ham-score (new-feature "test")) => 0)

(facts adjusting
       (ham-score (inc-ham-score (new-feature "good"))) => 1
       (spam-score (inc-spam-score (new-feature "bad"))) => 1)

(facts extract-words 
       (extract-words "this has a few words") => (list "this" "has" "few" "words")
       (extract-words "this has a few words words") => (list "this" "has" "few" "words"))

(clear-db)
(clear-counters)

(facts extract-features
       (let [s (new-store)]
         (extract-features s "this has a few words") => seq?
         (lookup s "this") => truthy))

