(ns cascalog-pre-format.format-novel (:require [clojure.string :as s]) (:gen-class))
(defn -main [file & args] ; read the whole file 
  (let [book (slurp file)
; Replace all the newlines trailed by lowercase letters by spaces
; note the challenge is "I" in a sentence :) (or any other proper noun)
        book-stripped-newlines (s/join " " (clojure.string/split book #"\r\n(?=[a-z])"))
; Split the string into a vector based on periods followed by space and non-breaking space
        book-sentences (s/split book-stripped-newlines #"(?<=\.)\x20") 
; join the vector into a single string delimited by newlines 
        book-sentences-flat (s/join "\n" book-sentences)]
; write the whole file
    (spit "./data/tuples.txt" book-sentences-flat)))

