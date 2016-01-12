(defproject rest-media-demo "0.1.0-SNAPSHOT"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.7.0-beta2"]
                 [compojure "1.3.4"]
                 [ring/ring-defaults "0.1.5"]
                 [liberator "0.13"]
                 [org.clojure/data.json "0.2.6"]]
  :plugins [[lein-ring "0.9.5"]]
  :ring {:handler rest-media-demo.handler/app
          :auto-reload? true
          :auto-refresh? true}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.2.0"]]}})
