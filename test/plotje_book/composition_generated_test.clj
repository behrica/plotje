(ns
 plotje-book.composition-generated-test
 (:require
  [tablecloth.api :as tc]
  [scicloj.kindly.v4.kind :as kind]
  [scicloj.plotje.api :as pj]
  [scicloj.metamorph.ml.rdatasets :as rdatasets]
  [clojure.test :refer [deftest is]]))


(def
 v3_l33
 (pj/arrange
  [(->
    (rdatasets/datasets-iris)
    (pj/lay-point :sepal-length :sepal-width {:color :species}))
   (->
    (rdatasets/datasets-iris)
    (pj/lay-point :petal-length :petal-width {:color :species}))]))


(deftest
 t4_l37
 (is
  ((fn
    [v]
    (let
     [s (pj/svg-summary v)]
     (and (= 2 (:panels s)) (= 300 (:points s)))))
   v3_l33)))


(def
 v6_l44
 (pj/arrange
  [(->
    (rdatasets/datasets-iris)
    (pj/lay-point :sepal-length :sepal-width {:color :species}))
   (->
    (rdatasets/datasets-iris)
    (pj/lay-point :petal-length :petal-width {:color :species}))]
  {:cols 1}))


(deftest
 t7_l49
 (is ((fn [v] (= 2 (:panels (pj/svg-summary v)))) v6_l44)))


(def
 v9_l67
 (def
  weighted
  (pj/pose
   {:layout {:direction :horizontal, :weights [2 1]},
    :poses
    [{:mapping {:x :sepal-length, :y :sepal-width},
      :layers [{:layer-type :point}]}
     {:mapping {:x :petal-length, :y :petal-width},
      :layers [{:layer-type :point}]}],
    :data (rdatasets/datasets-iris)})))


(def v11_l79 weighted)


(deftest
 t12_l81
 (is
  ((fn
    [v]
    (let
     [s (pj/svg-summary v)]
     (and (= 2 (:panels s)) (= 300 (:points s)))))
   v11_l79)))


(def v14_l89 (kind/pprint weighted))


(deftest
 t15_l91
 (is
  ((fn
    [pose]
    (and
     (= [2 1] (get-in pose [:layout :weights]))
     (= 2 (count (:poses pose)))))
   v14_l89)))


(def
 v17_l108
 (def
  shared-x
  (pj/pose
   {:share-scales #{:x},
    :layout {:direction :horizontal, :weights [1 1]},
    :poses
    [{:mapping {:x :sepal-length, :y :sepal-width},
      :layers [{:layer-type :point}]}
     {:mapping {:x :sepal-length, :y :petal-length},
      :layers [{:layer-type :point}]}],
    :data (rdatasets/datasets-iris)})))


(def v18_l118 shared-x)


(deftest
 t19_l120
 (is
  ((fn
    [v]
    (let
     [s (pj/svg-summary v)]
     (and (= 2 (:panels s)) (= 300 (:points s)))))
   v18_l118)))


(def
 v21_l134
 (def
  marginal
  (pj/pose
   {:share-scales #{:x},
    :layout {:direction :vertical, :weights [1 3]},
    :poses
    [{:mapping {:x :sepal-length}, :layers [{:layer-type :density}]}
     {:mapping {:x :sepal-length, :y :sepal-width, :color :species},
      :layers [{:layer-type :point}]}],
    :data (rdatasets/datasets-iris)})))


(def v22_l144 marginal)


