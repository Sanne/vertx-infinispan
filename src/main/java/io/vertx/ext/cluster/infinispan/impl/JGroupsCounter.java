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

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.shareddata.Counter;

import java.util.Objects;

/**
 * @author Thomas Segismont
 */
public class JGroupsCounter implements Counter {

  private final Vertx vertx;
  private final org.jgroups.blocks.atomic.Counter jgroupsCounter;

  public JGroupsCounter(Vertx vertx, org.jgroups.blocks.atomic.Counter jgroupsCounter) {
    this.vertx = vertx;
    this.jgroupsCounter = jgroupsCounter;
  }

  @Override
  public void get(Handler<AsyncResult<Long>> resultHandler) {
    Objects.requireNonNull(resultHandler, "resultHandler");
    vertx.executeBlocking(future -> {
      future.complete(jgroupsCounter.get());
    }, false, resultHandler);
  }

  @Override
  public void incrementAndGet(Handler<AsyncResult<Long>> resultHandler) {
    Objects.requireNonNull(resultHandler, "resultHandler");
    addAndGet(1L, resultHandler);
  }

  @Override
  public void getAndIncrement(Handler<AsyncResult<Long>> resultHandler) {
    Objects.requireNonNull(resultHandler, "resultHandler");
    getAndAdd(1L, resultHandler);
  }

  @Override
  public void decrementAndGet(Handler<AsyncResult<Long>> resultHandler) {
    Objects.requireNonNull(resultHandler, "resultHandler");
    addAndGet(-1L, resultHandler);
  }

  @Override
  public void addAndGet(long value, Handler<AsyncResult<Long>> resultHandler) {
    Objects.requireNonNull(resultHandler, "resultHandler");
    vertx.executeBlocking(future -> {
      future.complete(jgroupsCounter.addAndGet(value));
    }, false, resultHandler);
  }

  @Override
  public void getAndAdd(long value, Handler<AsyncResult<Long>> resultHandler) {
    Objects.requireNonNull(resultHandler, "resultHandler");
    vertx.executeBlocking(future -> {
      future.complete(jgroupsCounter.addAndGet(value) - value);
    }, false, resultHandler);
  }

  @Override
  public void compareAndSet(long expected, long value, Handler<AsyncResult<Boolean>> resultHandler) {
    Objects.requireNonNull(resultHandler, "resultHandler");
    vertx.executeBlocking(future -> {
      future.complete(jgroupsCounter.compareAndSet(expected, value));
    }, false, resultHandler);
  }
}
