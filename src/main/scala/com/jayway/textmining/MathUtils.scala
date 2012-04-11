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

case class MathUtils(vectorSpace:VectorSpace) {

  def simCosine(doc1:FeatureVector, doc2:FeatureVector):Double = {
    val weightedTermsSumProduction = vectorSpace.dimensions.map( term => doc1.weightedTerms.getOrElse(term, 0.0) * doc2.weightedTerms.getOrElse(term, 0.0)).sum
    weightedTermsSumProduction / (doc1.weightedSum * doc2.weightedSum)
  }

  def euclideanDistance(doc1:FeatureVector, doc2:FeatureVector):Double = 1 - simCosine(doc1, doc2)

}
