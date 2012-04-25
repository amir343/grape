package com.jayway.textmining

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

case class Document(fileName:String, content:String, nouns:List[String]) extends FeatureVector {

  lazy val uniqueNouns:Set[String] = nouns.toSet

  // number of occurrences of each unique noun
  lazy val nonUniqueWeightedTerms:Map[String, Double] = { for {
    term <- uniqueNouns
    weight = nouns.count( _ == term ).asInstanceOf[Double]
  } yield (term, weight) }.map(identity)(collection.breakOut)

  lazy val weightedTerms:Map[String, Double] = nonUniqueWeightedTerms.foldLeft(nonUniqueWeightedTerms) {
    (acc, pair:(String, Double)) =>
      acc.find( currentItem => isPlural(currentItem._1, pair._1)) match {
        case Some((item, value)) =>
          val nacc = acc - pair._1
          val newWeight = value + pair._2
          nacc + (item -> newWeight)
        case None => acc
      }
  }.toMap

  lazy val weightedSum:Double = scala.math.sqrt(weightedTerms.map( (wt) => wt._2 * wt._2 ).sum)

  private def isPlural(currentItem:String, pair:String):Boolean = {
    pair.diff(currentItem) == "s" || pair.diff(currentItem) == "ies"
  }
}
