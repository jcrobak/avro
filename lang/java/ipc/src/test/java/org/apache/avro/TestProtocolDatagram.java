/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.avro;

import java.util.Random;
import org.apache.avro.ipc.DatagramServer;
import org.apache.avro.ipc.DatagramTransceiver;
import org.apache.avro.specific.SpecificRequestor;
import org.apache.avro.specific.SpecificResponder;
import org.apache.avro.test.Simple;
import org.junit.Before;

import java.net.InetSocketAddress;

public class TestProtocolDatagram extends TestProtocolSpecific {

  @Before @Override
  public void testStartServer() throws Exception {
    if (server != null) return;
    server =
      new DatagramServer(new SpecificResponder(Simple.class, new TestImpl()),
                         new InetSocketAddress("localhost",
                                               new Random().nextInt(10000)+10000));
    server.start();
    client = new DatagramTransceiver(new InetSocketAddress("localhost", server.getPort()));
    proxy = SpecificRequestor.getClient(Simple.class, client);
  }

}
