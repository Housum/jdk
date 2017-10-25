import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

/**
 * @author luqibao
 * @date 2017/10/25
 */
public class JDKMapTest {

	public static void main(String[] args) {

		HashMap<HashCode, String> map = new HashMap<>(100);

		HashCode hashCode = null;
		for (int i = 0; i < 100; i++) {
			hashCode = new HashCode();
			hashCode.index = i;
			map.put(hashCode, i + "");
		}
		Iterator<HashCode> itr = map.keySet().iterator();
		while (itr.hasNext()) {
			System.out.println(itr.next());
		}

		System.out.println("====================");
        Iterator<HashCode> itr1 = map.keySet().iterator();
        while (itr1.hasNext()) {
            System.out.println(itr1.next());
        }
	}

	static class HashCode {
		static Random random = new Random();
		int index;

		@Override
		public int hashCode() {
			return random.nextInt(1000000000);
		}

		@Override
		public String toString() {

			return index + "";
		}
	}
}
