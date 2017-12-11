import java.lang.reflect.TypeVariable;
import java.util.*;

/**
 * @author luqibao
 * @date 2017/10/25
 */
public class JDKTest {

	public static void main(String[] args) {
		// testMap();
		// testCollections();
		// testType();
		testTreeMap();


	}

	public static void testTreeMap() {

		TreeMap<String, String> treeMap = new TreeMap<>();
		treeMap.put("1", "111");
		treeMap.put("2", "222");
		treeMap.put("3", "333");
		treeMap.put("4", "444");
		String floorKey = treeMap.floorKey("2");
		String ceilingKey = treeMap.ceilingKey("2");

		String lowerKey = treeMap.lowerKey("2");
		String higherKey = treeMap.higherKey("2");
		SortedMap<String, String> subMap = treeMap.subMap("1", "3"); // <include,exclude>
		log(floorKey + "|" + ceilingKey + "|" + lowerKey + "|" + higherKey);
		log(subMap);

		SortedMap<String,String> subTreeMap = treeMap.headMap("3",true);
		log(subTreeMap);

	}
	public static void testCollections() {

		List<String> list = new ArrayList<>();
		list.add("1");
		list.add("2");
		list.add("3");
		list.add("4");
		Collections.reverse(list);
		log(list);

		Collections.shuffle(list);
		log(list);

		Collections.sort(list);
		rotate2(list, 1);
		log(list);

		Collections.sort(list);
		Collections.rotate(list, 1);
		log(list);

		Collections.sort(list);
		log(list);

		Collections.sort(list);

		List<String> subList = new ArrayList<>(2);

		subList.add("3");
		subList.add("4");

		log(Collections.indexOfSubList(list, subList));

		log(Collections.lastIndexOfSubList(list, subList));

	}

	public static void log(Object msg) {
		System.out.println("=================================");
		System.out.println(Objects.toString(msg));
	}

	private static void rotate2(List<?> list, int distance) {
		int size = list.size();
		if (size == 0)
			return;
		int mid = -distance % size;
		if (mid < 0)
			mid += size;
		if (mid == 0)
			return;

		Collections.reverse(list.subList(0, mid));
		Collections.reverse(list.subList(mid, size));
		Collections.reverse(list);
	}

	public static void testMap() {
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

	public static void testType() {

		Class clazz = Integer.class;
		TypeVariable[] typeVariables = clazz.getTypeParameters();
		for (TypeVariable p : typeVariables) {
			System.out.println(p);
		}

		List<String> list = new ArrayList<>();
		TypeVariable[] typeVariable = list.getClass().getTypeParameters();

		for (TypeVariable p : typeVariable) {
			System.out.println(p);
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
