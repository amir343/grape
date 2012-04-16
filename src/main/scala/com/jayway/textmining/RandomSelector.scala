package com.jayway.textmining

import scala.collection.mutable
import util.Random

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

trait RandomSelector {

  implicit val dMap:mutable.Map[Document, Cluster] = mutable.Map[Document, Cluster]()

  def selectRandomInitialCluster(K:Int, documents:List[Document])(implicit documentMap:mutable.Map[Document, Cluster]):List[Cluster] = {
    var docs = documents map identity
    val r = new Random
    val seeds = mutable.ListBuffer[Cluster]()
    for ( i <- 0 until K ) {
      val selected = docs(r.nextInt(K))
      docs = docs.filterNot(_ == selected)
      val cluster = Cluster(selected)
      documentMap += (selected -> cluster)
      seeds += cluster
    }
    seeds.toList
  }

  def selectRandom[A](K:Int, documents:List[A]):List[A] = {
    var docs = documents map identity
    val r = new Random
    val seeds = mutable.ListBuffer[A]()
    for ( i <- 0 until K ) {
      val selected = docs(r.nextInt(K))
      docs = docs.filterNot(_ == selected)
      seeds += selected
    }
    seeds.toList
  }


}