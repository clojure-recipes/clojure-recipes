(defproject stock-ticker-demo "0.0.1-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [io.pedestal/pedestal.service "0.4.1"]
                 [io.pedestal/pedestal.jetty "0.4.1"]
                 [ch.qos.logback/logback-classic "1.1.3" :exclusions [org.slf4j/slf4j-api]]
                 [org.slf4j/jul-to-slf4j "1.7.12"]
                 [org.slf4j/jcl-over-slf4j "1.7.12"]
                 [org.slf4j/log4j-over-slf4j "1.7.12"]
                 [org.clojure/data.json "0.2.6"]
                 [org.clojure/core.async "0.2.374"]]
  :min-lein-version "2.0.0"
  :resource-paths ["config", "resources"]
  :profiles {:dev {:aliases {"run-dev" ["trampoline" "run" "-m" "stock-ticker-demo.server/run-dev"]}
                   :dependencies [[io.pedestal/pedestal.service-tools "0.4.1"]]}}
  :main ^{:skip-aot true} stock-ticker-demo.server)

