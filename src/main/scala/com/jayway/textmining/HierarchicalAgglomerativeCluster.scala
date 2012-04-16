package com.jayway.textmining

import com.weiglewilczek.slf4s.Logging
import collection.mutable
import scalaz.{Failure, Success}
import java.io.File

/**
 * Copyright 2012 Amir Moulavi (amir.moulavi@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * @author Amir Moulavi
 */

class HierarchicalAgglomerativeCluster(k:Int, files:List[File])
  extends Logging
  with FileReader { this:FeatureSelection =>

  require(k < files.size, "K can not be greater than number of documents")
  require(k > 0, "K must be a positive non-zero integer")

  def getK = k

  val fileContents:List[String] = readFiles(files) match {
    case Success(x) => x
    case Failure(x) => throw new RuntimeException(x)
  }

  val documents:List[Document] = selectFeatures(files.map( f => f.getName ).zip(fileContents))

  val clusters:List[Cluster] = documents.map( d => Cluster(d) )

  val vectorSpace = VectorSpace()
  documents.foreach( d => vectorSpace.addDimension(d.uniqueNouns))

  val mathUtils = MathUtils(vectorSpace)

  def clusterDocuments():List[Cluster] = {
    val clusterVector = mutable.ListBuffer[Cluster]()
    clusters.foreach { c => clusterVector += c }
    while ( clusterVector.size > k ) {
      val combinations = for {
        i <- 0 until clusterVector.size
        j <- i+1 until clusterVector.size
        c1 = clusterVector(i)
        c2 = clusterVector(j)
      } yield (c1, c2, mathUtils.euclideanDistance(c1.centroid, c2.centroid))
      val (cluster1, cluster2, _) = combinations.sortWith( (comb1, comb2) => comb1._3.compareTo(comb2._3) < 0).head
      cluster1.mergeWith(cluster2)
      clusterVector -= cluster2
      clusterVector.foreach { c => c.calculateNewCentroid() }
    }
    clusterVector.toList
  }

}
