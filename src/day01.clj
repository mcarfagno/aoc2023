(ns aoc)
(require '[clojure.string :as str])

(defn read-data [file] (vec (str/split-lines (slurp file))))

(defn parse-numbers [x] (filter number? (map read-string (str/split x #""))))

(defn first-last [x] [(first x) (last x)])

(defn combine-digits [x] (read-string (apply str x)))

(defn replace-digits
  [x]
  (str/replace x
               #"zero|one|two|three|four|five|six|seven|eight|nine"
               {"zero" "0",
                "one" "1",
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
       (map parse-numbers)
       (map first-last)
       (map combine-digits)
       (reduce +)
       (println)))

(defn part2
  [input]
  (->> input
       (map replace-digits)
       (map parse-numbers)
       (map first-last)
       (map combine-digits)
       (reduce +)
       (println)))

;(part1 (read-data "../input/day01.txt"))
(part2 (read-data "../input/day01.txt"))
