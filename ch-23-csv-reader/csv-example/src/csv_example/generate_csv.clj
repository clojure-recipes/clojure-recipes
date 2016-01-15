(ns csv-example.generate-csv (:gen-class)
    (:require [clojure.java.io :as io]))

(defn rand-line
  "Return one line for a CSV with four columns of random integers." []
  (str (apply str (repeatedly 4 #(str (rand 100) ","))) "\n"))

(defn -main
  "Generate a CSV."
  [& args]
  (with-open [writer (io/writer "sample-data.csv")]
    (dotimes [n 4000001]
      (.write writer (rand-line)))))

