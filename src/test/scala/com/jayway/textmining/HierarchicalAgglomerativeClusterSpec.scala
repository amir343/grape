package com.jayway.textmining

import org.specs2.mutable.Specification

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

class HierarchicalAgglomerativeClusterSpec
  extends Specification
  with TestData {

  val hac = new HierarchicalAgglomerativeCluster(docs, 2) with NLPFeatureSelection
  val clusters:List[Cluster] = hac.clusterDocuments()

  "cluster size in HAC algorithm" should {
    "equal sqrt(kn)" in {
      clusters.size mustEqual hac.getK
      success
    }
  }

  "document number in all clusters in HAC algorithm" should {
    "equal the initial number of documents" in {
      clusters.map( c => c.currentDocs.size ).sum mustEqual documents.size
    }
  }

}
