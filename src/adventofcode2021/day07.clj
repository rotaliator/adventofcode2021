(ns adventofcode2021.day07
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def input (-> "day07.txt" io/resource slurp
               (str/split #",")
               (->> (mapv (comp #(Long/parseLong %) str/trim)))))

(defn pos-cost [input pos]
  (->> input
       (mapv #(- pos %))
       (mapv #(Math/abs %))
       (reduce +)))

(defn pos-cost2 [input pos]
  (let [cost (fn [x] (reduce + (range 1 (inc (Math/abs (- x pos))))))]
    (->> input
         (mapv cost)
         (reduce +))))

(defn min-cost [input cost-fn]
  (let [low   (apply min input)
        hi    (apply max input)
        avg   (int (/ (reduce + input) (count input)))
        poses (mapcat vector (range avg low -1) (range (inc avg) hi))]
    (->> poses
         (mapv (partial cost-fn input))
         (apply min))))

(comment
  ;;part 1
  (min-cost input pos-cost)
  ;; => 343605

  ;;part 2
  (min-cost input pos-cost2)
  ;; => 96744904

  )
