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
    [day-10 :as d10]])
  (:gen-class))

(def cli-options
  [["-d" "--day DAY" "Day to compute"]
   ["-p" "--pull DAY" "only pull the data for the given day"]
   ["-h" "--help"]])

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

          :else
          (let [input-data (get-day-data (:day options) config)]
            (condp = (:day options)
              "01" (d01/execute input-data)
              "02" (d02/execute input-data)
              "03" (d03/execute input-data)
              "04" (d04/execute input-data)
              "05" (d05/execute input-data)
              "06" (d06/execute input-data)
              "07" (d07/execute input-data)
              "08" (d08/execute input-data)
              "09" (d09/execute input-data)
              "10" (d10/execute input-data)
              (println summary))))))
