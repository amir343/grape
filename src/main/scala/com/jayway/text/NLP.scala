package com.jayway.text

import java.io.{FileInputStream, InputStream}
import opennlp.tools.postag.{POSTaggerME, POSModel}
import opennlp.tools.tokenize.{TokenizerME, TokenizerModel}


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
class NLP {

  var posModelIn:InputStream = null
  var tokenizerModelIn:InputStream = null
  var posModel:POSModel = null
  var tokenizerModel:TokenizerModel = null

  def start(content:String) {
    try {
      tokenizerModelIn = new FileInputStream("src/main/resources/en-token.bin")
      tokenizerModel = new TokenizerModel(tokenizerModelIn)
      posModelIn = new FileInputStream("src/main/resources/en-pos-maxent.bin")
      posModel = new POSModel(posModelIn)
    }
    catch { case e => e.printStackTrace()}
    finally {
      if (posModelIn != null && tokenizerModelIn != null) {
        try {
          posModelIn.close()
          tokenizerModelIn.close()
        }
        catch { case e => e.printStackTrace() }
      }
    }
    val tokenizer = new TokenizerME(tokenizerModel)
    val sent = tokenizer.tokenize(content)
    val tagger = new POSTaggerME(posModel)
    val tags = tagger.tag(sent)
    for (i <- 0 until tags.length) {
      println(sent.apply(i) + ": " + tags.apply(i))
    }
  }


}
