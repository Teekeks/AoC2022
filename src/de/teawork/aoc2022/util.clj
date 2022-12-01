(ns de.teawork.aoc2022.util
  (:require
    [clojure.java.io :as io]
    [clojure.edn :as edn]
    [clj-http.client :as http])
  (:gen-class))

(defn str->int
 [str]
 (if (= "" str)
    nil
    (read-string str)))


(defn get-config
  []
  (let [source "config.edn"]
    (try
      (with-open [r (io/reader source)]
        (edn/read (java.io.PushbackReader. r)))

      (catch java.io.IOException e
        (printf "Couldn't open '%s': %s\n" source (.getMessage e)))
      (catch RuntimeException e
        (printf "Error parsing edn file '%s': %s\n" source (.getMessage e))))))


(defn pull-and-store-data
  [day config file-name]
  (let [headers {:cookie (str "session=" (:session-cookie config))
                 :user-agent (str (:repo config) " by " (:mail-adress config))}
        url (str "https://adventofcode.com/2022/day/" (str->int day) "/input")
        data (->> (http/get url {:headers headers :body "string"})
                  :body)]
    (spit file-name data)
    data))


(defn get-day-data
  [day config]
  (let [file-name (str "resources/" day ".txt")]
    (if (.exists (io/file file-name))
        (slurp file-name)
        (pull-and-store-data day config file-name))))


(defn split-by
  [pred coll]
  (lazy-seq
    (when-let [s (seq coll)]
      (let [[xs ys] (split-with pred s)]
        (if (seq xs)
          (cons xs (split-by pred ys))
          (let [!pred (complement pred)
                skip (take-while !pred s)
                others (drop-while !pred s)
                ys (second (split-with pred others))]
            (cons (concat skip)
                  (split-by pred ys))))))))
