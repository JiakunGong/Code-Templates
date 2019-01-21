import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPClient {
  private static void log(String content) {
    System.out.print("[Client] " + content + "\n");
  }
  private static void err(String content) {
    System.err.print("[Client] " + content + "\n");
  }

  public static void main(String args[]) {
    // Simple sanity check.
    if (args.length != 3) {
      err("Usage: java TCPClient <server-ip> <server-port> <packet-count>");
      return;
    }

    // Get server's address from arguments.
    String serverIp = args[0];
    int serverPort = Integer.parseInt(args[1]);

    // Get the number of packets to send.
    int packetCnt = Integer.parseInt(args[2]);

    for (int i = 0; i < packetCnt; ++i) {
      try (Socket socket = new Socket(serverIp, serverPort)) {
        // Send packets to the server.
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        out.writeInt(i);
        log("Sent " + i + " to server@" + serverIp + ":" + args[1]);
      } catch (UnknownHostException e) {
        err("Cannot connect to server@" + serverIp + ":" + args[1]);
        System.exit(1);
      } catch (IOException e) {
        err("Unexpected IOException occurs.");
        e.printStackTrace();
        System.exit(1);
      }
    }
  }
}