package com.jayway.textmining

import java.io.File
import org.apache.commons.io.FileUtils

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

trait TestData {

  private val resourcesDir = new File("src/test/resources")
  private val files:List[File] = resourcesDir.listFiles().toList
  private val docs = files.map( f => (f.getName, FileUtils.readFileToString(f, "UTF-8")))
  private val nlp = new NLP

  val documents:List[Document] = docs.map ( t => nlp.createDocumentFrom(t._1, t._2) )

}