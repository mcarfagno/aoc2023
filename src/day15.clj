(ns aoc)
(require '[clojure.string :as str])

(defn read-input [file] (str/split (str/replace (slurp file) #"\n" "") #","))

(defn str-to-ASCII [x] (map int (seq (char-array x))))
(defn HASH [x] (reduce (fn [a b] (rem (* (+ a b) 17) 256)) 0 (str-to-ASCII x)))

(defn solve1
  [input]
  (->> input
       (map HASH)
       (reduce +)))

(println (solve1 (read-input "../input/day15.txt")))

;; part 2
(def boxes (into {} (for [box (range 256)] [box []])))


;(defn split-instr
;  [x]
;  (if (= 4 (count x))
;    [(subs x 0 2) (nth x 2) (nth x 3)]
;    [(subs x 0 2) (nth x 2) nil]))
(defn split-instr
  [x]
  (if (str/includes? x "=")
    [(re-find #"[a-z]+" x) \= (last x)]
    [(re-find #"[a-z]+" x) \- nil]))

;; https://stackoverflow.com/questions/8087115/clojure-index-of-a-value-in-a-list-or-other-collection
(defn index-of-lens
  [[e _] coll]
  (first (keep-indexed #(if (= e (first %2)) %1) coll)))

;; check if exists
(defn process_eq
  [boxes id lens]
  (let [rep-idx (index-of-lens lens (boxes id))]
    (if (some? rep-idx)
      (assoc boxes id (assoc (boxes id) rep-idx lens))
      (assoc boxes id (conj (boxes id) lens)))))

;; remove lens
(defn process_da
  [boxes id label]
  (if (empty? (boxes id))
    boxes
    (assoc boxes id (vec (remove #(= label (first %)) (boxes id))))))

(defn process-instr
  [boxes instr]
  (let [[label op focal] (split-instr instr)]
    (case op
      \= (process_eq boxes (HASH label) [label focal])
      \- (process_da boxes (HASH label) label))))

(defn set-lenses [x] (reduce process-instr boxes x))

(defn box-focusing-power
  [[k v]]
  (reduce +
    (for [i (range (count v))]
      (* (inc k) (* (inc i) (Character/digit (second (nth v i)) 10))))))

(defn solve2
  [input]
  (->> input
       (set-lenses)
       (map box-focusing-power)
       (reduce +)))

(println (solve2 (read-input "../input/day15.txt")))
