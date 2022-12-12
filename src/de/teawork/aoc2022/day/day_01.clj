(ns de.teawork.aoc2022.day.day-01
  (:require
    [de.teawork.aoc2022.util :refer :all]
    [clojure.string :refer [split-lines]]
    [uncomplicate.fluokitten.core :as f])
  (:gen-class))

(defn execute
  [input-data]
  (let [elves (->> input-data
                   split-lines
                   (f/fmap str->int)
                   (split-by nil?)
                   (f/fmap #(reduce + %))
                   sort)]
    [(last elves)
     (->> (take-last 3 elves)
          (reduce +))]))
