(ns de.teawork.aoc2022.day.day-08
  (:require
   [de.teawork.aoc2022.util :refer :all]
   [clojure.string :refer [split-lines split]]
   [uncomplicate.fluokitten.core :as f])
  (:gen-class))


(defn walk-data
  [data visited pos direction max-val]
  (if (or
       (nil? (get-in data pos))
       (= max-val 9))
    visited
    (let [v (get-in data pos)
          mv (if (> v max-val) v max-val)
          vis (if (> v max-val) (conj visited pos) visited)]
      (recur data vis  [(+ (first pos) (first direction)) (+ (second pos) (second direction))] direction mv))))

(defn direction-view-score-calc
  "calculates the view score from pos into the given direction"
  [data pos direction height score]
  (let [new-pos [(+ (first pos) (first direction)) (+ (second pos) (second direction))]
        c (get-in data new-pos)]
    (if (or (nil? c)
            (>= c height))
      (if (not (nil? c))
        (+ 1 score)
        score)
      (recur data new-pos direction height (+ 1 score)))))

(defn get-view-score
  "gets the view score at a given location"
  [data pos]
  (let [own-height (get-in data pos)]
    (* (direction-view-score-calc data pos [0 1] own-height 0)
       (direction-view-score-calc data pos [0 -1] own-height 0)
       (direction-view-score-calc data pos [1 0] own-height 0)
       (direction-view-score-calc data pos [-1 0] own-height 0))))

(defn execute
  [input-data]
  (let [data (->> input-data
                  split-lines
                  (f/fmap #(split % #""))
                  (f/fmap #(f/fmap str->int %)))
        rows (apply concat (for [idx (range (count data))]
                             (concat (walk-data data [] [idx 0] [0 1] -1)
                                     (walk-data data [] [idx (- (count (first data)) 1)] [0 -1] -1))))
        cols (apply concat (for [idx (range (count (first data)))]
                             (concat (walk-data data [] [0 idx] [1 0] -1)
                                     (walk-data data [] [(- (count data) 1) idx] [-1 0] -1))))
        result-p1 (count (set (concat rows cols)))
        result-p2 (apply max (for [x (range (count data))
                                   y (range (count (first data)))]
                               (get-view-score data [x y])))]
    (println "P1: " result-p1)
    (println "P2: " result-p2)))