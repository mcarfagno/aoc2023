(ns aoc)
(require '[clojure.string :as str])

(defn read-input
  [file]
  (map #(vec (str/split-lines %)) (str/split (slurp file) #"\n\n")))

(defn transpose [m] (apply mapv vector m))

(defn palindrome?
  [coll]
  (and (even? (count coll)) (= (seq coll) (reverse coll))))

(defn reflection? [x] (every? palindrome? x))

(defn get-reflection-idx
  [x]
  (let [l (count (first x))]
    (loop [i 0
           s x]
      ;(println i)
      ;(println s)
      (cond (= i l) nil
            :else (if (reflection? s)
                    (+ i (* 0.5 (count (first s)))) ; columns on LEFT
                    (recur (inc i) (map #(apply str (drop 1 %)) s)))))))

(defn solve1p1
  [input]
  (->> input
       (first)
       (get-reflection-idx)))

(defn solve1p2
  [input]
  (->> input
       (second)
       (transpose)
       (get-reflection-idx)))

(println (solve1p1 (read-input "../test/day13.txt")))
(println (solve1p2 (read-input "../test/day13.txt")))
