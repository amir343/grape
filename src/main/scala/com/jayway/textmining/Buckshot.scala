package com.jayway.textmining

import com.weiglewilczek.slf4s.Logging
import java.io.File
import scalaz.{Failure, Success}

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

class Buckshot(files:List[(String, String)], k:Int)
  extends RandomSelector
  with Logging { this:FeatureSelection =>

  require(k < files.size, "K can not be greater than number of documents")
  require(k > 0, "K must be a positive non-zero integer")

  val fileContents:List[String] = files.map( _._2 )

  val documents:List[Document] = selectFeatures(files)

  val vectorSpace = VectorSpace()
  documents.foreach( d => vectorSpace.addDimension(d.uniqueNouns))

  val mathUtils = MathUtils(vectorSpace)

  def clusterDocument:List[Cluster] = {
    val selectedFiles = `select kd random document`
    val remainingFiles = files diff selectedFiles
    val remainingDocs = convertFilesToDocuments(remainingFiles)
    val clusters = (new HierarchicalAgglomerativeCluster(selectedFiles, k) with NLPFeatureSelection).clusterDocuments()
    clusters.foreach( c => c.calculateNewCentroid() )
    remainingDocs.foreach { d =>
      val distances = clusters.map( c => (c, mathUtils.euclideanDistance(d, c.centroid)) )
      val closestCluster = distances.sortWith( (c1, c2) => c1._2.compareTo(c2._2) < 0).head._1
      closestCluster.addDocument(d)
    }
    clusters
  }

  private def convertFilesToDocuments(list:List[(String, String)]):List[Document] = {
    selectFeatures(list)
  }

  private def `select kd random document`:List[(String, String)] =
    selectRandom[(String, String)](math.sqrt((k*documents.size).asInstanceOf[Double]).asInstanceOf[Int], files)
}
