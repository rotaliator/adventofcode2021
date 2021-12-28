(ns adventofcode2021.day05
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def input (->> "day05.txt" io/resource slurp str/split-lines
                (mapv #(str/split % #"\D+"))
                (mapv #(mapv (fn [s] (Integer/parseInt s)) %))))

(defn hor-vert? [[x1 y1 x2 y2]]
  (or (= x1 x2) (= y1 y2)))

(defn range' [start stop]
  (if (< start stop)
    (range start (inc stop))
    (range start (dec stop) -1)))

(defn line->pix [[x1 y1 x2 y2]]
  (cond
    (= x1 x2) (mapv vector (repeat x1)    (range' y1 y2))
    (= y1 y2) (mapv vector (range' x1 x2) (repeat y1))
    :else     (mapv vector (range' x1 x2) (range' y1 y2))))

(comment
  (->> input
       (filterv hor-vert?)
       (mapcat line->pix)
       (frequencies)
       (filterv (fn [[_ v]] (< 1 v)))
       count) ;; => 5632

  (->> input
       (mapcat line->pix)
       (frequencies)
       (filterv (fn [[_ v]] (< 1 v)))
       count) ;; => 22213
  )
