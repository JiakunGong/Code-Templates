import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Node {
  private String id;

  private static void log(String content) {
    System.out.print("[Server] " + content + "\n");
  }
  private static void err(String content) {
    System.err.print("[Server] " + content + "\n");
  }
  public static void main(String args[]) {
    // Load configuration from the command line.
    if (args.length != 4) {
      err("Usage: Node <node-id> <local-port> <remote-port> <send-count>");
      System.exit(1);
    }
    String nodeId = args[0];
    int localPort = Integer.parseInt(args[1]);
    int remotePort = Integer.parseInt(args[2]);
    int sendCount = Integer.parseInt(args[3]);

    // Start the server thread.

    // Start the client threads.
  }
}