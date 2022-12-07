(ns de.teawork.aoc2022.day.day-07
  (:require
   [de.teawork.aoc2022.util :refer :all]
   [clojure.string :refer [split-lines split starts-with?]])
  (:gen-class))


(defn walk-path
  [dir-sizes cur-dir-size data]
  (cond
    (empty? data) [(conj dir-sizes cur-dir-size) cur-dir-size data]
    (starts-with? (first data) "$ cd ..") [(conj dir-sizes cur-dir-size) cur-dir-size (rest data)]
    (starts-with? (first data) "$ cd") (let [[dir-sizes in-dir-size data] (walk-path dir-sizes  0 (rest data))]
                                         (walk-path dir-sizes (+ in-dir-size cur-dir-size) data))
    (starts-with? (first data) "$ ls") (walk-path dir-sizes cur-dir-size (rest data))
    (starts-with? (first data) "dir") (walk-path dir-sizes cur-dir-size (rest data))
    (re-find #"^\d+ " (first data)) (let [file-size (-> (split (first data) #" ")
                                                        first
                                                        str->int)]
                                      (walk-path dir-sizes (+ file-size cur-dir-size) (rest data)))))


(defn execute
  [input-data]
  (let [data (->> input-data
                  split-lines
                  (walk-path [] 0)
                  first)
        result-p1 (->> data
                       (filter #(< % 100000))
                       (reduce +))
        space-to-free (- 30000000 (- 70000000 (apply max data)))
        result-p2 (apply min (filter #(< space-to-free %) data))]
    (println "P1: " result-p1)
    (println "P2: " result-p2)))