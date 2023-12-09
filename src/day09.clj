(ns aoc)
(require '[clojure.string :as str])
(defn read-input
  [file]
  (map #(vec (map parse-long (re-seq #"[-]?\d+" %)))
    (str/split-lines (slurp file))))

(defn diff [[a b]] (- b a))

(defn extrapolate
  [samples]
  (loop [s samples
         placeholders []]
    (cond (every? zero? s) (concat placeholders [0])
          :else (recur (map diff (partition 2 1 s))
                       (concat placeholders [(last s)])))))

(defn solve1
  [input]
  (->> input
       (map extrapolate)
       (map #(reduce + (reverse %)))
       (reduce +)))

(defn solve2
  [input]
  (->> input
       (map #(extrapolate (reverse %)))
       (map #(reduce + (reverse %)))
       (reduce +)))

(println (solve1 (read-input "../input/day09.txt")))
(println (solve2 (read-input "../input/day09.txt")))
