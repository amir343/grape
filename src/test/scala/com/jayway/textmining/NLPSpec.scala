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

class NLPSpec extends Specification with TestData {

  val sentence = "All possible tags should be separated into individual tag. Tags are good for your health. Amir has different" +
    " tagging system."

  "NLP" should {
    "tokenize sentences correctly" in {
      val nlp = new NLP
      nlp.tokenize("test", sentence).size mustEqual 23
    }
    "posTag sentences correctly" in {
      val nlp = new NLP
      val tokens = nlp.tokenize("test", sentence)
      val posTagResult = nlp.posTag("test", tokens)
      for ( i <- 0 until tokens.size)
        println(tokens(i) + ": " + posTagResult(i))
      success
    }
    "extract nouns for all documents" in {
      val nlp = new NLP
      docs.foreach { f =>
        (nlp.processFile(f._1, f._2).size > 0) mustEqual true
      }
      success
    }
  }

}
