(ns rest-demo.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults 
             :refer [wrap-defaults site-defaults]]))

(defn handle-http []
  (context "/:id" [id]
    (defroutes api-routes
      (GET    "/" [] (str "get called: " id "\n"))
      (POST   "/" {form-params :form-params} 
        (str "post called: " id "\n" form-params " \n"))
      (PUT    "/" req (str "put called with params: " req)))
    (DELETE "/" [] (str "delete called: " id "\n"))))

(defroutes app-routes
  (handle-http)
  (route/not-found (str 
                    "This is the default page - try " 
                    "<a href='http://localhost:4000/33'>this</a>\n")))

(def app
  (wrap-defaults app-routes 
                 (assoc-in site-defaults [:security :anti-forgery] false)))
