(ns de.teawork.aoc2022.day.day-10
  (:require
   [de.teawork.aoc2022.util :refer :all]
   [clojure.string :as str]
   [uncomplicate.fluokitten.core :as f]))


(defn parse-cycle-states
  [lines]
  (loop [[inst & rem] lines
         val 1
         cycle-states []]
    (if (empty? inst)
      (conj cycle-states val)
      (cond
        (= "noop" inst) 
        (recur rem val (conj cycle-states val))
        
        (str/starts-with? inst "addx")
        (let [new-val (str->int (second (str/split inst #" ")))]
          (recur rem (+ val new-val) (conj cycle-states val val)))))))


(defn render-line
  [line-data]
  (loop [states line-data
         idx 0
         line ""]
    (if (empty? states)
      line
      (let [c (if (and (<= (- (first states) 1) idx)
                       (>= (+ (first states) 1) idx)) "#" ".")]
        (recur (rest states) (inc idx) (str line c))))))


(defn execute
  [input-data] 
  (let [data (-> input-data
                 str/split-lines)
        cycle-states (parse-cycle-states data)
        result-p1 (+ (* (nth cycle-states 19) 20)
                     (* (nth cycle-states 59) 60)
                     (* (nth cycle-states 99) 100)
                     (* (nth cycle-states 139) 140)
                     (* (nth cycle-states 179) 180)
                     (* (nth cycle-states 219) 220))
        screen-line-data (->> (partition 40 cycle-states)
                              (f/fmap vec))
        screen-lines (f/fmap render-line screen-line-data)]
    (println "P1: " result-p1)
    (println "P2:")
    (doseq [line screen-lines]
      (println line))))