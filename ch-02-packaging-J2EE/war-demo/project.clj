(defproject war-demo "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [compojure "1.4.0"]]
  :plugins [[lein-ring "0.9.7"]]
  :ring {:handler war-demo.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.0"]]}})
