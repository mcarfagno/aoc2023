(ns aoc)
(require '[clojure.string :as str])
(require '[clojure.set :as cs])

(def input-file "../input/day03.txt")
(defn read-input [file] (str/split-lines (slurp file)))

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

(defn parse-symbols
  ([line y]
   (map (fn [{:keys [value start end]}]
          {:value value, :points (set (map #(vector % y) (range start end)))})
     (re-matcher-seq #"[^\d+|.]|[+]" line))))

;; utility to apply parse-X to each line of the schematic
(defn parse-from-schematic
  [x parser]
  ;; parse parts and their number from each line
  (flatten (for [col (range (count x)) :let [n (parser (nth x col) col)]] n)))

(defn adjacent?
  [a b]
  (not= #{} (clojure.set/intersection (get a :points) (get b :points))))

(defn surrounding
  [[a b]]
  (for [x [a (inc a) (dec a)] y [b (inc b) (dec b)]] (vector x y)))

(defn expand-points
  [x]
  {:value (get x :value), :points (set (surrounding (first (get x :points))))})

(def all-symbols (parse-from-schematic (read-input input-file) parse-symbols))
(def all-numbers (parse-from-schematic (read-input input-file) parse-numbers))
(def expanded (map #(expand-points %) all-symbols))
;(println all-symbols)
;(println all-numbers)
;(println expanded)


(def valid-parts
  (filter (fn [x] (pos? (count (for [y expanded :when (adjacent? x y)] y))))
    all-numbers))

(defn part1
  []
  (->> valid-parts
       (map (fn [x] (get x :value)))
       (reduce +)))

(println (part1))
