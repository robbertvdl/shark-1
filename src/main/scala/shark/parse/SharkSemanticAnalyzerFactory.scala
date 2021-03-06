/*
 * Copyright (C) 2012 The Regents of The University California. 
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package shark.parse

import org.apache.hadoop.hive.conf.HiveConf
import org.apache.hadoop.hive.ql.parse.{ASTNode, BaseSemanticAnalyzer, SemanticAnalyzerFactory,
  ExplainSemanticAnalyzer, SemanticAnalyzer}

import shark.SharkConfVars


object SharkSemanticAnalyzerFactory {

  /**
   * Return a semantic analyzer for the given ASTNode.
   */
  def get(conf: HiveConf, tree:ASTNode): BaseSemanticAnalyzer = {
    val baseSem = SemanticAnalyzerFactory.get(conf, tree)

    if (baseSem.isInstanceOf[SemanticAnalyzer]) {
      new SharkSemanticAnalyzer(conf)
    } else if (baseSem.isInstanceOf[ExplainSemanticAnalyzer] &&
        SharkConfVars.getVar(conf, SharkConfVars.EXPLAIN_MODE) == "shark") {
      new SharkExplainSemanticAnalyzer(conf)
    } else {
      baseSem
    }
  }
}

