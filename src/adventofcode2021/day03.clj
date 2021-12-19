(ns adventofcode2021.day03
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))


(def input (->> "day03.txt" io/resource slurp str/split-lines))
(comment
  (->> input
       (apply mapv vector)
       (mapv frequencies)
       (mapv #(sort-by second %))
       (mapv (juxt #(-> % second first) ffirst))
       (apply mapv vector)
       (mapv #(apply str %))
       (mapv #(Integer/parseInt % 2))
       (apply *))
;; => 852500
  )

(defn get-rating [msb lsb op i]
  (->> (loop [acc   []
              input i]
         (let [freq      (->> input
                              (group-by first)
                              (mapv (fn [[k v]] [k (count v)]))
                              (into {}))
               top-bit   (cond
                           (op (get freq msb) (get freq lsb)) lsb
                           (op (get freq lsb) (get freq msb)) msb
                           :else                              msb)
               remaining (->> input
                              (filterv (fn [x] (= top-bit (-> x first))))
                              (mapv rest))
               left      (count remaining)]
           (cond
             (< 1 left) (recur (conj acc top-bit) remaining)
             :else      (apply conj acc top-bit (first remaining)))))
       (apply str)
       (#(Integer/parseInt % 2))))

(comment
  (let [ox-rating  (get-rating \1 \0 < input)
        co2-rating (get-rating \0 \1 > input)]
    (* ox-rating co2-rating))
;; => 1007985
  )
