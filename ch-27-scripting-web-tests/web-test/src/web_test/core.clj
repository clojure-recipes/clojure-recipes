(ns web-test.core
  (:gen-class)
  (:import (org.openqa.selenium.firefox.FirefoxDriver)
           (org.openqa.selenium.support.ui.WebDriverWait) (org.openqa.selenium.By)))
(defn do-search [driver]
  (.get driver "http://www.google.com/")
  (let [element1 (.findElement driver (org.openqa.selenium.By/name "q"))]
    (.sendKeys element1 (into-array ["sydney"]))
    (.click (.findElement driver (org.openqa.selenium.By/name "btnG")))))
(defn do-wait [driver]
  (let [wait (org.openqa.selenium.support.ui.WebDriverWait. driver 10)
        condition
        (proxy [org.openqa.selenium.support.ui.ExpectedCondition] []
          (apply [^org.openqa.selenium.WebDriver d]
            (.findElement d (org.openqa.selenium.By/id "resultStats"))))]
    (.until wait condition)))
(defn test-results [driver]
  (let [pageResults (.getPageSource driver)
        result1 (.contains pageResults "Harbor")
        result2 (.contains pageResults "Harbour")] (println (str "Is Harbor present? " result1)) (println (str "Is Harbour present? " result2))))
(defn -main [& args]
  (let [driver (org.openqa.selenium.firefox.FirefoxDriver.)]
    (do-search driver) (do-wait driver) (test-results driver) (.close driver)))

