(ns adventofcode2021.day07
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def input (-> "day07.txt" io/resource slurp
               (str/split #",")
               (->> (mapv (comp #(Long/parseLong %) str/trim)))))

(defn mean [coll]
  (let [sum (apply + coll)
        count (count coll)]
    (if (pos? count)
      (int (/ sum count))
      0)))

(defn median [coll]
  (let [sorted (sort coll)
        cnt (count sorted)
        halfway (quot cnt 2)]
    (if (odd? cnt)
      (nth sorted halfway)
      (let [bottom (dec halfway)
            bottom-val (nth sorted bottom)
            top-val (nth sorted halfway)]
        (mean [bottom-val top-val])))))

(defn pos-cost [input pos]
  (->> input
       (mapv #(- pos %))
       (mapv #(Math/abs ^long %))
       (reduce +)))

(defn pos-cost2 [input pos]
  (let [cost (fn [x]
               (let [diff (Math/abs ^long (- x pos))]
                 (quot (* diff (inc diff)) 2)))]
    (->> input
         (mapv cost)
         (reduce +))))

(comment
  ;;part 1
  (pos-cost input (median input))
  ;; => 343605

  ;;part 2
  (pos-cost2 input (mean input))
  ;; => 96744904

  )
