/*
 * Copyright 2016 Red Hat, Inc.
 *
 * Red Hat licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package io.vertx.ext.cluster.infinispan.impl;

/**
 * @author Thomas Segismont
 */
public class DataConverter {

  public static <T> Object toCachedObject(T t) {
    return t;
  }

  @SuppressWarnings("unchecked")
  public static <T> T fromCachedObject(Object value) {
    return (T) value;
  }

  private DataConverter() {
    // Utility class
  }
}
