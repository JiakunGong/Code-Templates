import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

/**
 * This is a static version of vector clock.
 */
public class VectorClock {
  private static HashMap<String, Integer> clocks;

  /**
   * Incrementing the clock value of the specified node by 1.
   * If the given node does not exist, initialize it with 1.
   */
  public static synchronized void increment(String nodeId) {
    if (clocks.containsKey(nodeId)) {
      clocks.put(nodeId, clocks.get(nodeId) + 1);
    } else {
      clocks.put(nodeId, 1);
    }
  }

  /**
   * Merge the remote vector clock with ours.
   */
  public static synchronized void merge(HashMap<String, Integer> remoteClocks) {
    for (Map.Entry<String, Integer> nodeClock : remoteClocks.entrySet()) {
      // If we have a clock value of this node, keep the bigger one.
      if (clocks.containsKey(nodeClock.getKey())) {
        Integer localClock = clocks.get(nodeClock.getKey());
        Integer remoteClock = nodeClock.getValue();
        if (remoteClock > localClock) {
          clocks.put(nodeClock.getKey(), remoteClock);
        }
      } else {
        // Otherwise, copy from remote.
        clocks.put(nodeClock.getKey(), nodeClock.getValue());
      }
    }
  }

  /**
   * Serialize the vector clock and return as a ByteBuffer.
   */
  public static synchronized ByteBuffer serialize() {
    // Get the size of the content.
    int size = 0;
    for (Map.Entry<String, Integer> nodeClock : clocks.entrySet()) {
      size += 4; // Use to hold the size of this entry.
      size += nodeClock.getKey().length; // Key size.
      size += 4; // Value size.
    }

    // Serialize the clock.
    ByteBuffer buf = ByteBuffer.allocate(size);
    for (Map.Entry<String, Integer> nodeClock : clocks.entrySet()) {
      // Serialize the size of this entry.
      buf.putInt(nodeClock.getKey().length + 4);
      // Serialize the key.
      for (int i = 0; i < nodeClock.getKey().length; ++i) {
        buf.putChar(nodeClock.getKey().charAt(i));
      }
      // Serialize the value.
      buf.putInt(nodeClock.getValue());
    }

    buf.position(0);
    return buf;
  }

  /**
   * Deserialize the given buffer as a vector clock.
   */
  public static HashMap<String, Integer> deserialize(ByteBuffer buf) {
    HashMap<String, Integer> clocks = new HashMap<String, Integer>();
    
    // Read the buffer entry by entry until we have consumed all the bytes.
    while (buf.hasRemaining()) {
      // Get the size of this entry.
      int size = buf.getInt();
      // Get the key.
      String nodeId = "";
      for (int i = 0; i < size - 4; ++i) {
        nodeId += buf.getChar();
      }
      // Get the value.
      int clockTime = buf.getInt();
      // Construct a new entry.
      clocks.put(nodeId, clockTime);
    }

    buf.position(0);
    return clocks;
  }

  /**
   * Return the string version of the given vector clock.
   */
  public static String stringVerionOf(HashMap<String, Integer> clock) {
    String res = "{";
    boolean first = true;
    for (Map.Entry<String, Integer> nodeClock : clock.entrySet()) {
      if (first) {
        first = false;
      } else {
        res += " ";
      }

      res += nodeClock.getKey() + ":" + nodeClock.getValue();
    }
    res += "}";

    return res;
  }

  /**
   * Return the string version of the current vector clock.
   */
  public static synchronized String stringVersion() {
    return stringVerionOf(clocks);
  }
}