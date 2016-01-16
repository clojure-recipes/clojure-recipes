(ns display-status.handler
  (:use compojure.core)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]))
(defroutes app-routes
  (GET "/" [] "Status: Ok")
;(GET "/" [] "Status: Error - System Down!")
  (route/resources "/") (route/not-found "Not Found"))
(def app
  (handler/site app-routes))