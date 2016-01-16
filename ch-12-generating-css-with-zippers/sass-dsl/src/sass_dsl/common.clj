(ns sass-dsl.common
  (:require [clojure.zip :as zip]))

(defn is-element 
  "Is there a css element at this zipper node?"
  [loc] 
  {:pre  [(not (nil? loc))]}
  (not (coll? (zip/node loc))))

(defn get-parent 
  "Retrieve the parent to this css node"
  [loc]
  {:pre  [(not (nil? loc))]}
  (zip/left (zip/up loc)))

(defn is-first-element 
  "Is this the first element in this zipper structure?"
  [loc]
  (nil? (zip/left loc)))

(defn is-last-element 
  "Is this is a tree node in this zipper structure?"
  [loc]
  {:pre [(not (nil? loc))]}
  ;note that nil punning is required here
  (nil? (zip/right loc)))

(defn has-no-colon 
  "Is this lacking a colon? ie is this not a value row?"
  [loc]
  (not (.contains (zip/node loc) ":")))

(defn- has-right-element 
  "Is there an element to the right?"
  [loc]
  (zip/right loc))

(defn is-selector 
  "Is this a selector?"
  [loc]
  {:pre  [(not (nil? loc))]}
  (if (and 
       (is-element loc)
       (has-right-element loc))
    (zip/branch? (zip/right loc))))
