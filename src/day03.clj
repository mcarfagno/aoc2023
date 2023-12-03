(ns aoc)
(require '[clojure.string :as str])

(defn read-input [file] (str/split-lines (slurp file)))
(defn parse-schematic [x] (for [line x] (str/split line #"")))
(defn get-symbols-positions
  [x]
  (println (count x))
  (println (count (first x)))
  (for [col (range (count x))
        row (range (count (first x)))
        :when (re-matches #"[$]|[#]|[*]|[-]|[*]|[+]" (nth (nth x col) row))]
    [col row]))

(defn part1
  [input]
  (->> input
       (parse-schematic)
       (get-symbols-positions)
       (println)
       ;((fn [x] (re-seq #"\d+|[$]|[#]|[*]|[-]|[*]|[+]" x))) ;ty regex101
  ))


(println (part1 (read-input "../test/day03.txt")))
