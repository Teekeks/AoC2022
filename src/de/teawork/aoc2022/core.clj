(ns de.teawork.aoc2022.core
  (:require
   [clojure.tools.cli :refer [parse-opts]]
   [plumbing.core :refer :all]
   [de.teawork.aoc2022.util :refer :all]
   [de.teawork.aoc2022.day.01 :as d01])
  (:gen-class))

(def cli-options
  [["-d" "--day ID" "Day"]
   ["-h" "--help"]])

(defn -main
  [& args]
  (letk [[arguments
          errors
          options
          summary :as opts] (parse-opts args cli-options)
         config (get-config)]
        (cond
          (:help options)
          (println summary)

          (seq errors)
          (do
            (println errors)
            (println summary))

          :else
          (condp = (:day options)
            "01" (d01/execute config)
            (println summary)))))
