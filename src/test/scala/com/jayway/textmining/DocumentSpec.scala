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

class DocumentSpec extends Specification {

  "Document" should {
    "replace plural nouns with singular and new weight" in {
      val nouns = List("tags", "tag", "tags", "health", "Amir", "tagging", "system")
      val doc = Document("testDocument", "All possible tags should be separated into individual tag. Tags are good for your health. Amir has different tagging system", nouns)
      doc.weightedTerms.get("tag").get mustEqual 3.0
      doc.weightedTerms.get("health").get mustEqual 1.0
      doc.weightedTerms.get("Amir").get mustEqual 1.0
      doc.weightedTerms.get("system").get mustEqual 1.0
      doc.uniqueNouns.size mustEqual 6
    }
  }

}
