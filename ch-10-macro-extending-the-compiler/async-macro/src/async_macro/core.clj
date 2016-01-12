(ns async-macro.core
  (:gen-class))

(defmacro go-off-main-thread-rotating [& args]
  (let [instructions (zipmap (range (count args)) args)
  		size (count args)]
  	(future
  `(do
  	~(loop [x 0] (when (<= x 10)
  	  (let [y (mod x size)]
  	  	(eval (get instructions y)))
  		(Thread/sleep 100)
  	  	(recur (inc x))))))))

(go-off-main-thread-rotating
  (println "a")
  (println "b")
  (println "c")
  (println "d"))

(def q (ref (clojure.lang.PersistentQueue/EMPTY)))

(dosync (alter q conj 1 2 3)) 

(defn qpop [queue-ref]
  (dosync 
    (let [item (peek @queue-ref)]
      (alter queue-ref pop)
      item)))

(println "pop: " (qpop q))
(println "pop: " (qpop q))

(defmacro wake-on-queue-event [queue & args]
  (future
  	(do
  	  (loop [x 0] 
  	  	(let [item  (qpop (eval queue))]
  	  	  (when item
  	  		(println "pop-wake: " item)
  	  	  	(mapcat eval args))); run the instructions passed in when we get a queue item
  	  	(Thread/sleep 100)
  	  	(recur (inc x))))))

(wake-on-queue-event q (println "awake!"))
; we have converting code from blocking to non-blocking!

(dosync (alter q conj 4 5 6)) 

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (future (Thread/sleep 2000) ;put in future-exit after 2 seconds
  	(println "Done")
	(System/exit 0))
  (shutdown-agents));Needed when using futures



