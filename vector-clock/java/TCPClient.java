import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ThreadLocalRandom;

public class TCPClient extends Thread {
  private String myId;
  private String remoteIp;
  private int remotePort;

  public TCPClient(String local, String rIp, int rPort) {
    myId = local;
    remoteIp = rIp;
    remotePort = rPort;
  }

  private void log(String content) {
    System.out.print("[client@" + myId + "] " + content + "\n");
  }
  private void err(String content) {
    System.err.print("[client@" + myId + "] " + content + "\n");
  }

  public void run() {
    // Sleep for a random time.
    Thread.sleep(ThreadLocalRandom.current().nextLong() % 1000);

    try (Socket socket = new Socket(remoteIp, remotePort)) {
      // Increment the current clock value.
      VectorClock.increment(myId);

      // Send it to others.
    }
  }
}