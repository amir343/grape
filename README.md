Grape
=====

Grape is a collection of document [clustering](http://en.wikipedia.org/wiki/Data_clustering) algorithms written in Scala.
It avails from [Apache OpenNLP](http://opennlp.apache.org/) to extract specific feature from the each document and build
the final vector space that is used in different approaches. Grape contains the following algorithms (at the moment):

* KMean Clustering
* Hierarchical Agglomerative Clustering
* Buckshot Clustering

How to use
==========

An example how to use KMean clustering on your documents:

```scala
import com.jayway.textmining.{NLPFeatureSelection, Cluster, KMeanCluster}

// number of clusters
val k = ...

// A document is a pair of (Document ID, Document Content). ID can be anything.
val docs: List[(String, String)] = ...

val kMeanCluster = new KMeanCluster(docs, k) with NLPFeatureSelection
val clusters:List[Cluster] = kMeanCluster.doCluster()
```

License
-------

Copyright (C) 2012 [Amir Moulavi](http://amirmoulavi.com)

Distributed under the [Apache Software License](http://www.apache.org/licenses/LICENSE-2.0.html).
