(ns musubi.spam.store)

(defprotocol Store
  "Spam meta data storage."
  (store [this id object])
  (lookup [this id])
  (inc-counter [this id])
  (get-counter [this id]))

