/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015-2017 Helge Holzmann (L3S) and Vinay Goel (Internet Archive)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package de.l3s.archivespark.enrich

import de.l3s.archivespark.utils.Json._

class SingleValueEnrichable[T] private (override val get: T, parentEnrichable: Enrichable = null, field: String = null, enrichRoot: EnrichRoot = null) extends TypedEnrichable[T] {
  setHierarchy(parentEnrichable, field, enrichRoot)

  def toJson: Map[String, Any] = (if (isExcludedFromOutput) Map() else Map(
    null.asInstanceOf[String] -> json(this.get)
  )) ++ enrichments.map{e => (e, mapToJsonValue(enrichment(e).get.toJson))}.filter{ case (_, field) => field != null }
}

object SingleValueEnrichable {
  def apply[T](value: T, parent: Enrichable = null, field: String = null, root: EnrichRoot = null): SingleValueEnrichable[T] = new SingleValueEnrichable[T](value, parent, field, root)
}