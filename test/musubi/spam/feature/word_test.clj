(ns musubi.spam.feature.word-test
  (:use [clojure.test])
  (:require 
    [musubi.spam.feature :refer :all]
    [musubi.spam.feature.word :refer :all]
    [musubi.spam.feature.database.memory :refer :all]
    [midje.sweet :refer :all]))



(facts new
       (new-feature "viagra") => truthy
       (id (new-feature "test")) => "test"
       (spam-score (new-feature "test")) => 0
       (ham-score (new-feature "test")) => 0)

(facts adjusting
       ;(against-background (before :checks clear-feature-db))
       (ham-score (inc-ham-score (new-feature "good"))) => 1
       (spam-score (inc-spam-score (new-feature "bad"))) => 1)

(facts extract-words 
       (extract-words "this has a few words") => (list "this" "has" "few" "words")
       (extract-words "this has a few words words") => (list "this" "has" "few" "words"))

(facts extract-features
       (extract-features "this has a few words" persistance) => seq?
       (id (lookup "this")) => "this")

