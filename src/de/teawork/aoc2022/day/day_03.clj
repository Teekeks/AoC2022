(ns de.teawork.aoc2022.day.day-03
  (:require
   [de.teawork.aoc2022.util :refer :all]
   [clojure.string :refer [split-lines]]
   [uncomplicate.fluokitten.core :as f]
   [clojure.set :refer [intersection]])
  (:gen-class))


(defn execute
  [input-data]
  (let [data (->> input-data
                  split-lines)
        result-p1 (->> data
                       (f/fmap #(split-at (/ (count %) 2) %))
                       (f/fmap (fn [e]
                                 (->> (intersection (set (first e)) (set (second e)))
                                      first
                                      (#(- (int %) (if (Character/isUpperCase %) 38 96))))))
                       (reduce +))
        result-p2 (->> data
                       (f/fmap set)
                       (partition 3)
                       (f/fmap (fn [e] 
                                 (->> (apply intersection e)
                                      first
                                      (#(- (int %) (if (Character/isUpperCase %) 38 96))))))
                       (reduce +))]
    (println "P1: " result-p1)
    (println "P2: " result-p2)))