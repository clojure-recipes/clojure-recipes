(ns storm-jms-demo.price-feed)
(def stock-map {:YHOO {:stock-name "YHOO" :max-price 31.1 :min-price 11.51 :wavelength 90 :starting-point 0.5 :eps 2.15}
                :AAPL {:stock-name "AAPL" :max-price 408.38 :min-price 84.11 :wavelength 60 :starting-point 0 :eps 31.25}
                :GOOG {:stock-name "GOOG" :max-price 809.1 :min-price 292.96 :wavelength 50 :starting-point 0.75 :eps 29.31}
                :AMZN {:stock-name "AMZN" :max-price 274.7 :min-price 42.7 :wavelength 45 :starting-point 0.25 :eps 1.15}})
(defn time-model
  "Build a fake pricing model for stocks based on a sine wave." [time-secs stock-map]
  (let [max-price (:max-price stock-map) min-price (:min-price stock-map)
        wavelength (:wavelength stock-map)
        med-price (+ (/ (- max-price min-price) 2) min-price) amplitude (- max-price med-price)
        starting-point (:starting-point stock-map)] (+
                                                     (* (Math/sin
                                                         (- (/
                                                             (* 2 Math/PI time-secs)
                                                             wavelength)
                                                            (* 2 Math/PI starting-point wavelength)))
                                                        amplitude) med-price)))
(defn curr-price
  "Given a stock code, get the price in our model for the current point in
time." [stock-code]
  (format "%.2f" (time-model
                  (/ (java.lang.System/currentTimeMillis) 60)
                  (stock-map (keyword stock-code))))) (def stock-codes ["AAPL" "GOOG" "AMZN" "YHOO"])


