import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;

import com.sun.corba.se.impl.ior.ByteBuffer;

public class TCPServer {
  private String nodeId;
  private int listeningPort;
  private int messageCount;

  public TCPServer(String id, int port, int count) {
    nodeId = id;
    listeningPort = port;
    messageCount = count;
  }

  private void log(String content) {
    System.out.print("[server@" + nodeId + "] " + content + "\n");
  }
  private void err(String content) {
    System.err.print("[server@" + nodeId + "] " + content + "\n");
  }

  public void run() {
    // Start the server.
    try (ServerSocket listener = new ServerSocket(listeningPort)) {
      for (int msgIdx = 0; msgIdx < messageCount; ++msgIdx) {
        // Read one message.
        try (Socket socket = listener.accept()) {
          // Increment the vector clock when we receive something.
          VectorClock.increment(nodeId);

          // Receive the remote vector clock.
          DataInputStream in = new DataInputStream(socket.getInputStream());
          int messageSize = in.readInt();
          ByteBuffer buf = ByteBuffer.allocate(messageSize);
          for (int byteIdx = 0; byteIdx < messageSize; ++ byteIdx) {
            buf.put(in.readByte());
          }
          HashMap<String, Integer> remoteClock = VectorClock.deserialize(buf);
          log("Received " + VectorClock.stringVerionOf(remoteClock));

          // Merge the vector clock.
          VectorClock.merge(remoteClock);
          log("Current vector clock is " + VectorClock.stringVersion());
        }
      }
    } catch (IOException e) {
      err("Unexpected IOException occurs.");
      e.printStackTrace();
      System.exit(1);
    }
  }
}