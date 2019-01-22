import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
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
    try {
      long sleepTime = ThreadLocalRandom.current().nextLong(1000) + 1;
      log("Sleep for " + sleepTime + " milliseconds before sending message.");
      Thread.sleep(sleepTime);
    } catch (InterruptedException e) {
      err("Interrupted while sleeping. Exits.");
      System.exit(1);
    }

    try (Socket socket = new Socket(remoteIp, remotePort)) {
      // Increment the current clock value.
      VectorClock.increment(myId);

      // Send it to others.
      DataOutputStream out = new DataOutputStream(socket.getOutputStream());
      ByteBuffer buf = VectorClock.serialize();
      int size = buf.capacity();
      out.writeInt(size);
      for (int byteIdx = 0; byteIdx < size; ++byteIdx) {
        out.writeByte(buf.get());
      }
    } catch (UnknownHostException e) {
      err("Cannot connect to server@" + remoteIp + ":" + remotePort);
      System.exit(1);
    } catch (IOException e) {
      err("Unexpected IOException occurs.");
      e.printStackTrace();
      System.exit(1);
    }
  }
}