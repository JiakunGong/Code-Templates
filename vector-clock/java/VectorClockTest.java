import java.nio.ByteBuffer;
import java.util.HashMap;

public class VectorClockTest {
	public static void main(String args[]) {
		// Create local vector clock.
		for (int i = 0; i < 3; ++i) {
			VectorClock.increment("node1");
		}

		// Merge with a remote clock.
		HashMap<String, Integer> remoteClock = new HashMap<String, Integer>();
		remoteClock.put("node2", 2);
		VectorClock.merge(remoteClock);

		// Serialize it.
		ByteBuffer buf = VectorClock.serialize();

		// Print
		System.out.println(VectorClock.stringVersion());
	}
}