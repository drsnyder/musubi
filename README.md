# musubi

A Clojure implementation of the [Bayesian spam filter described in Practical
Common Lisp](http://www.gigamonkeys.com/book/practical-a-spam-filter.html).

## Usage

    (:require (musubi.spam [bayesian :refer :all] 
                           [feature :refer :all]
                           [store :refer :all])
              [musubi.spam.feature.word :refer (extract-features)]
              [musubi.spam.store.memory :refer :all]
    ; ...

    (def s (new-store))
    (map #(train s % :spam) spam-texts)
    (map #(train s % :ham)  ham-texts)

    ; returns the probability that new-unknown is spam 
    : closer to 1 is spam
    ; closer to 0 is ham
    (score s new-unknown-text)

## License

Copyright © 2013 Damon Snyder 

Distributed under the Eclipse Public License, the same as Clojure.
