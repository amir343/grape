package com.jayway.textmining

import java.io.{FileInputStream, InputStream}
import opennlp.tools.postag.{POSTaggerME, POSModel}
import opennlp.tools.tokenize.{TokenizerME, TokenizerModel}
import scala.collection.mutable
import com.weiglewilczek.slf4s.Logging


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
class NLP extends Logging {
  import NLP._

  val tokenizer = new TokenizerME(tokenizerModel)
  val tagger = new POSTaggerME(posModel)

  def createDocumentFrom(fileName:String, content:String):Document = Document(fileName, content, processFile(fileName, content))

  def processFile(fileName:String, content:String):List[String] = {
    require(posModelIn != null && tokenizerModelIn != null, "NLP pos and/or tokenizer are not initialized properly")
    val nouns:mutable.ListBuffer[String] = mutable.ListBuffer[String]()
    val sent = tokenize(fileName, content)
    val tags = posTag(fileName, sent)
    for (i <- 0 until tags.length) {
      if (tags(i) == "NN" || tags(i) == "NNS") nouns += sent(i).toLowerCase
      else if (tags(i) == "NNP") nouns += sent(i).toLowerCase
    }
    logger.info("%s noun(s) is extracted from the document '%s'".format(nouns.size, fileName))
    nouns.toList
  }

  def tokenize(fileName:String, content:String):Array[String] = {
    logger.info("Tokenizing document '%s'".format(fileName))
    val tokens = tokenizer.tokenize(content)
    logger.info("Done tokenizing")
    tokens
  }

  def posTag(fileName:String, sent:Array[String]):Array[String] = {
    logger.info("PoS tagging document '%s'".format(fileName))
    val tags = tagger.tag(sent)
    logger.info("Done PoS tagging")
    tags
  }

}

object NLP extends Logging {
  var posModelIn:InputStream = null
  var tokenizerModelIn:InputStream = null
  var posModel:POSModel = null
  var tokenizerModel:TokenizerModel = null

  try {
    tokenizerModelIn = new FileInputStream("src/main/resources/en-token.bin")
    tokenizerModel = new TokenizerModel(tokenizerModelIn)
    posModelIn = new FileInputStream("src/main/resources/en-pos-maxent.bin")
    posModel = new POSModel(posModelIn)
  } catch {
    case e =>
      e.printStackTrace()
      logger.error("Could not load model files")
  }
  finally {
    if (posModelIn != null && tokenizerModelIn != null) {
      try {
        posModelIn.close()
        tokenizerModelIn.close()
      }
      catch {
        case e =>
          e.printStackTrace()
          logger.error("Could not close model files")
      }
    }
  }

}
