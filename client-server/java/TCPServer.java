import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
  private static void log(String content) {
    System.out.print("[Server] " + content + "\n");
  }
  private static void err(String content) {
    System.err.print("[Server] " + content + "\n");
  }
  public static void main(String args[]) {
    // Simple sanity check.
    if (args.length != 1) {
      err("Usage: TCPServer <port>");
      System.exit(1);
    }
    // Get server port.
    int port = Integer.parseInt(args[0]);

    // Start the server.
    try (ServerSocket listener = new ServerSocket(port)) {
      while (true) {
        try (Socket socket = listener.accept()) {
          DataInputStream in = new DataInputStream(socket.getInputStream());
          int data = in.readInt();
          log("Read number " + data + " from a client.");
        }
      }
    } catch (IOException e) {
      err("Unexpected IOException occurs.");
      e.printStackTrace();
      System.exit(1);
    }
  }
}