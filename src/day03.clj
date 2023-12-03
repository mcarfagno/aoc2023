(ns aoc)
(require '[clojure.string :as str])
(require '[clojure.set :as cs])

(def input-file "../input/day03.txt")
(defn read-input [file] (str/split-lines (slurp file)))
(defn parse-schematic [x] (for [line x] (str/split line #"")))
(defn get-from-schematic [x [col row]] (nth (nth x col) row))

;; https://github.com/abyala/advent-2023-clojure/blob/main/docs/day03.md
(defn re-matcher-seq
  "Returns a lazy sequence of maps from applying a regular expression `re` to the string `s`. Each returned map
  will be of form `{:value v, :start x, :end y}` where `:value` is the text value from the captured group, and
  `:start` and `:end` are the start and end indexes of that group's characters."
  [re s]
  (letfn [(next-value [m]
            (when (.find m)
              (cons {:value (.group m), :start (.start m), :end (.end m)}
                    (lazy-seq (next-value m)))))]
    (next-value (re-matcher re s))))

;; https://github.com/abyala/advent-2023-clojure/blob/main/docs/day03.md
(defn parse-numbers
  ([line y]
   (map (fn [{:keys [value start end]}]
          {:value (parse-long value),
           :points (set (map #(vector % y) (range start end)))})
     (re-matcher-seq #"\d+" line))))

; ty regex-101
(defn get-symbols-pos
  [x]
  (for [col (range (count x))
        row (range (count (first x)))
        :when (re-matches #"[^\d+|.]|[+]" (get-from-schematic x [col row]))]
    [row col]))

(defn surrounding
  [[a b]]
  (for [x [a (inc a) (dec a)] y [b (inc b) (dec b)]] (vector x y)))

;; set of valid schematics positions for digits to be in
;; aka set of all idx adjacent to a symbol
(def valid-positions
  (set (reduce into
         []
         (map surrounding
           (get-symbols-pos (parse-schematic (read-input input-file)))))))

(defn get-numbers
  [x]
  ;; parse parts and their number from each line
  (for [col (range (count x))
        :let [n (parse-numbers (nth x col) col)]]
    ;; filter out parts whose points do not intersect with the points around
    ;; symbols
    (filter (fn [nn]
              (not= #{}
                    (clojure.set/intersection (get nn :points)
                                              valid-positions)))
      n)))

(defn part1
  [input]
  (->> input
       (get-numbers)
       (flatten)
       (map (fn [x] (get x :value)))
       (reduce +)))


(println (part1 (read-input input-file)))
