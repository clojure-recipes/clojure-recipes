(ns stock-ticker-demo.service
  (:require [io.pedestal.http :as bootstrap]
            [io.pedestal.http.route :as route]
            [io.pedestal.http.body-params :as body-params]
            [io.pedestal.http.route.definition :refer [defroutes]]
            [ring.util.response :as ring-resp]
            [io.pedestal.http.sse :as sse]
            [clojure.data.json :as json]
            [stock-ticker-demo.price-feed :as price-feed]
            [clojure.core.async :as async])
  (:import  [java.util Date]
            [java.util.concurrent Executors]))

(defn pump-stock-prices 
  "Given a count - iterate returning a series of stock prices"
  [event-ch num-iterations ctx]
  (let [stock-code  (rand-nth price-feed/stock-codes)
        price (price-feed/curr-price stock-code)
        stock-info  (price-feed/stock-map stock-code)
        eps (:eps stock-info)]
    (async/put! event-ch {:name "price"
                          :data (json/write-str 
                                 {:stock-code stock-code :price price 
                                  :eps eps})})
    (println 
     (str stock-code " " price " " eps " " stock-info " " 
          (java.util.Date.) "\n")))
  (Thread/sleep  1000)
  (if (> num-iterations 0)
    (recur event-ch (dec num-iterations) ctx)
    (do
      (async/put! event-ch {:name "close" :data ""})
      (async/close! event-ch))))

(defn prices-stream
  "Starts sending price events to the channel."
  [event-ch ctx]
  (pump-stock-prices event-ch 100 ctx))

(defn prices-called 
  "Feedback for when a stock order is placed on the web page"
  [{{order-json "order"} :form-params}]
  (let [order (json/read-str order-json :key-fn keyword)
        {stock-code :stock-code price :price} order]
    (println order)
    (println 
     (str "An order has come in for: " stock-code " at: " price))) 
  {:status 204})

(defn about-page
  [request]
  (ring-resp/response (format "Clojure %s - served from %s"
                              (clojure-version)
                              (route/url-for ::about-page))))

(defn home-page
  [request]
  (ring-resp/response "Go to <a href='/stocks.html'>this page</a>"))

(defroutes routes
  [[["/" {:get home-page}
     ^:interceptors [(body-params/body-params) bootstrap/html-body]
     ["/prices" {:get [::prices (sse/start-event-stream prices-stream)]
                 :post prices-called}]
     ["/about" {:get about-page}]]]])

(def service {:env :prod
              ::bootstrap/routes routes
              ::bootstrap/resource-path "/public"
              ::bootstrap/type :jetty
              ::bootstrap/port 8080})
