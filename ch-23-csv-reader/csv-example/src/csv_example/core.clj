(ns csv-example.core
	(:gen-class)
    (:require [clojure.java.io :as io]))


(defn -main
  "Import a CSV."
  [& args]
  (println "starting") 
  (let [col-sum (atom 0)
        filename-from-args (get args 0)
        fileName (if (empty? filename-from-args) 
                   "sample-data.csv" filename-from-args)
        stream (io/reader fileName)] 
    (loop []
      (let [line ^String (.readLine stream)]
        (if (and (not (nil? line)) (> (count line) 0))
          (do
            (let [col1 (read-string (aget (.split line ",") 0))]
              (reset! col-sum (+ col1 @col-sum))
              (recur))))))
    (println "done - sum is " (str @col-sum))))
