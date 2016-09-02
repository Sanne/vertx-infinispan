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
 * Identifiers used by the Marshaller to delegate to specialized Externalizers.
 * For details, read http://infinispan.org/docs/9.0.x/user_guide/user_guide.html#preassigned_externalizer_id_ranges
 *
 * The range reserved for this modules is from 2000 to 2099.
 *
 * @author Sanne Grinovero
 */
public interface ExternalizerIds {

  /**
   * @see io.vertx.ext.cluster.infinispan.impl.ServerIDExternalizer
   */
  static final Integer SERVER_ID = 2000;

  /**
   * @see io.vertx.ext.cluster.infinispan.impl.ClusterSerializableExternalizer
   */
  static final Integer CLUSTER_SERIALIZABLE = 2001;

}
