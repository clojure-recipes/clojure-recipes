(ns rest-media-demo.handler
  (:use compojure.core)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [liberator.core :refer  [resource defresource]]
            [liberator.representation :refer [render-map-generic]]
            [hiccup.core :refer [html]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [clojure.data.json :as json]))


(def default-media-types
  ["application/json"
   "text/plain"
   "text/html"])

(defmethod render-map-generic "application/json" [data context]
  (json/write-str (conj (:links data) (:properties data))))

(defmethod render-map-generic "text/html" [data context]
  (html [:div
         [:h1 (-> data :class first)]
         [:dl
          (mapcat (fn [[key value]] [[:dt key] [:dd value]])
                  (:properties data))]]))


(defrecord Coffee [name price])

(def ristretto
  (->Coffee "Ristretto" "$1.80"))

(def macchiato
  (->Coffee "Macchiato" "$1.80"))

(def latte
  (->Coffee "Caffe Latte" "$3.20"))

(def coffees [latte macchiato ristretto])

(defn index-data [ctx]
  coffees)

(defresource index
  :allowed-methods [:options :get :post  :delete]
  :available-media-types default-media-types
  :handle-ok index-data)


(defresource coffee [id]
  :allowed-methods [:options :get]
  :available-media-types ["text/html" "application/json"]
  :handle-ok (fn [_]
               {:properties 
                (nth coffees (Integer/parseInt id))
                :links [{:rel ["self"] 
                         :href (str "/coffees/" id)}
                        {:rel ["listing"] 
                         :href "/coffees"}]}))

(defroutes app-routes
  (OPTIONS "/" [] 
    {:headers {"Allow:" "GET, POST, DELETE, OPTIONS"}})
  (ANY "/" [] index)
  (ANY "/coffees" [] index)
  (OPTIONS "/coffees/:id" [] 
    {:headers {"Allow:" "GET, OPTIONS"}})
  (ANY "/coffees/:id" [id] 
    (coffee id))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))
