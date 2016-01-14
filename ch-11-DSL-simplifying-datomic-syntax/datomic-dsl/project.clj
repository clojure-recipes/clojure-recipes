(defproject datomic-dsl "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.6.0"]
    			 [com.datomic/datomic-free "0.9.5130"]]
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
