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

package io.vertx.ext.cluster.infinispan.test;

import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.ext.cluster.infinispan.InfinispanClusterManager;
import io.vertx.test.core.HATest;
import org.infinispan.configuration.global.GlobalConfiguration;

/**
 * @author Thomas Segismont
 */
public class InfinispanHATest extends HATest {
  @Override
  protected ClusterManager getClusterManager() {
    GlobalConfiguration globalConfiguration = ConfigUtil.createGlobalConfigurationForTesting();
    return new InfinispanClusterManager(globalConfiguration);
  }

}