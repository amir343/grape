package com.jayway.textmining

import org.specs2.mutable.Specification
import org.apache.commons.io.FileUtils
import java.io.File
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

  "Weighted Terms" should {
    "have extracted nouns with their equivalent number of occurrences" in {
      documents.foreach { d =>
        println(d.fileName)
        println(d.weightedTerms)
        println("***********************************************************")
      }
      success
    }
  }

  "KMean Clustering" should {
    "have exactly the same number of given K as its number of cluster at the end" in {
      val k = 2
      val kmeanCluster = KMeanCluster(documents, k)
      val clusters = kmeanCluster.doCluster()
      clusters.size mustEqual k
      success
    }

    "the total number of documents should be the same as total number of documents in each cluster" in {
      val k = 3
      val kmeanCluster = KMeanCluster(documents, k)
      val clusters = kmeanCluster.doCluster()
      clusters.map(_.currentDocs.size).sum mustEqual documents.size
      success
    }
  }

}
