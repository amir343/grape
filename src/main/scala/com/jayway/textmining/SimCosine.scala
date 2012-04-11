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

case class SimCosine(vectorSpace:VectorSpace) {

  def sim(document1:Document, document2:Document):Double = {
    val weightedTermsSumProduction = vectorSpace.dimensions.map( term => document1.weightedTerms.getOrElse(term, 0) * document2.weightedTerms.getOrElse(term, 0)).sum
    weightedTermsSumProduction / (document1.weightedSum * document2.weightedSum)
  }

}
