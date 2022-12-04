(ns de.teawork.aoc2022.day.day-04
  (:require
   [de.teawork.aoc2022.util :refer :all]
   [clojure.string :refer [split-lines split]]
   [uncomplicate.fluokitten.core :as f])
  (:gen-class))

(defn execute [input-data]
  (let [data (->> input-data
                  split-lines
                  (f/fmap #(split % #",|-"))
                  (f/fmap #(map str->int %)))
        result-p1 (->> data
                       (filter #(or (and (<= (first %) (nth % 2) (second %))
                                         (<= (first %) (nth % 3) (second %)))
                                    (and (<= (nth % 2) (first %) (nth % 3))
                                         (<= (nth % 2) (second %) (nth % 3)))))
                       count)
        result-p2 (->> data
                       (filter #(or (<= (first %) (nth % 2) (second %))
                                    (<= (first %) (nth % 3) (second %))
                                    (<= (nth % 2) (first %) (nth % 3))
                                    (<= (nth % 2) (second %) (nth % 3))))
                       count)]
    (println "P1: " result-p1)
    (println "P2: " result-p2)))