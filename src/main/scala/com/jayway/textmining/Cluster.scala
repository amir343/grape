package com.jayway.textmining

import scala.collection.mutable
import scalaz._
import Scalaz._

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

case class Cluster(initialDoc:Document) {

  private val docs:mutable.ListBuffer[Document] = mutable.ListBuffer[Document]()
  var centroid:Centroid = Centroid(initialDoc.weightedTerms)

  docs += initialDoc

  def addDocument(doc:Document) {
    docs += doc
  }

  def removeDocument(doc:Document) {
    docs -= doc
  }

  def calculateNewCentroid() {
    if (docs.size > 0)
      centroid = Centroid(docs.map( _.weightedTerms ).reduce(_ |+| _).mapValues( _ / docs.size.asInstanceOf[Double]))
  }

  def mergeWith(cluster:Cluster) {
    cluster.currentDocs.foreach( d => this.addDocument(d) )
  }

  def resetDocuments() {
    docs.clear()
  }

  def currentDocs:List[Document] = docs.toList

}
