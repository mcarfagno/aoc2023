(ns aoc)
(require '[clojure.string :as str])

(def input-file "../test/day03.txt")
(defn read-input [file] (str/split-lines (slurp file)))
(defn parse-schematic [x] (for [line x] (str/split line #"")))
(defn get-from-schematic [x [col row]] (nth (nth x col) row))

(defn get-symbols-idx
  [x]
  (for [col (range (count x))
        row (range (count (first x)))
        :when (re-matches #"[$]|[#]|[*]|[-]|[*]|[+]"
                          (get-from-schematic x [col row]))]
    [col row]))

(defn get-numbers-idx
  [x]
  (for [col (range (count x))
        row (range (count (first x)))
        :when (re-matches #"\d" (get-from-schematic x [col row]))]
    [col row]))

(defn surrounding-idx
  [[a b]]
  [[(inc a) b] [(dec a) b] [a (inc b)] [a (dec b)] [(inc a) (inc b)]
   [(dec a) (inc b)] [(inc a) (dec b)] [(dec a) (dec b)]])


;; set of valid schematics positions for digits to be in
;; aka set of all idx adjacent to a symbol
(def valid-positions
  (set (reduce into
         []
         (map surrounding-idx
           (get-symbols-idx (parse-schematic (read-input input-file)))))))

; create seq containing indices of digits close to a symbol
(defn candidates [x] (filter (fn [a] (contains? valid-positions a)) x))

(defn part1
  [input]
  (->> input
       (parse-schematic)
       (get-numbers-idx)
       (candidates))) ;;sequences of indices where a valid number is located


(println (part1 (read-input input-file)))
