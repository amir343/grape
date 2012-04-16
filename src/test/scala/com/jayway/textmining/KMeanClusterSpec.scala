package com.jayway.textmining

import org.specs2.mutable.Specification
import scala.collection.JavaConverters._
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

class KMeanClusterSpec extends Specification with TestData {

  val k = 2
  val kmeanCluster = new KMeanCluster(files, k) with NLPFeatureSelection
  val clusters = kmeanCluster.doCluster()

  "KMean Clustering" should {
    "have exactly the same number of given K as its number of cluster at the end" in {
      clusters.size mustEqual k
    }
  }

  "Kmean Clustering" should {
    "the total number of documents should be the same as total number of documents in each cluster" in {
      clusters.map(_.currentDocs.size).sum mustEqual documents.size
    }
  }

}
