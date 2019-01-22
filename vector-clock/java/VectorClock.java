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
  public static synchronized ByteBuffer asBytes() {
    // Get the size of the content.
    int contentSize = 0;
    for (Map.Entry<String, Integer> nodeClock : clocks.entrySet()) {
      contentSize += 4; // Use to hold the size of this entry.
      contentSize += nodeClock.getKey().length; // Key size.
      contentSize += 4; // Value size.
    }

    // Serialize the clock.
    // The first 4 bytes are the size of the remaining content.
    ByteBuffer buf = ByteBuffer.allocate(4 + contentSize);
    buf.putInt(contentSize);
    for (Map.Entry<String, Integer> nodeClock : clocks.entrySet()) {
      buf.putInt(nodeClock.getKey().length + 4); // entry length
      for (int i = 0; i < nodeClock.getKey().length; ++i) {
        buf.putChar(nodeClock.getKey().charAt(i));
      }
      buf.putInt(nodeClock.getValue());
    }

    buf.position(0);
    return buf;
  }

  // TODO: Implement the corresponding deserialization as well.
}