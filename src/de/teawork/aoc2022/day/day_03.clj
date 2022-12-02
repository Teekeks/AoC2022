(ns de.teawork.aoc2022.day.day-03
  (:require
   [de.teawork.aoc2022.util :refer :all]
   [clojure.string :refer [split-lines split]]
   [uncomplicate.fluokitten.core :as f])
  (:gen-class))


(defn execute
  [config]
  (let [data (->> (get-day-data "03" config))]
    data))