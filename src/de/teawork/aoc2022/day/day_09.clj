(ns de.teawork.aoc2022.day.day-09
  (:require
   [de.teawork.aoc2022.util :refer :all]
   [clojure.string :refer [split-lines split]])
  (:gen-class))


(def dir-lookup {"L" [-1 0]
                 "R" [1 0]
                 "U" [0 -1]
                 "D" [0 1]})

(defn follow
  [[hx hy] [tx ty]]
  (let [dx (- hx tx)
        dy (- hy ty)]
    (if (< (max (abs dx) (abs dy)) 2)
      [tx ty]
      [(+ tx (norm-1d dx))
       (+ ty (norm-1d dy))])))


(defn move-head
  [head cmd]
  (mapv + head (dir-lookup cmd)))

(defn move-tail
  [head tail]
  (loop [[current & remaining] tail
         rope [head]]
    (if (empty? current)
      rope
      (recur remaining (->> current
                            (follow (peek rope))
                            (conj rope))))))


(defn move-rope
  [[head & tail] cmd]
  (-> head
      (move-head cmd)
      (move-tail tail)))

(defn play-plan
  [instructions length]
  (loop [[cmd & rem] instructions
         rope (vec (repeat length [0 0]))
         seen #{}]
    (if (empty? cmd)
      (count (conj seen (peek rope)))
      (recur rem
             (move-rope rope cmd)
             (conj seen (peek rope))))))

(defn parse-line 
  [line]
  (->> line
       (#(split % #" "))
       ((fn [[dir amount]]
          (repeat (str->int amount) dir)))))


(defn execute
  [input-data]
  (let [data (->> input-data
                  split-lines
                  (mapcat parse-line))
        result-p1 (play-plan data 2)
        result-p2 (play-plan data 10)]
    (println "P1: " result-p1)
    (println "P2: " result-p2)))