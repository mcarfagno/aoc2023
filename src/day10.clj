(ns aoc)
(require '[clojure.string :as str])
(require '[clojure.set :as cs])

(defn read-input [file] (str/split-lines (slurp file)))


(defn pipes
  [[x y] pipe]
  (case pipe
    "|" {[x (inc y)] [x (dec y)]}
    "-" {[(inc x) y] [(dec x) y]}
    "L" {[x (dec y)] [(inc x) y]}
    "J" {[x (dec y)] [(dec x) y]}
    "7" {[x (inc y)] [(dec x) y]}
    "F" {[x (inc y)] [(inc x) y]}))

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

(defn parse-line
  ([line y]
   (map (fn [{:keys [value start end]}]
          {:value value, :tile [start y], :connecting (pipes [start y] value)})
     (re-matcher-seq #"[7]|[-]|[|]|[F]|[J]|[L]" line))))


(defn parse
  [x]
  (flatten
    (for [col (range (count x)) :let [n (parse-line (nth x col) col)]] n)))


(defn part1
  [input]
  (->> input
       (parse)
       (println)))


(println (part1 (read-input "../test/day10.txt")))
