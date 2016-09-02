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

import io.vertx.core.net.impl.ServerID;

import org.infinispan.commons.marshall.AdvancedExternalizer;
import org.infinispan.commons.util.Util;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Set;

/**
 * @author Thomas Segismont
 * @author Sanne Grinovero
 */
public class ServerIDExternalizer implements AdvancedExternalizer<ServerID> {

  @Override
  public void writeObject(ObjectOutput output, ServerID serverID) throws IOException {
    output.writeInt(serverID.port);
    output.writeUTF(serverID.host);
  }

  @Override
  public ServerID readObject(ObjectInput input) throws IOException, ClassNotFoundException {
    ServerID serverID = new ServerID(input.readInt(), input.readUTF());
    return serverID;
  }

  @Override
  public Set<Class<? extends ServerID>> getTypeClasses() {
    return Util.<Class<? extends ServerID>>asSet(ServerID.class);
  }

  @Override
  public Integer getId() {
    return ExternalizerIds.SERVER_ID;
  }

}
