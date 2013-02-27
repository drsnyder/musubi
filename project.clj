(defproject musubi "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [midje "1.5-alpha10"]
                 [bultitude "0.1.7"]
                 [org.clojure/java.jdbc "0.2.3"]
                 [postgresql "9.0-801.jdbc4"]]
  :repl-options {:init-ns musubi.repl-helper}
  :profiles {:dev {:dependencies []
                   :plugins [[lein-midje "2.0.3"]]}})
