(ns aoc)
(require '[clojure.string :as str])

(defn parse-node
  [x]
  (let [[N L R] (re-seq #"[A-Z][A-Z][A-Z]" x)] {:node N, :left L, :right R}))

(defn read-input
  [file]
  ((fn [x] [(first x) (set (map parse-node (str/split-lines (second x))))])
    (str/split (slurp file) #"\n\n")))

(defn next-node
  [current next-step]
  (let [{curr-node :node, step :step, nodes :map} current
        direction (first (filter #(= curr-node (:node %)) nodes))]
    (case curr-node
      "ZZZ" (reduced current)
      {:node (if (= next-step "R") (:right direction) (:left direction)),
       :step (inc step),
       :map nodes})))

(defn walk-map
  [[instructions nodes]]
  (let [inst-seq (cycle (str/split instructions #""))
        current {:node "AAA", :step 0, :map nodes}]
    (reduce next-node current inst-seq)))

(defn solve1
  [input]
  (->> input
       (walk-map)
       (:step)))

(println (solve1 (read-input "../input/day08.txt")))
