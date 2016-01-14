(defproject lein-test "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.7.0"]]
  :main ^:skip-aot lein-test.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