(deftest
 t23_l146
 (is
  ((fn
    [v]
    (let
     [s
      (pj/svg-summary v)
      panels
      (mapv
       (fn* [p1__71051#] (-> p1__71051# :plan :panels first))
       (:sub-plots (pj/plan marginal)))
      [d-x s-x]
      (mapv :x-domain panels)
      [d-y s-y]
      (mapv :y-domain panels)]
     (and
      (= 2 (:panels s))
      (= 150 (:points s))
      (pos? (:polygons s))
      (= d-x s-x)
      (not= d-y s-y))))
   v22_l144)))


(def
 v25_l175
 (def
  dashboard
  (pj/arrange
   [[(-> (rdatasets/datasets-iris) (pj/lay-histogram :sepal-length))
     (->
      (rdatasets/datasets-iris)
      (pj/lay-boxplot :species :sepal-width {:color :species}))]
    [(->
      (rdatasets/datasets-iris)
      (pj/lay-point :petal-length :petal-width {:color :species}))
     (->
      (rdatasets/datasets-iris)
      (pj/lay-density :petal-length {:color :species}))]])))


(def v26_l182 dashboard)


(deftest
 t27_l184
 (is
  ((fn
    [v]
    (let
     [chrome (-> dashboard pj/plan :chrome)]
     (and
      (= 4 (:panels (pj/svg-summary v)))
      (= #{} (:shared-aesthetics chrome)))))
   v26_l182)))


(def
 v29_l220
 (def overlay-base (tc/dataset {:fitted [1 2 3], :residual [1 2 3]})))


(def
 v30_l224
 (def overlay-other (tc/dataset {:x [0.5 1.5 2.5], :y [1.5 2.5 3.5]})))


(def
 v31_l228
 (->
  overlay-base
  (pj/lay-point :fitted :residual)
  (pj/lay-point
   :fitted
   :residual
   {:data
    (tc/rename-columns overlay-other {:x :fitted, :y :residual})})))


(deftest
 t32_l234
 (is
  ((fn
    [v]
    (let
     [s (pj/svg-summary v)]
     (and (= 1 (:panels s)) (= 6 (:points s)))))
   v31_l228)))


(def
 v34_l254
 (->
  overlay-base
  (pj/lay-point :fitted :residual)
  (pj/lay-point :x :y {:data overlay-other})))


(deftest
 t35_l258
 (is
  ((fn
    [v]
    (let
     [s (pj/svg-summary v)]
     (and (= 2 (:panels s)) (= 6 (:points s)))))
   v34_l254)))


(def
 v37_l284
 (def
  bar-counts
  {:species ["setosa" "versicolor" "virginica"],
   :pct [33.3 33.3 33.3]}))


(def
 v38_l288
 (def
  bar-labels
  {:species ["setosa" "versicolor" "virginica"],
   :pct [16.6 16.6 16.6],
   :label ["33.3" "33.3" "33.3"]}))


(def
 v39_l293
 (->
  bar-counts
  (pj/lay-value-bar :species :pct {:color "#a6cee3"})
  (pj/lay-text :species :pct {:text :label, :data bar-labels})
  (pj/coord :flip)))


(deftest
 t40_l298
 (is
  ((fn
    [fr]
    (=
     [:rect :text]
     (->> fr pj/plan :panels first :layers (mapv :mark))))
   v39_l293)))


(deftest
 t42_l307
 (is
  ((fn
    [_]
    (let
     [marks
      (fn
       [pose]
       (->> pose pj/plan :panels first :layers (mapv :mark)))]
     (and
      (=
       [:rect :text]
       (marks
        (->
         bar-counts
         (pj/lay-value-bar :species :pct)
         (pj/lay-text
          :species
          :pct
          {:text :label, :data bar-labels}))))
      (=
       [:text :rect]
       (marks
        (->
         bar-counts
         (pj/lay-text :species :pct {:text :label, :data bar-labels})
         (pj/lay-value-bar :species :pct)))))))
   v39_l293)))


(deftest
 t44_l351
 (is
  ((fn
    [_]
    (let
     [iris
      (rdatasets/datasets-iris)
      all-color
      (pj/arrange
       [(->
         iris
         (pj/lay-point :sepal-length :sepal-width {:color :species}))
        (->
         iris
         (pj/lay-point :petal-length :petal-width {:color :species}))])
      mixed
      (pj/arrange
       [(-> iris (pj/lay-histogram :sepal-length))
        (->
         iris
         (pj/lay-point
          :petal-length
          :petal-width
          {:color :species}))])]
     (and
      (= #{:color} (-> all-color pj/plan :chrome :shared-aesthetics))
      (= #{} (-> mixed pj/plan :chrome :shared-aesthetics)))))
   v39_l293)))
