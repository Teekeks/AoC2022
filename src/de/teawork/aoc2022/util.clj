(ns de.teawork.aoc2022.util
  (:require
   [clojure.java.io :as io]
   [clojure.edn :as edn]
   [clojure.string]
   [clj-http.client :as http])
  (:gen-class))

(defn str->int
 [str]
 (if (= "" str)
    nil
    (read-string (clojure.string/replace str #"^0+(?!$)" ""))))


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
  "splits a sequence into multiple sequences on the given marker, removing the marker"
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

(defn drop-nth 
  "complement to take-nth"
  [n coll]
  (lazy-seq
   (when-let [s (seq coll)]
     (concat (take (dec n) (rest s))
             (drop-nth n (drop n s))))))

(defn dups 
  "get duplicates in sequence"
  [seq]
  (for [[id freq] (frequencies seq)
        :when (> freq 1)]
    id))


(defn add-vec
  "adds 2 or more vectors to each other"
  [& args]
  (when (seq args)
    (apply mapv + args)))


(defn norm-1d
  "normalizes a '1d vector' (aka single number)"
  [x]
  (cond
    (> x 0) 1
    (< x 0) -1
    :else 0))