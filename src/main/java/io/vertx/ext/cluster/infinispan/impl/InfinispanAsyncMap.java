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
import io.vertx.core.Context;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.shareddata.AsyncMap;
import org.infinispan.Cache;

import static java.util.concurrent.TimeUnit.*;

/**
 * @author Thomas Segismont
 */
public class InfinispanAsyncMap<K, V> implements AsyncMap<K, V> {

  private final Vertx vertx;
  private final Cache<Object, Object> cache;

  public InfinispanAsyncMap(Vertx vertx, Cache<Object, Object> cache) {
    this.vertx = vertx;
    this.cache = cache;
  }

  @Override
  public void get(K k, Handler<AsyncResult<V>> resultHandler) {
    Context context = vertx.getOrCreateContext();
    FutureAdapter<Object> futureAdapter = new FutureAdapter<>(context);
    cache.getAsync(k).attachListener(futureAdapter);
    futureAdapter.getVertxFuture().map(DataConverter::<V>fromCachedObject).setHandler(resultHandler);
  }

  @Override
  public void put(K k, V v, Handler<AsyncResult<Void>> completionHandler) {
    Context context = vertx.getOrCreateContext();
    FutureAdapter<Object> futureAdapter = new FutureAdapter<>(context);
    cache.putAsync(k, v).attachListener(futureAdapter);
    futureAdapter.getVertxFuture().map((Void) null).setHandler(completionHandler);
  }

  @Override
  public void put(K k, V v, long ttl, Handler<AsyncResult<Void>> completionHandler) {
    Context context = vertx.getOrCreateContext();
    FutureAdapter<Object> futureAdapter = new FutureAdapter<>(context);
    cache.putAsync(k, v, ttl, MILLISECONDS).attachListener(futureAdapter);
    futureAdapter.getVertxFuture().map((Void) null).setHandler(completionHandler);
  }

  @Override
  public void putIfAbsent(K k, V v, Handler<AsyncResult<V>> completionHandler) {
    Context context = vertx.getOrCreateContext();
    FutureAdapter<Object> futureAdapter = new FutureAdapter<>(context);
    cache.putIfAbsentAsync(k, v).attachListener(futureAdapter);
    futureAdapter.getVertxFuture().map(DataConverter::<V>fromCachedObject).setHandler(completionHandler);
  }

  @Override
  public void putIfAbsent(K k, V v, long ttl, Handler<AsyncResult<V>> completionHandler) {
    Context context = vertx.getOrCreateContext();
    FutureAdapter<Object> futureAdapter = new FutureAdapter<>(context);
    cache.putIfAbsentAsync(k, v, ttl, MILLISECONDS).attachListener(futureAdapter);
    futureAdapter.getVertxFuture().map(DataConverter::<V>fromCachedObject).setHandler(completionHandler);
  }

  @Override
  public void remove(K k, Handler<AsyncResult<V>> resultHandler) {
    Context context = vertx.getOrCreateContext();
    FutureAdapter<Object> futureAdapter = new FutureAdapter<>(context);
    cache.removeAsync(k).attachListener(futureAdapter);
    futureAdapter.getVertxFuture().map(DataConverter::<V>fromCachedObject).setHandler(resultHandler);
  }

  @Override
  public void removeIfPresent(K k, V v, Handler<AsyncResult<Boolean>> resultHandler) {
    Context context = vertx.getOrCreateContext();
    FutureAdapter<Boolean> futureAdapter = new FutureAdapter<>(context);
    cache.removeAsync(k, v).attachListener(futureAdapter);
    futureAdapter.getVertxFuture().setHandler(resultHandler);
  }

  @Override
  public void replace(K k, V v, Handler<AsyncResult<V>> resultHandler) {
    Context context = vertx.getOrCreateContext();
    FutureAdapter<Object> futureAdapter = new FutureAdapter<>(context);
    cache.replaceAsync(k, v).attachListener(futureAdapter);
    futureAdapter.getVertxFuture().map(DataConverter::<V>fromCachedObject).setHandler(resultHandler);
  }

  @Override
  public void replaceIfPresent(K k, V oldValue, V newValue, Handler<AsyncResult<Boolean>> resultHandler) {
    Context context = vertx.getOrCreateContext();
    FutureAdapter<Boolean> futureAdapter = new FutureAdapter<>(context);
    cache.replaceAsync(k, oldValue, newValue).attachListener(futureAdapter);
    futureAdapter.getVertxFuture().setHandler(resultHandler);
  }

  @Override
  public void clear(Handler<AsyncResult<Void>> resultHandler) {
    Context context = vertx.getOrCreateContext();
    FutureAdapter<Void> futureAdapter = new FutureAdapter<>(context);
    cache.clearAsync().attachListener(futureAdapter);
    futureAdapter.getVertxFuture().setHandler(resultHandler);
  }

  @Override
  public void size(Handler<AsyncResult<Integer>> resultHandler) {
    vertx.executeBlocking(future -> future.complete(cache.size()), false, resultHandler);
  }
}
