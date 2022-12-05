(ns de.teawork.aoc2022.day.day-05
  (:require
   [de.teawork.aoc2022.util :refer :all]
   [clojure.string :refer [split-lines split]]
   [uncomplicate.fluokitten.core :as f])
(:gen-class))


(defn parse-initial-state 
  [input]
  (->> (reverse (for [line input
                      :let [parts (re-seq #".{1,4}" line)]
                      :when (not (Character/isDigit (second line)))] ; skip the line with the index
                  (f/fmap #(if (= \[ (first %)) (second %) nil) parts)))
       (apply map vector)
       (map #(into [] (remove nil? %))) 
       (zipmap [1 2 3 4 5 6 7 8 9])))

(defn parse-input-instructions
  [input]
  (for [line input
        :let [parts (split line #" ")]]
    (->> (drop-nth 2 parts)
         (f/fmap str->int))))

(defn move-part
  "moves up to 'mvoeable' parts at a time from 'from' to 'to' n times and returns the new state"
  [list moveable times from to]
  (let [move (if (> moveable times) times moveable)
        part (take-last move (get list from))
        src (into [] (drop-last move (get list from)))
        dest (into (get list to) part)
        result (-> (assoc list from src)
                   (assoc to dest))]
    (if (> times 1)
      (recur result moveable (- times move) from to)
      result)))


(defn work-instructions
  [state instructions moveable]
  (let [inst (first instructions)
        result (apply move-part state moveable inst)
        next-insts (rest instructions)]
    (if (> (count next-insts) 0)
      (recur result next-insts moveable)
      result)))


(defn execute [input-data]
  (let [data (->> input-data
                  split-lines
                  (split-by empty?))
        initial-state (parse-initial-state (first data))
        move-data (parse-input-instructions (second data))
        final-state-p1 (work-instructions initial-state move-data 1)
        result-p1 (str (last (get final-state-p1 1))
                       (last (get final-state-p1 2))
                       (last (get final-state-p1 3))
                       (last (get final-state-p1 4))
                       (last (get final-state-p1 5))
                       (last (get final-state-p1 6))
                       (last (get final-state-p1 7))
                       (last (get final-state-p1 8))
                       (last (get final-state-p1 9)))
        final-state-p2 (work-instructions initial-state move-data 1000)
        result-p2 (str (last (get final-state-p2 1))
                       (last (get final-state-p2 2))
                       (last (get final-state-p2 3))
                       (last (get final-state-p2 4))
                       (last (get final-state-p2 5))
                       (last (get final-state-p2 6))
                       (last (get final-state-p2 7))
                       (last (get final-state-p2 8))
                       (last (get final-state-p2 9)))]
    (println "P1: " result-p1)
    (println "P2: " result-p2)))