(ns de.teawork.aoc2022.day.02
  (:require
    [de.teawork.aoc2022.util :refer :all]
    [clojure.string :refer [split-lines split]]
    [uncomplicate.fluokitten.core :as f])
  (:gen-class))


(def scores {:rock 1 :paper 2 :scissors 3})

(def win-lookup {:rock :scissors 
                 :scissors :paper
                 :paper :rock})

(def loose-lookup {:scissors :rock
                   :paper :scissors
                   :rock :paper})

(def lookup {"A" :rock
             "X" :rock
             "B" :paper
             "Y" :paper
             "C" :scissors
             "Z" :scissors})

(def result-p2-lookup {"X" :loose
                       "Y" :draw
                       "Z" :win})

(defn play-game-p1
  [theirs ours]
  (let [e (get lookup theirs)
        o (get lookup ours)
        result (if (= o e) 3 (if (= (get win-lookup o) e) 6 0))]
    (+ result (get scores o))))

(defn play-game-p2
  [theirs result]
  (let [e (get lookup theirs)
        r (get result-p2-lookup result)
        o (condp = r
            :draw e
            :loose (e win-lookup)
            :win (e loose-lookup))
        result (if (= o e) 3 (if (= (get win-lookup o) e) 6 0))]
    (+ result (get scores o))))
    

(defn execute
  [config]
  (let [data (->> (get-day-data "02" config)
                  split-lines
                  (f/fmap #(split % #" ")))
        score-p1 (->> (f/fmap #(play-game-p1 (first %) (second %)) data)
                   (reduce +))
        score-p2 (->> (f/fmap #(play-game-p2 (first %) (second %)) data)
                      (reduce +))]
    (println "P1: " score-p1)
    (println "P2: " score-p2)))
