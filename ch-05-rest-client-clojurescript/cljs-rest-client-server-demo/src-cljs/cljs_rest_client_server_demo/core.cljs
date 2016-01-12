(ns cljs-rest-client-server-demo.core
  (:require [clojure.browser.repl :as repl]
			[goog.net.XhrIo :as xhr]
          	[goog.Uri.QueryData :as query-data]
            [goog.structs :as structs]
          	[goog.dom :as dom]))

(enable-console-print!)

(defn receiver [event]
  (let [response (.-target event)]
    (println (.getResponseText response))
    (set! (.-value (dom/getElement "returnVal"))  
    	(.getResponseText response))))

(defn post [url content]
  (xhr/send url receiver "POST" content))

(defn ^:export main [& _]
  (println "starting!")
  (post "/4459" 
  	(query-data/createFromMap 
      (structs/Map. (clj->js {:mykey 
        (.-value (dom/getElement "returnVal"))}))))
  (println "done"))

