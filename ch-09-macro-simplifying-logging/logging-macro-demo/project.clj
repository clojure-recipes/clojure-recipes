(defproject logging-macro-demo "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/math.numeric-tower "0.0.4"]
                 [clojurewerkz/money "1.9.0"]]
  :main ^:skip-aot logging-macro-demo.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
