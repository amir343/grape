package com.jayway.textmining

import collection.mutable
import com.weiglewilczek.slf4s.Logging

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

case class KMeanCluster(documents:List[Document], k:Int)
  extends RandomSelector
  with Logging {

  require( k < documents.size, "'k' can not be greater than the document size" )

  val iterations:Int = 100
  logger.info("Number of iterations: %s".format(iterations))

  val documentMap = mutable.Map[Document, Cluster]()

  val clusters:List[Cluster] = selectRandomInitialCluster(k, documents)(documentMap)

  val vectorSpace = VectorSpace()
  documents.foreach( d => vectorSpace.addDimension(d.uniqueNouns))
  logger.info("Vector space dimensions: %s".format(vectorSpace.dimensions.size))

  val mathUtils = MathUtils(vectorSpace)

  def doCluster():List[Cluster] = {
    for ( i <- 0 until iterations) {
      assignDocumentsToClusters()
      updateCentroids()
    }
    clusters
  }

  private def assignDocumentsToClusters() {
    documents.foreach { doc =>
      val distances:List[(Cluster, Double)] = clusters.map( c => (c, mathUtils.euclideanDistance(c.centroid, doc)))
      val minimumDistance = distances.sortWith( (p1, p2) => p1._2.compareTo(p2._2) < 0 ).head
      val selectedCluster = minimumDistance._1
      documentMap.get(doc) match {
        case Some(c) => c.removeDocument(doc)
        case None    =>
      }
      selectedCluster.addDocument(doc)
      documentMap += (doc -> selectedCluster)
    }
  }

  private def updateCentroids() {
    clusters.foreach(_.calculateNewCentroid())
  }

}