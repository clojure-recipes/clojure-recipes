(ns logging-macro-demo.core
  (:require [clojure.math.numeric-tower :as math]
            [clojurewerkz.money.amounts :refer [amount-of]]
            [clojurewerkz.money.currencies :refer [USD]]
            [clojurewerkz.money.format :as mf]) 
  (:gen-class))

(defn round 
  "given value and a precision return a rounded number"
  [d precision]
  (let [factor (Math/pow 10 precision)]
    (/ (Math/floor (* d factor)) factor)))

(defn format-currency
  "do decimal currency formatting using the 
  Clojurewerkx/Joda currency library"
  [input] (mf/format (amount-of USD (round input 2))))

(def balances [3367.01 7839.76 326478.01 23479.15])

(defn indices 
  "Given a predicate function and a collection, return a list of collection indexes 
  that satisfy the predicate"
  [pred coll]
  (keep-indexed #(when (pred %2) %1) coll))

(defmacro my-debug 
  "call with a function definition and this will add logging to the start and end"
  [arg]
  (let [param-index (first (indices vector? arg))
        first-half (take (inc param-index) arg)
        body (drop (inc param-index) arg)
        func-name (second arg)
        params (nth arg param-index) 
        new-print-args-statement `(println  (str "(" '~func-name " "  ~params ")"))
        last-statement  (take-last 1 body)
        body-remainder (drop-last 1 body)
        print-last-statement `(println (str '~func-name " result: " ~(first last-statement)))
        new-function (concat first-half
                             (list new-print-args-statement) 
                             body-remainder 
                             (list print-last-statement)
                             last-statement)]
    `(do
       (println "param-index: " ~param-index)
       (println "first-half: " ~first-half)
       (println "body: " '~body)
       (println "func-name: " '~func-name)
       (println "params: " '~params)
       (println "new-print-statement: " '~new-print-args-statement)
       (println "last-statement: " (first '~last-statement))
       (println "body-remainder: " ~body-remainder)
       
       (println "print-last-statement: " '~print-last-statement)
       (println "new-function: " '~new-function)
       (eval '~new-function))))


(my-debug (defn add-meaning "Douglas Adams reference" [arg] (+ 42 arg)))

(defmacro my-debug-sm 
  "call with a function definition and this will add logging to the start and end"
  [arg]
  (let [param-index (first (indices vector? arg))
        first-half (take (inc param-index) arg)
        body (drop (inc param-index) arg)
        func-name (second arg)
        params (nth arg param-index) 
        new-print-args-statement `(println  (str "(" '~func-name " "  ~params ")"))
        last-statement  (take-last 1 body)
        body-remainder (drop-last 1 body)
        print-last-statement `(println (str '~func-name " result: " ~(first last-statement)))
        new-function (concat first-half
                             (list new-print-args-statement) 
                             body-remainder 
                             (list print-last-statement)
                             last-statement)]
    `(do
       (eval '~new-function))))



(my-debug-sm (defn apply-interest 
               "compound interest formula"
               [years interest-rate input-balance]
               (* input-balance (math/expt (+ 1 (float interest-rate)) years)))) 

(my-debug-sm (defn round-and-format
               "set decimal places and currency formatting"
               [val]
               (format-currency (round val 2))))

(def apply-interest-curry (partial apply-interest 1 0.4))

(defn -main
  "Run the macro demo for function logging by a syntax tree walk."
  [& args]
  (println (add-meaning 1))
  (println  (map round-and-format 
                 (map apply-interest-curry balances))))



