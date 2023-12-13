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
        bifurcarion (first (filter #(= curr-node (:node %)) nodes))]
    (if (= curr-node "ZZZ")
      (reduced current)
      {:node (if (= next-step "R") (:right bifurcarion) (:left bifurcarion)),
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

;; part 2
(defn get-nodes
  [x regex]
  (set (filter #(re-matches regex %) (for [y x] (get y :node)))))

(defn step-all-nodes
  [curr-nodes move nodes]
  (set (for [n curr-nodes
             :let [bifurcarion (first (filter #(= n (:node %)) nodes))]]
         (if (= move "R") (:right bifurcarion) (:left bifurcarion)))))

(defn next-nodes
  [current next-step]
  (let [{curr-nodes :nodes, targets :targets, step :step, nodes :map} current]
    (if (every? curr-nodes targets)
      (reduced current)
      {:nodes (step-all-nodes curr-nodes next-step nodes),
       :step (inc step),
       :targets targets,
       :map nodes})))

(defn walk-map-multi
  [[instructions nodes]]
  (let [inst-seq (cycle (str/split instructions #""))
        src (get-nodes nodes #"..[A]")
        trg (get-nodes nodes #"..[Z]")
        current {:nodes src, :targets trg, :step 0, :map nodes}]
    (println src)
    (println trg)
    (reduce next-nodes current inst-seq)))


(defn solve2
  [input]
  (->> input
       (walk-map-multi)
       (:step)))

(println (solve1 (read-input "../input/day08.txt")))
(println (solve2 (read-input "../input/day08.txt")))
