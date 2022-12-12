(ns de.teawork.aoc2022.core
  (:require
   [clojure.tools.cli :refer [parse-opts]]
   [plumbing.core :refer :all]
   [de.teawork.aoc2022.util :refer :all]
   [de.teawork.aoc2022.day
    [day-01 :as d01]
    [day-02 :as d02]
    [day-03 :as d03]
    [day-04 :as d04]
    [day-05 :as d05]
    [day-06 :as d06]
    [day-07 :as d07]
    [day-08 :as d08]
    [day-09 :as d09]
    [day-10 :as d10]
    [day-11 :as d11]]
   [criterium.core :as crit])
  (:gen-class))

(def cli-options
  [["-d" "--day DAY" "Day to compute"]
   ["-p" "--pull DAY" "only pull the data for the given day"]
   ["-b" "--benchmark DAY" "Benchmark the solution for the given day"]
   ["-h" "--help"]])


(defn run-day
  [day input output]
  (let [[p1 p2] (condp = day
                  "01" (d01/execute input)
                  "02" (d02/execute input)
                  "03" (d03/execute input)
                  "04" (d04/execute input)
                  "05" (d05/execute input)
                  "06" (d06/execute input)
                  "07" (d07/execute input)
                  "08" (d08/execute input)
                  "09" (d09/execute input)
                  "10" (d10/execute input)
                  "11" (d11/execute input))]
    (if output
      (println (str "Part 1:\n" p1 "\n\nPart 2:\n" p2))
      nil)))

(defn -main
  [& args]
  (letk [[errors
          options
          summary] (parse-opts args cli-options)
         config (get-config)]
        (cond
          (:help options)
          (println summary)

          (seq errors)
          (do
            (println errors)
            (println summary))

          (:pull options)
          (get-day-data (:pull options) config)

          (:day options)
          (let [input-data (get-day-data (:day options) config)]
            (run-day (:day options) input-data true))

          (:benchmark options)
          (let [input-data (get-day-data (:benchmark options) config)]
            (crit/quick-bench (run-day (:benchmark options) input-data false)))

          :else
          (println summary))))
          
