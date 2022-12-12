(ns de.teawork.aoc2022.day.day-11
  (:require
   [de.teawork.aoc2022.util :refer :all]
   [clojure.string :as str]
   [uncomplicate.fluokitten.core :as f]))


(defn parse-monkey
  [data]
  (let [op-lookup {"*" *
                   "+" +}
        d (str/split-lines data)
        m-nr (->> (str/split (first d) #" ")
                  second
                  (#(str/join "" (drop-last %)))
                  str->int)
        items (-> (second d)
                  (str/split #":")
                  second
                  (str/split #", ")
                  (#(f/fmap str->int %)))
        op-data (-> (nth d 2)
                    (str/split #"= ")
                    second
                    (str/split #" "))
        op (op-lookup (second op-data))
        op-param (->> [(first op-data) (last op-data)]
                      (f/fmap #(if (= % "old") % (str->int %))))
        test-div (-> (nth d 3)
                     (str/split #" ")
                     last
                     str->int) 
        test-pos (-> (nth d 4)
                     (str/split #" ")
                     last
                     str->int)
        test-neg (-> (nth d 5)
                     (str/split #" ")
                     last
                     str->int)]
    {:monkey m-nr :items items :op op :op-param op-param
     :test-div test-div :test-pos test-pos :test-neg test-neg
     :activity 0}))

(defn divisible?
  [div num]
  (zero? (mod num div)))

(defn inspect-item
  [monkey
   item
   magic-fn]
  (let [ops (->> (:op-param monkey)
                 (f/fmap #(if (= % "old") item %)))
        new-val (-> (apply (:op monkey) ops)
                    (magic-fn))
        target (if (divisible? (:test-div monkey) new-val)
                 (:test-pos monkey)
                 (:test-neg monkey))]
    [target new-val]))


(defn monkey-inspect
  [monkey
   monkey-data 
   magic-fn]
  (loop [items (:items monkey)
         md monkey-data]
    (let [item (first items)
          rem (rest items)]
      (if (empty? items)
        md
        (let [[target new-val] (inspect-item monkey item magic-fn)
              mid (:monkey monkey)
              new-md (-> (assoc-in md [target :items] (conj (:items (nth md target)) new-val))
                         (assoc-in [mid :items] (rest (:items (nth md mid))))
                         (assoc-in [mid :activity] (inc (:activity (nth md mid)))))]
          (recur rem new-md))))))


(defn inspect-round
  [monkeys magic-fn]
  (loop [idx 0
         md monkeys]
    (if (>= idx (count monkeys))
      md
      (recur (inc idx) (monkey-inspect (nth md idx) md magic-fn)))))

(defn inspect-rounds
  [monkeys rounds magic-fn]
  (loop [nr 0
         md monkeys]
    (if (>= nr rounds)
      md
      (recur (inc nr) (inspect-round md magic-fn)))))

(defn execute
  [input-data]
  (let [data (->> (str/split input-data #"\n\n")
                  (f/fmap parse-monkey))
        magic-mod (->> data
                       (map :test-div)
                       (apply *))
        d-p1 (->> (inspect-rounds data 20 #(quot % 3))
                  (map :activity)
                  (sort >)
                  (take 2)
                  (reduce *))
        d-p2 (->> (inspect-rounds data 10000 #(mod % magic-mod))
                  (map :activity)
                  (sort >)
                  (take 2)
                  (reduce *))]
    [d-p1 d-p2]))