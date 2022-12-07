(ns de.teawork.aoc2022.day.day-06
  (:require
   [de.teawork.aoc2022.util :refer :all])
  (:gen-class))


(defn get-unique
  [input length]
  (loop [idx 0]
    (if (= (count (set (subs input idx (+ idx length)))) length)
      (+ idx length)
      (recur (+ 1 idx)))))


(defn execute
  [input-data]
  (let [result-p1 (get-unique input-data 4)
        result-p2 (get-unique input-data 14)]
    (println "P1: " result-p1)
    (println "P2: " result-p2)))