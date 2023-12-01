(ns aoc)
(require '[clojure.string :as str])

(defn read-input [file] (vec (str/split-lines (slurp file))))

(defn parse-digits [x] (filter number? (map read-string (str/split x #""))))

(defn first-last [x] [(first x) (last x)])

(defn combine-digits [x] (read-string (apply str x)))

(defn replace-digits
  [x]
  (str/replace x
               #"one|two|three|four|five|six|seven|eight|nine"
               {"one" "1",
                "two" "2",
                "three" "3",
                "four" "4",
                "five" "5",
                "six" "6",
                "seven" "7",
                "eight" "8",
                "nine" "9"}))

(defn part1
  [input]
  (->> input
       (map parse-digits)
       (map first-last)
       (map combine-digits)
       (reduce +)))

(defn part2
  [input]
  (->> input
       (map replace-digits)
       (map parse-digits)
       (map first-last)
       (map combine-digits)
       (reduce +)))

(println (part1 (read-input "../input/day01.txt")))
(println (part2 (read-input "../input/day01.txt")))
