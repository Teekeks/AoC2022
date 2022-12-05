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
    [day-05 :as d05]])
  (:gen-class))

(def cli-options
  [["-d" "--day DAY" "Day to compute"]
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

          :else
          (let [input-data (get-day-data (:day options) config)]
            (condp = (:day options)
              "01" (d01/execute input-data)
              "02" (d02/execute input-data)
              "03" (d03/execute input-data)
              "04" (d04/execute input-data)
              "05" (d05/execute input-data)
              (println summary))))))
