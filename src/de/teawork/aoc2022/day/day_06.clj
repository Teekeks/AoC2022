(ns de.teawork.aoc2022.day.day-06
  (:require
   [de.teawork.aoc2022.util :refer :all])
  (:gen-class))


(defn get-unique
  [input length idx]
  (let [sequence (-> (subs input idx (+ idx length))
                     char-array
                     seq)]
    (if (> (count (dups sequence)) 0)
      (recur input length (+ 1 idx))
      (+ idx length))))


(defn execute
  [input-data]
  (let [result-p1 (get-unique input-data 4 0)
        result-p2 (get-unique input-data 14 0)]
    (println "P1: " result-p1)
    (println "P2: " result-p2)))