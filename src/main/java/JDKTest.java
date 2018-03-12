import sun.misc.Unsafe;

import java.io.InvalidClassException;
import java.io.ObjectStreamClass;
import java.io.ObjectStreamField;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.TypeVariable;
import java.util.*;

/**
 * @author luqibao
 * @date 2017/10/25
 */
public class JDKTest {

	private static Garbage WEAK_HOLDER;
	private static byte[] bytes;
	private static final sun.misc.Unsafe U;

	private volatile long size = 0;
	private static final long SIZE;

	static {
		try {
			U = getUnsafe();
			Class<?> k = JDKTest.class;
			SIZE = U.objectFieldOffset(k.getDeclaredField("size"));
		} catch (Exception e) {
			throw new Error(e);
		}
	}

	public static Unsafe getUnsafe() {
		try {
			Field f = Unsafe.class.getDeclaredField("theUnsafe");
			f.setAccessible(true);
			return (Unsafe) f.get(null);
		} catch (Exception e) {
			/* ... */
			return null;
		}
	}

	static final int tableSizeFor(int cap) {
		int n = cap - 1;
		n |= n >>> 1;
		n |= n >>> 2;
		n |= n >>> 4;
		n |= n >>> 8;
		n |= n >>> 16;
		return (n < 0) ? 1 : (n >= (1 << 30)) ? (1 << 30) : n + 1;
	}

	public static void main(String[] args) throws Exception {
		// testMap();
		// testCollections();
		// testType();
		// testTreeMap();
		// testWeakHashMap();

		 testObjectStreamClass();

		// JDKTest test = new JDKTest();
		// test.testUnSafe();

		// System.out.println(tableSizeFor(33));
//		testputMapEntries();
//		System.out.println(Integer.valueOf("110101",2) & Integer.valueOf("101010",2));

	}

	public static void testputMapEntries() {

		Map<String, String> map1 = new HashMap<>();
		for (int i = 0; i < 10; i++) {
			map1.put(String.valueOf(i),"");
		}

		Map<String,String> map2 = new HashMap<>(map1);

	}

	private void testUnSafe() {

		long size1 = size;
		log(size);
		log(size1);
		U.compareAndSwapInt(this, SIZE, 0, 100);
		log(size);
		log(size1);
	}

	public static void testObjectStreamClass() throws Exception {

		ObjectStreamClass streamClass = ObjectStreamClass.lookup(Garbage.class);
		log(streamClass.getSerialVersionUID());

		ObjectStreamClassDemo classDemo = new ObjectStreamClassDemo();

//		getDeclaredSerialFields(ObjectStreamClassDemo.class);

	}

	/**
	 * 获取需要序列化的字段
	 * 
	 * @param cl
	 * @return
	 * @throws InvalidClassException
	 */
	private static ObjectStreamField[] getDeclaredSerialFields(Class<?> cl) throws InvalidClassException {
		ObjectStreamField[] serialPersistentFields = null;
		try {
			Field f = cl.getDeclaredField("serialPersistentFields");
			int mask = Modifier.PRIVATE | Modifier.STATIC | Modifier.FINAL;
			if ((f.getModifiers() & mask) == mask) {
				f.setAccessible(true);
				serialPersistentFields = (ObjectStreamField[]) f.get(null);
			}
		} catch (Exception ex) {
		}
		if (serialPersistentFields == null) {
			return null;
		} else if (serialPersistentFields.length == 0) {
			return new ObjectStreamField[] {};
		}

		return null;
	}

	public static void testWeakHashMap() throws Exception {

		Map<Garbage, String> weakHashMap = new WeakHashMap<>();
		Garbage garbage1 = new Garbage("use");

		weakHashMap.put(garbage1, "use");

		log(weakHashMap);

		WEAK_HOLDER = garbage1;
		bytes = new byte[1024 * 1024 * 1024];

		Garbage garbage2 = null;
		for (int i = 1;; i++) {
			garbage2 = new Garbage("unUse" + i);
			weakHashMap.put(garbage2, "unUse" + i);
			if (hasGarbage) {
				break;
			}
		}

		log(weakHashMap.get(WEAK_HOLDER) + "!!!!!!!!!!!!!!!!!!");
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

		SortedMap<String, String> subTreeMap = treeMap.headMap("3", true);
		log(subTreeMap);
		for (Map.Entry<String, String> entry : treeMap.entrySet()) {
			entry.setValue(entry.getValue() + ".old");
		}

		log(treeMap);

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

	private static volatile boolean hasGarbage;

	private static class Garbage implements Serializable {
		public Garbage(String desc) {
			this.desc = desc;
		}

		String desc;

		String idx;

		@Override
		protected void finalize() throws Throwable {
			log("garbage " + desc);
			hasGarbage = true;
			super.finalize();
		}

		@Override
		public String toString() {
			return desc;
		}
	}

	private static class ObjectStreamClassDemo implements Serializable {

		private static final ObjectStreamField[] serialPersistentFields = new ObjectStreamField[] {
				new ObjectStreamField("field", String.class) };
	}
}
