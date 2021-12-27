(ns adventofcode2021.day04
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.set :as set]))

(def input' (->> "day04.txt" io/resource slurp))

(defn str->board [s]
  (->> (str/split (str/triml s) #"\s+")
       (mapv #(Integer/parseInt %))
       (partition 5)))

(def input (-> input' (str/split #"\n\n")))
(def numbers (->> input first (#(str/split % #",")) (mapv #(Integer/parseInt %))))
(def boards (->> input rest (mapv str->board)))

(defn bingo? [board numbers]
  (some #(= (count board) %)
        (mapv (fn [row] (count (set/intersection (set numbers) (set row))))
              (concat board (apply map vector board)))))

(defn sum-unmarked [board numbers]
  (apply + (set/difference (set (flatten board)) (set numbers))))

(defn separate-by [pred coll]
  (let [groups (group-by (comp boolean pred) coll)]
    [(groups true) (groups false)]))

(comment
  ;; part1
  (loop [nums (take 1 numbers)]
    (let [bingo-board (filter #(bingo? % nums) boards)]
      (if (seq bingo-board)
        (* (sum-unmarked (first bingo-board) nums) (last nums))
        (recur (take (inc (count nums)) numbers)))))
  ;; => 69579

  ;; part2
  (loop [n 1
         last-won nil
         last-num nil
         last-numbers nil
         boards boards]
    (let [nums (take n numbers)
          [bingo-boards not-bingo-boards] (separate-by #(bingo? % nums) boards)]
      (if (seq boards)
        (recur (inc n) (first bingo-boards) (last nums) nums not-bingo-boards)
        (* (sum-unmarked last-won last-numbers) last-num))))
  ;; => 14877

  )
