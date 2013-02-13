(ns musubi.spam.feature)

(defprotocol Feature
  "A spam feature abstraction."
  (id [feat])
  (ham-score [feat])
  (spam-score [feat]))

(defprotocol SpamFeaturePersistance
  (store [feat])
  (inc-spam-score [feat])
  (inc-ham-score [feat]))


(deftype SpamFeature [f ham-score spam-score]
  Feature
  (id [feat] feat)
  (ham-score [feat] ham-score)
  (spam-score [feat] spam-score))

(defn new-spam-feature 
  ([feat ham-score spam-score]
   (SpamFeature. feat ham-score spam-score))
  ([feat]
   (SpamFeature. feat 0 0)))

