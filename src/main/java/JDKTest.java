import sun.misc.Unsafe;
import sun.reflect.generics.tree.Wildcard;

import javax.annotation.Resource;
import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.invoke.MethodHandle;
import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.reflect.*;
import java.net.URL;
import java.security.ProtectionDomain;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author luqibao
 * @date 2017/10/25
 */
public class JDKTest<K extends Object & Map, V> {

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

        // testObjectStreamClass();

        // testUnicode();
        // JDKTest test = new JDKTest();
        // test.testUnSafe();

        // System.out.println(tableSizeFor(33));
        // testputMapEntries();
        // System.out.println(Integer.valueOf("110101",2) &
        // Integer.valueOf("101010",2));
        // testWriteObject();

        // testWriteByteIntoOutputStream();

        // Integer 最大值和最小值
        // log(0x80000000);
        // log(0x7fffffff);
        //
        // log(Integer.toString(10,16));
        //
        // Integer integer = Integer.valueOf(127);
        // Integer integer1 = Integer.valueOf(127);
        //
        // log(integer == integer1);
        //
        // Integer integer2 = Integer.valueOf(128);
        // Integer integer3 = Integer.valueOf(128);
        //
        // log(integer2 == integer3);
        //
        // testSecurityManager();

        // testLogFileSort();

        // testTimeZone();

        // testBase64();

        // testTimer();

        // testStringTokenizer();

        // testStringJoiner();

//		testCalendar();
//        testInterrupt();
//        testFinalize();
//        testPhantomReference();
//        testParameterizedType();

