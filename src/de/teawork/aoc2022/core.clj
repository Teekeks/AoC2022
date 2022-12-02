(ns de.teawork.aoc2022.core
  (:require
   [clojure.tools.cli :refer [parse-opts]]
   [plumbing.core :refer :all]
   [de.teawork.aoc2022.util :refer :all]
   [de.teawork.aoc2022.day
    [day-01 :as d01]
    [day-02 :as d02]
    [day-03 :as d03]])
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
          (time (condp = (:day options)
                  "01" (d01/execute config)
                  "02" (d02/execute config)
                  "03" (d03/execute config)
                  (println summary))))))