        testTypeVariable();


//        testGenericArrayType();


//        testWildcard();

//        testClass();

//        testMethod();

    }


    private static void testSecurityManager() {
        System.clearProperty("java.version");
    }

    public static void testputMapEntries() {

        Map<String, String> map1 = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            map1.put(String.valueOf(i), "");
        }

        Map<String, String> map2 = new HashMap<>(map1);

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

        // getDeclaredSerialFields(ObjectStreamClassDemo.class);

    }

    public static void testWriteObject() throws Exception {

        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("file.txt"));
        outputStream.writeObject("1123");
        outputStream.flush();
        outputStream.close();
    }

    public static void testWriteByteIntoOutputStream() throws Exception {

        FileOutputStream outputStream = new FileOutputStream(new File("file1.txt"));
        byte[] bytes = "1".getBytes();

        outputStream.write(bytes);

        outputStream.flush();
        outputStream.close();

        log(new String(bytes));

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
            return new ObjectStreamField[]{};
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
        for (int i = 1; ; i++) {
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

    public static void testUnicode() throws Exception {
        // 文件以UTF-16(UCS-2)保存
        FileInputStream inputStream = new FileInputStream(new File("111.txt"));
        int b;
        while ((b = inputStream.read()) != -1) {
            System.out.print(b + " ");
        }

        // 输出 254 255 0 49 0 ...
    }

    public static void testLogFileSort() {
        Random random = new Random();
        int num = 1000000;
        Map<String, Integer> map = new HashMap<>(num);

        for (int i = 0; i < num; i++) {
            String uid = String.valueOf(random.nextInt(10000));
            if (map.containsKey(uid)) {
                map.put(uid, map.get(uid) + 1);
            } else {
                map.put(uid, 1);
            }
        }

        PriorityQueue<LogFile> priorityQueue = new PriorityQueue<>(map.size(), new Comparator<LogFile>() {
            @Override
            public int compare(LogFile o1, LogFile o2) {
                return o2.loginTime.compareTo(o1.loginTime);
            }
        });

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            priorityQueue.add(new LogFile(entry.getKey(), entry.getValue()));
        }

        for (int i = 0; i < priorityQueue.size(); i++) {
            System.out.println(priorityQueue.poll());

        }
    }

    public static void testTimeZone() {
        for (String timeZone : TimeZone.getAvailableIDs()) {
            System.out.println(timeZone);
        }
    }

    public static void testBase64() throws Exception {
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] bytes = encoder.encode("hello world".getBytes());
        log(new String(bytes, "UTF-8"));
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] bytes1 = decoder.decode(bytes);
        log(new String(bytes1, "UTF-8"));
    }

    public static void testTimer() throws Exception {

        Timer timer = new Timer();
        AtomicInteger integer = new AtomicInteger(0);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (Exception e) {

                }
                log(System.currentTimeMillis());
                integer.incrementAndGet();

                if (integer.get() == 10) {
                    throw new RuntimeException("test throw exception ");
                }

            }
        }, 1000, 1000);

        Thread.sleep(10000000);
    }

    public static void testStringTokenizer() {

        StringTokenizer stringTokenizer = new StringTokenizer("hello&1111|111", "&|");

        log("count = " + stringTokenizer.countTokens());

        while (stringTokenizer.hasMoreTokens()) {
            log(stringTokenizer.nextToken());
        }
    }

    public static void testStringJoiner() {

        StringJoiner joiner = new StringJoiner("|");

        joiner.add("hello");
        joiner.add("world");

        log(joiner.toString());

        joiner = new StringJoiner("|", "{", "}");
        joiner.add("hello");
        joiner.add("world");
        log(joiner.toString());
    }

    public static void testCalendar() {

        Calendar calendar = Calendar.getInstance();
        int numberOfWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        log("numberOfWeek  = " + numberOfWeek);
        int firstDayOfWeek = calendar.getFirstDayOfWeek();
        log("firstDayOfWeek = " + firstDayOfWeek);
        int weekOfMonth = calendar.get(Calendar.WEEK_OF_MONTH);
        log("weekOfMonth = " + weekOfMonth);
        int dayOfWeekInMonth = calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH);
        log("dayOfWeekInMonth = " + dayOfWeekInMonth);
        int era = calendar.get(Calendar.ERA);
        log("era = " + era);
        int date = calendar.get(Calendar.DATE);
        log("date = " + date);
        int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
        log("dayOfYear = " + dayOfYear);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        log("dayOfWeek = " + dayOfWeek);
        int zoneOffset = calendar.get(Calendar.ZONE_OFFSET);
        log("zoneOffset = " + zoneOffset);

        // calendar.add(Calendar.HOUR,-10);
        int am = calendar.get(Calendar.AM_PM);
        if ((am & Calendar.PM) != 0) {
            log("afternoon");
        }

        Calendar calendar1 = new Calendar.Builder().setFields(Calendar.YEAR, 2010).build();
        log(new SimpleDateFormat("yyyy-MM-dd").format(calendar1.getTime()));

        log(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .format(Calendar.getInstance(TimeZone.getTimeZone("GMT")).getTime()));

        Calendar calendar2 = Calendar.getInstance();
        log(new SimpleDateFormat("yyyy-MM-dd").format(calendar2.getTime()));
        calendar2.roll(Calendar.YEAR, false);
        log(new SimpleDateFormat("yyyy-MM-dd").format(calendar2.getTime()));
    }

    public static void testInterrupt() throws Exception {


        Thread t1 = new Thread(() -> {
            log("before");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                //抛出InterruptedException 的时候中断状态已经被清理了
                // 所以1 2 3都为false
                log("interrupt");
                log("1 = " + Thread.currentThread().isInterrupted());
                log("2 = " + Thread.interrupted());
                log("3 = " + Thread.currentThread().isInterrupted());
                Thread.currentThread().interrupt();
                //Thread.currentThread().interrupt() 将状态设置为中断 Thread.interrupted() 返回中断的状态并且将中断状态清理掉
                // 所以4 = true 5 = true 6 = false
                log("4 = " + Thread.currentThread().isInterrupted());
                log("5 = " + Thread.interrupted());
                log("6 = " + Thread.currentThread().isInterrupted());
            }
            log("after");
        });


        t1.start();
        t1.interrupt();
        t1.join();
    }

    public static void testFinalize() throws Exception {


        FinalizeClass finalizeClass = new FinalizeClass();
        finalizeClass = null;
        System.gc();
        TimeUnit.SECONDS.sleep(1000);
    }

    /**
     * 虚拟引用
     *
     * @throws Exception
     */
    public static void testPhantomReference() throws Exception {
        ReferenceQueue<FinalizeClass> queue = new ReferenceQueue<>();
        FinalizeClass finalizeClass = new FinalizeClass();
        PhantomReference<FinalizeClass> phantomReference = new PhantomReference<>(finalizeClass, queue);
        PhantomReference<FinalizeClass> phantomReference1 = new PhantomReference<>(finalizeClass, queue);

        TimeUnit.SECONDS.sleep(5);
        System.gc();
        TimeUnit.SECONDS.sleep(5);
        Reference fc = queue.remove();

    }


    public static void testParameterizedType() throws Exception {
        Method method = JDKTest.class.getMethod("method1", List.class);
        Type[] types = method.getGenericParameterTypes();
        if (types[0] instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType) types[0];
            types = pt.getActualTypeArguments();
            for (Type type : types) {
                System.out.println(type); //class java.lang.String
            }

            if (types[0] instanceof Class) {
                Class clazz = (Class) types[0];
                System.out.println(clazz.getName()); //java.lang.String
            }

            Type type = pt.getRawType();
            if (type instanceof Class) {
                System.out.println(((Class) type).getName()); //java.util.List
            }
        }


        method = JDKTest.class.getMethod("method2", Map.Entry.class);
        types = method.getGenericParameterTypes();


        ParameterizedType type = (ParameterizedType) types[0];
        Type type1 = type.getOwnerType();
        if (type1 instanceof Class) {
            System.out.println(((Class) type1).getName());//java.util.Map
        }
    }

    public static void testTypeVariable() {


        JDKTest<FinalizeClass, FinalizeClass> jdkTest = new JDKTest<>();

        //获取类型参数
        Type[] types = jdkTest.getClass().getTypeParameters();

        /*
         * print :
        =================================
        class JDKTest
        =================================
        interface java.util.Map
        =================================
        t = class java.lang.Object
        =================================
        t = interface java.util.Map
        =================================
        K
        =================================
        class JDKTest
        =================================
        class java.lang.Object
        =================================
        t = class java.lang.Object
        =================================
        V
         */
        for (Type type : types) {

            TypeVariable typeVariable = (TypeVariable) type;
            log(typeVariable.getGenericDeclaration());

            //获取类型参数的具体类型的上限
            log(typeVariable.getBounds()[typeVariable.getBounds().length - 1]);

            types = typeVariable.getBounds();

            for (Type t:types){
                log("t = " + t);
            }

            log(((TypeVariable) type).getName());
        }

    }


    K[][] k;


    public static void testGenericArrayType() throws Exception {

        Field field = JDKTest.class.getDeclaredField("k");
        Type type = field.getGenericType();
        /* print:
         *  =================================
         *  K[]
         */
        log(((GenericArrayType) type).getGenericComponentType());

    }


    public static void testWildcard() throws Exception {

        Method method = JDKTest.class.getMethod("method3", Class.class);
        Type[] types = method.getGenericParameterTypes();
        ParameterizedType parameterizedType = (ParameterizedType) types[0];
        types = parameterizedType.getActualTypeArguments();
        WildcardType wildcardType = (WildcardType) types[0];

        log(wildcardType);//? super java.lang.Float
        log(wildcardType.getLowerBounds()[0]); //class java.lang.Float


        Method method1 = JDKTest.class.getMethod("method4", Class.class);
        types = method1.getGenericParameterTypes();
        parameterizedType = (ParameterizedType) types[0];
        types = parameterizedType.getActualTypeArguments();
        wildcardType = (WildcardType) types[0];

        log(wildcardType);//? extends java.lang.Float
        log(wildcardType.getUpperBounds()[0]); //class java.lang.Float


    }


    public void method3(Class<? super Float> clazz) {
    }

    public void method4(Class<? extends Float> clazz) {

    }

    public static void testClass() throws Exception {

        Class clazz = Class.forName("SeriaClass");
        System.out.println(clazz.getName());

//        Class  clazz2 = Class.forName("SupperSeriaClass",true,Thread.currentThread().getContextClassLoader());
//        System.out.println(clazz2.getName());

        Method method = Class.class.getDeclaredMethod("forName0", String.class, boolean.class, ClassLoader.class, Class.class);
        method.setAccessible(true);


        Class class2 = (Class) method.invoke(null, "SupperSeriaClass", true, Thread.currentThread().getContextClassLoader(), System.class);
        Object ob = class2.newInstance();

        //返回是否可以使用断言
        log(class2.desiredAssertionStatus());
        assert 1 == 2;

        enumDemo[] enumDemos = enumDemo.class.getEnumConstants();
        for (enumDemo demo : enumDemos) {
            log(demo);
        }

        log("isInterface = " + class2.isInterface());

        log("isInstance = " + class2.isInstance(ob));

        //判断class2 是不是Object相同的或者超类
        log(class2.isAssignableFrom(Object.class));

        //class3 是不是SupperSeriaClass.class的 超类
        Class class3 = Object.class;
        log(class3.isAssignableFrom(SupperSeriaClass.class));


        log(class3.isAssignableFrom(Object.class));

        log("isArray = " + enumDemos.getClass().isArray());

        log("primitive = " + int.class.isPrimitive());

        log("isAnnotation = " + JDKTest.class.isAnnotation());

        log("isSynthetic = " + JDKTest.class.isSynthetic());

        Class<?> cLass4 = JDKTest.class.getSuperclass();
        log("super class = " + cLass4.getName());


        //TODO getGenericSuperclass方法没看明白
        Type type = JDKTest.class.getGenericSuperclass();

        log("int.class = "+int.class.getGenericSuperclass());

        log(type);//class java.lang.Object

        type = AbstractMap.class.getGenericSuperclass();
        log(type);//class java.lang.Object

        type = int.class.getGenericSuperclass();
        log(type);//null

        type = Object.class.getGenericSuperclass();
        log(type);//null

        log("package = " + String.class.getPackage().getName());//package = java.lang

        log("interfaces = " + Arrays.asList(HashMap.class.getInterfaces()));//interfaces = [interface java.util.Map, interface java.lang.Cloneable, interface java.io.Serializable]


        Map<String, String> mapObj = new AbstractMap<String, String>() {
            @Override
            public Set<Entry<String, String>> entrySet() {
                return null;
            }
        };

        //返回局部类和匿名类的所在方法
        log(mapObj.getClass().getEnclosingMethod());//public static void JDKTest.testClass() throws java.lang.Exception

        log(JDKTest.class.getEnclosingMethod());//null

        class localClass {
        }

        log(JDKTest.class.getEnclosingMethod());//null
        log(localClass.class.getEnclosingMethod());//public static void JDKTest.testClass() throws java.lang.Exception


        //所有定义的类
        /* print:
         *  c = class JDKTest$ObjectStreamClassDemo
            =================================
            c = class JDKTest$Garbage
            =================================
            c = class JDKTest$LogFile
            =================================
            c = class JDKTest$FinalizeClass
            =================================
            c = class JDKTest$enumDemo
            =================================
            c = class JDKTest$HashCode
         * */
        Class[] classes = JDKTest.class.getDeclaredClasses();
        for (Class c : classes) {
            log("c = " + c);
        }


        //所有定义的字段
        /*
         *  f= private static JDKTest$Garbage JDKTest.WEAK_HOLDER
            =================================
            f= private static byte[] JDKTest.bytes
            =================================
            f= private static final sun.misc.Unsafe JDKTest.U
            =================================
            f= private volatile long JDKTest.size
            =================================
            f= private static final long JDKTest.SIZE
            =================================
            f= java.lang.Object[][] JDKTest.k
            =================================
            f= java.util.Map JDKTest.aClass
            =================================
            f= private static volatile boolean JDKTest.hasGarbage
            =================================
            f= static final boolean JDKTest.$assertionsDisabled
         * */
        Field[] fields = JDKTest.class.getDeclaredFields();
        for (Field field : fields) {
            log("f= " + field);
        }

        //所有的方法
        Method[] methods = JDKTest.class.getDeclaredMethods();
        for (Method m : methods) {
            log("m = " + m);
        }

//        所有的构造函数
        Constructor[] constructors = JDKTest.class.getDeclaredConstructors();
        for (Constructor constructor : constructors) {
            log(constructor); //public JDKTest()
        }


        ProtectionDomain protectionDomain = JDKTest.class.getProtectionDomain();
        log("protectionDomain = " + protectionDomain);


        URL url = JDKTest.class.getResource("Class.md");
        log("url = " + url);


        Class<?> clazz1 = int[].class;
        log(clazz1.getName());

        log("toGenericString  = " + JDKTest.class.toGenericString());


        //see @java.lang.Class.classLoader
        Field field = JDKTest.class.getDeclaredField("classLoader");
        field.setAccessible(true);
        log(field);
    }

    public static void testMethod() throws Exception {

        Method method = JDKTest.class.getMethod("method1", List.class);

        Field field = Method.class.getDeclaredField("slot");
        field.setAccessible(true);
        log("slot = " + field.getInt(method));


        field = Method.class.getDeclaredField("annotations");
        field.setAccessible(true);
        log("annotations = " + field.get(method));

        log("declaringClass = " + method.getDeclaringClass());

        Annotation[] annotations = method.getAnnotations();
        for (Annotation annotation : annotations) {
            log("annotation  = " + annotation);
        }

        log("name = " + method.getName());

        log("modifiers = " + method.getModifiers());

        //如果参数是范型的话 那么返回类型参数（类似 K V ） @see  testTypeVariable
        TypeVariable typeVariable[] = method.getTypeParameters();
        for (TypeVariable tv : typeVariable) {
            log(tv);
        }

        Class<?> type = method.getReturnType();
        log("returnType = " + type);

        //返回的是 方法返回值的标准表示
        log("genericReturnType = " + method.getGenericReturnType());

        log("count = " + method.getParameterCount());

        //
        Type[] types = method.getGenericParameterTypes();
        for (Type t : types) {
            log(t);
        }


        types = method.getGenericExceptionTypes();
        for (Type t : types) {
            log("e = " + t);
        }

        log(method);

        log("generic string = " + method.toGenericString());

        log("bridge = " + method.isBridge());

        log("varArgs = " + method.isVarArgs());

        //TODO ?
        log("default value = " + method.getDefaultValue());


    }


    Map aClass;

    private enum enumDemo {

        YES(),
        NO(),

    }

    @Annotation1
    public void method1(@Annotation1 List<String> list) {
    }


    @Retention(RetentionPolicy.RUNTIME)
    @interface Annotation1 {

        String value() default "123";
    }


    int i = 10;

    public int method4() {

        return 10;
    }


    public void method2(Map.Entry<String, Integer> entry) {
    }

    private static class FinalizeClass extends AbstractMap {


        private byte[] bytes = new byte[1024 * 1024 * 1024];

        @Override
        protected void finalize() throws Throwable {
            System.out.println("====");
            bytes = null;
            //@see Finalizer
            super.finalize();
        }

        @Override
        public Set<Entry> entrySet() {
            return null;
        }
    }

    private static class LogFile {

        public LogFile(String uid, Integer loginTime) {
            this.uid = uid;
            this.loginTime = loginTime;
        }

        private String uid;
        private Integer loginTime;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public Integer getLoginTime() {
            return loginTime;
        }

        public void setLoginTime(Integer loginTime) {
            this.loginTime = loginTime;
        }

        @Override
        public String toString() {
            return "LogFile{" + "uid='" + uid + '\'' + ", loginTime=" + loginTime + '}';
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

        private static final ObjectStreamField[] serialPersistentFields = new ObjectStreamField[]{
                new ObjectStreamField("field", String.class)};
    }
}
