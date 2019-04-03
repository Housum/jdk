import sun.misc.Unsafe;
import sun.reflect.ConstantPool;

import java.io.*;
import java.lang.annotation.*;
import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.reflect.*;
import java.net.InetSocketAddress;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.security.AccessController;
import java.security.ProtectionDomain;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author luqibao
 * @date 2017/10/25
 */
public class JDKTest<K extends Object & Map, V> implements Serializable {

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

    public static void main(String[] args) throws Throwable {
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

//        testTypeVariable();


//        testGenericArrayType();


//        testWildcard();

//        testClass();

//        testMethod();


//        testInvocationHandler();

//        System.out.println(Base64.getEncoder().encode(new String("你好".getBytes("UTF-16"), "GB2312").getBytes()));


//        Character.UnicodeBlock unicodeBlock = Character.UnicodeBlock.of(120);
//        if (unicodeBlock == Character.UnicodeBlock.BASIC_LATIN) {
//            System.out.println("ASCII");
//        }

//
//        int active = Thread.activeCount();
//        Thread[] arr = new Thread[active];
//        Thread.enumerate(arr);
//        System.out.println(arr.length);
//
//        Thread thread = new Thread() {
//            @Override
//            public void run() {
//                System.out.println("1");
//            }
//        };
//
//        thread.start();
//
//        ClassLoader classLoader = thread.getContextClassLoader();
//
//
//        StackTraceElement[] stackTraceElements = thread.getStackTrace();
//
//        ThreadLocal<String> stringThreadLocal = ThreadLocal.withInitial(() -> "1234");
//
//
//        new Thread() {
//            @Override
//            public void run() {
//                System.out.println(stringThreadLocal.get());
//                System.out.println("inner : " + Thread.currentThread().getName());
//            }
//        }.start();
//
//        System.out.println(Thread.currentThread().getName());

//        new Thread(() -> new TreadLocalDemo().run()).start();
//
//        synchronized (JDKTest.class) {
//            JDKTest.class.wait();
//        }

        /*
         * output
         * Thread-0 threadLocal inheritableThreadLocal
         * Thread-1 threadLocal 0-0
         * Thread-2 threadLocal 0-0
         * Thread-3 threadLocal 0-0
         * Thread-4 threadLocal 0-0
         * Thread-5 threadLocal 0-0
         * Thread-6 threadLocal 0-0
         * Thread-7 threadLocal 0-0
         */

//        testThrowableSuppressed();

//        testFillInStackTrace();

//        testRepeatableAnnotation();


//        testClass_native_getConstantPool();
//        test_class_getAnnotatedInterfaces();

//        testClassLoader();


//        test_class_package();

//        test_classLoader_getResource();

//        test_compiler();

//        test_System_setIO_setOut();

//        test_System_setIO_setIn();

//        test_System_property();

//        test_system_separator();

//        test_package_version();

//        testDeque();

//        testLinkedHashMap();

//        testConcurrentHashMap();

//        testInterrupt();

//        testLockSupport();

//        testInterruptInRunningTime();

//        testReentrantLockInterrupt();

//        testAQSCondition();

//        testPrintThreadPoolStatus();

//        testScheduleExecutorServiceFixRate();

//        testScheduleExecutorServiceFixDelayed();

//        testInputStreamMarkAndReset();

//        testBufferMarkAndResetInWriteMode();

//        testServiceSocketChannel();


        new Thread(() -> {
            try {
                testAsynchronousServerSocketChannel();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        TimeUnit.SECONDS.sleep(2);
        testAsynchronousSocketChannel();


        TimeUnit.SECONDS.sleep(1000);
    }


    private static int RESIZE_STAMP_BITS = 16;


    public static void testAsynchronousServerSocketChannel() throws Exception {

        AsynchronousServerSocketChannel serverSocketChannel = AsynchronousServerSocketChannel.
                open(AsynchronousChannelGroup.withThreadPool(Executors.newFixedThreadPool(4)));
        serverSocketChannel.bind(new InetSocketAddress(8082));

        serverSocketChannel.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {
            @Override
            public void completed(AsynchronousSocketChannel socketChannel, Object attachment) {
                log("accept completed");

                ByteBuffer byteBuffer =ByteBuffer.allocate(1024);
                socketChannel.read(byteBuffer, byteBuffer, new CompletionHandler<Integer, ByteBuffer>() {
                    @Override
                    public void completed(Integer result, ByteBuffer buffer) {
                        log("read completed");
                        buffer.flip();
                        log("这是服务端读到的信息:" + new String(buffer.array(),0,buffer.remaining()));
                        buffer.clear();
                        buffer.put("这是服务端的信息".getBytes());
                        buffer.flip();
                        socketChannel.write(buffer);
                    }

                    @Override
                    public void failed(Throwable exc, ByteBuffer attachment) {

                    }
                });




            }
            @Override
            public void failed(Throwable exc, Object attachment) {

            }
        });
    }

    public static void testAsynchronousSocketChannel() throws Exception {

        AsynchronousSocketChannel socketChannel = AsynchronousSocketChannel.open();
        socketChannel.connect(new InetSocketAddress(8082), null, new CompletionHandler<Void, Object>() {
            @Override
            public void completed(Void result, Object attachment) {
                log("connect completed");
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                byteBuffer.put("这是客户端的消息".getBytes());
                byteBuffer.flip();
                socketChannel.write(byteBuffer);

                byteBuffer.clear();

                socketChannel.read(byteBuffer, byteBuffer, new CompletionHandler<Integer, ByteBuffer>() {

                    @Override
                    public void completed(Integer result, ByteBuffer buffer) {
                        log("read completed");
                        buffer.flip();
                        log("这是客户端读到的信息:" + new String(buffer.array(),0,buffer.remaining()));
                        buffer.clear();

                        byteBuffer.put("这是客户端的消息1".getBytes());
                        byteBuffer.flip();
                        socketChannel.write(byteBuffer);

                    }

                    @Override
                    public void failed(Throwable exc, ByteBuffer attachment) {

                    }
                });
            }
            @Override
            public void failed(Throwable exc, Object attachment) {

            }
        });



    }


    public static void testServiceSocketChannel() throws Exception {

        ServerSocketChannel socketChannel = ServerSocketChannel.open();
        socketChannel.bind(new InetSocketAddress(8082));

        Selector selector = Selector.open();
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            int i = selector.select();
            if (i != 0) {
                Iterator<SelectionKey> selectionKeyIterator = selector.selectedKeys().iterator();
                while (selectionKeyIterator.hasNext()) {
                    SelectionKey selectionKey = selectionKeyIterator.next();

                    if (selectionKey.isAcceptable()) {
                        log("accept");

                        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
                        SocketChannel channel = serverSocketChannel.accept();
                        channel.configureBlocking(false);

                        channel.register(selector, SelectionKey.OP_READ);
                    } else if (selectionKey.isReadable()) {
                        SocketChannel channel = (SocketChannel) selectionKey.channel();
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        while (channel.read(byteBuffer) > 0) {
                            byteBuffer.flip();
                            String clientMsg = new String(byteBuffer.array(), 0, byteBuffer.remaining());
                            Integer index = Integer.valueOf(clientMsg);
                            log("客户端传入的数字:" + index);
                            if (index % 5 == 0) {
                                channel.register(selector, SelectionKey.OP_WRITE);
                                selectionKey.attach("您传入数字为5的倍数" + index);
                            }
                            byteBuffer.clear();
                        }
                    } else if (selectionKey.isWritable()) {
                        SocketChannel channel = (SocketChannel) selectionKey.channel();
                        ByteBuffer charBuffer = ByteBuffer.allocate(1024);
                        String content = (String) selectionKey.attachment();
                        charBuffer.put(("this is server msg :" + content).getBytes());
                        charBuffer.flip();
                        channel.write(charBuffer);
                        selectionKey.interestOps(SelectionKey.OP_READ);
                    }
                    selectionKeyIterator.remove();
                }
            }
        }
    }

    public static void testSocketServer() throws Exception {

        Selector selector = Selector.open();

        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_CONNECT);
        boolean cc = socketChannel.connect(new InetSocketAddress("127.0.0.1", 8082));
        log("cc:" + cc);

        int k = 0;

        while (true) {
            int i = selector.select();
            if (i > 0) {
                Set<SelectionKey> selectionKeySet = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeySet.iterator();
                while (iterator.hasNext()) {
                    SelectionKey sk = iterator.next();
                    iterator.remove();
                    if (sk.isConnectable()) {
                        SocketChannel socketChannel1 = (SocketChannel) sk.channel();
                        socketChannel1.finishConnect();
                        socketChannel1.register(sk.selector(), SelectionKey.OP_WRITE | SelectionKey.OP_READ);
                    } else if (sk.isReadable()) {
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        SocketChannel sc = (SocketChannel) sk.channel();
                        if (sc.read(byteBuffer) > 0) {
                            byteBuffer.flip();
                            log("服务端消息:" + new String(byteBuffer.array(), 0, byteBuffer.remaining()));
                        }
                    } else if (sk.isWritable()) {
                        TimeUnit.SECONDS.sleep(1);
                        ByteBuffer charBuffer = ByteBuffer.allocate(1024);
                        charBuffer.put(("" + k++).getBytes());
                        charBuffer.flip();
                        SocketChannel sc = (SocketChannel) sk.channel();
                        sc.write(charBuffer);
                    }
                }


            }
        }
    }

    public static void testInputStreamMarkAndReset() {

        ByteArrayInputStream inputStream = new ByteArrayInputStream("123456789".getBytes());
        log((char) inputStream.read());
        log((char) inputStream.read());
        inputStream.mark(1);
        log((char) inputStream.read());
        log((char) inputStream.read());
        log((char) inputStream.read());
        inputStream.reset();
        log((char) inputStream.read());
        log((char) inputStream.read());
        log((char) inputStream.read());
    }


    public static void testBufferMarkAndResetInWriteMode() {

        /*
        * =================================
            3
            =================================
            4
            =================================
            5
        *
        * */
        ByteBuffer byteBuffer = ByteBuffer.allocate(6);
        byteBuffer.mark();
        byteBuffer.put((byte) 0);
        byteBuffer.put((byte) 1);
        byteBuffer.put((byte) 2);
        byteBuffer.reset();
        byteBuffer.put((byte) 3);
        byteBuffer.put((byte) 4);
        byteBuffer.put((byte) 5);

        byteBuffer.flip();
        while (byteBuffer.hasRemaining()) {
            log(byteBuffer.get());
        }


    }


    public static void testScheduleExecutorServiceFixRate() throws Exception {


        /*
         * output :
         * =================================
         * time :2
         * =================================
         * time :8
         * =================================
         * time :8
         * =================================
         * time :8
         * =================================
         * time :10
         * =================================
         * time :12
         * */
        ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(2);

        AtomicBoolean first = new AtomicBoolean(true);

        long startTime = System.currentTimeMillis();
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            log("time :" + (System.currentTimeMillis() - startTime) / 1000);
            if (first.get()) {
                try {
                    TimeUnit.SECONDS.sleep(6);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                first.set(false);
            }
        }, 2, 2, TimeUnit.SECONDS);

        scheduledExecutorService.awaitTermination(60, TimeUnit.SECONDS);
    }

    public static void testScheduleExecutorServiceFixDelayed() throws Exception {


        ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(2);
        AtomicBoolean first = new AtomicBoolean(true);
        /*
        * output:
        =================================
        time :2
        =================================
        time :10
        =================================
        time :12
        =================================
        time :14
        =================================
        time :16
        *
        *
        * */
        long startTime = System.currentTimeMillis();
        scheduledExecutorService.scheduleWithFixedDelay(() -> {
            log("time :" + (System.currentTimeMillis() - startTime) / 1000);
            if (first.get()) {
                try {
                    TimeUnit.SECONDS.sleep(6);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                first.set(false);
            }
        }, 2, 2, TimeUnit.SECONDS);

        scheduledExecutorService.awaitTermination(60, TimeUnit.SECONDS);
    }


    public static void testConcurrentHashMap() {

        log(Integer.toString(16, 2));
        log(Integer.toString(Integer.numberOfLeadingZeros(16)));
        log(Integer.toString(Integer.numberOfLeadingZeros(16), 2));
        int i = Integer.numberOfLeadingZeros(16) | (1 << (RESIZE_STAMP_BITS - 1));
        log(Integer.toString(i, 2));

        //并行计算
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        String baseStr = "ABCDEF";
        for (int j = 1; j < baseStr.length(); j++) {
            map.put(String.valueOf(baseStr.charAt(j - 1)), j);
        }
        map.forEach(2, (k, v) -> System.out.println(Thread.currentThread().getName() + ",k = " + k + ",v  = " + v));

        Integer v = map.search(4, (k, v1) -> {
            if ("A".equals(k)) return v1;
            return null;

        });
        System.out.println(v);

        System.out.println("A".split(",").length);

    }

    public static void testLinkedHashMap() {

        Map<String, Integer> integerMap = new LinkedHashMap<String, Integer>() {
            @Override
            protected boolean removeEldestEntry(Map.Entry eldest) {
                return size() > 1;
            }
        };
        /*
         * =================================
         * {1=1}
         * =================================
         * {2=2}
         * */
        integerMap.put("1", 1);
        log(integerMap);
        integerMap.put("2", 2);
        log(integerMap);
    }


    public static void testDeque() {
        Deque<Integer> deque = new ArrayDeque<>();
        deque.push(1);
        deque.push(2);

        log(deque.pop());
        log(deque.pop());
    }

    public static void testRepeatableAnnotation() {

        RepeatAnnotationUseNewVersion annotationUseNewVersion = new RepeatAnnotationUseNewVersion();
        Annotation[] annotations = annotationUseNewVersion.getClass().getDeclaredAnnotationsByType(Authorities.class);
        for (Annotation annotation : annotations) {
            log(annotation);
        }
    }

    @Repeatable(Authorities.class)
    public @interface Authority {
        String role();
    }

    public @interface Authorities {
        Authority[] value();
    }

    @Authority(role = "Admin")
    @Authority(role = "Manager")
    public static class RepeatAnnotationUseNewVersion {
        public void doSomeThing() {
        }
    }

    private static void testFillInStackTrace() throws Throwable {

        Throwable throwable = new Throwable("th");
        helpTestFillInStackTrace(throwable);
    }

    private static void helpTestFillInStackTrace(Throwable throwable) throws Throwable {
        //1.如果没有注释
        /*
         *Exception in thread "main" java.lang.Throwable: th
            at JDKTest.helpTestFillInStackTrace(JDKTest.java:207)
            at JDKTest.testFillInStackTrace(JDKTest.java:202)
	        at JDKTest.main(JDKTest.java:194)
         */
        throwable.fillInStackTrace();
        //2.如果注释掉的话
        /*
        Exception in thread "main" java.lang.Throwable: th
        at JDKTest.testFillInStackTrace(JDKTest.java:201)
        at JDKTest.main(JDKTest.java:194)
	      */
        throw throwable;
    }

    private static void testThrowableSuppressed() throws Exception {
        /*
        * Exception in thread "main" java.lang.RuntimeException: run1
	at JDKTest.testThrowableSuppressed(JDKTest.java:199)
	at JDKTest.main(JDKTest.java:192)
	Suppressed: java.lang.RuntimeException: run2
		at JDKTest.testThrowableSuppressed(JDKTest.java:200)
		... 1 more
	Suppressed: java.lang.RuntimeException: run3
		at JDKTest.testThrowableSuppressed(JDKTest.java:201)
		... 1 more
        * */
        Exception exception = new RuntimeException("run1");
        exception.addSuppressed(new RuntimeException("run2"));
        exception.addSuppressed(new RuntimeException("run3"));
        throw exception;
    }


//    static {
//        Properties properties = new Properties();
//        properties.setProperty("java.lang.Integer.IntegerCache.high", Integer.MAX_VALUE + "");
//        VM.saveAndRemoveProperties(properties);
//    }


    private static class TreadLocalDemo {

        private static ThreadLocal<String> threadLocal = ThreadLocal.withInitial(() -> "threadLocal");
        private static ThreadLocal<String> inheritableThreadLocal = new InheritableThreadLocal<String>() {
            @Override
            protected String initialValue() {
                return "inheritableThreadLocal";
            }
        };
        private static volatile boolean fork = false;

        public void run() {
            if (fork) return;
            System.out.println(Thread.currentThread().getName() + " " + threadLocal.get() + " " + inheritableThreadLocal.get());
            inheritableThreadLocal.set("0-0");
            threadLocal.set("--");
            new Thread(() -> new TreadLocalDemo().run()).start();
            synchronized (this) {
                try {
                    this.wait(1);
                } catch (Exception e) {
                }
            }
            fork = true;
        }

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
                //isInterrupted()(非静态方法)返回中断状态但是并不会清除中断状态
                //Thread.currentThread().interrupt() 如果非堵塞状态 将中断状态设置为true
                //Thread.interrupted()(静态方法) 返回中断的状态并且将中断状态清理掉
                // 所以4 = true 5 = true 6 = false
                log("4 = " + Thread.currentThread().isInterrupted());
                log("5 = " + Thread.interrupted());
                log("6 = " + Thread.currentThread().isInterrupted());
            }
            log("after");
        });


        t1.start();
        Thread.sleep(1000);
        log("main status = " + t1.isInterrupted());

        // 如果处于堵塞状态 进行中断
        t1.interrupt();
        t1.join();
    }

    public static void testLockSupport() throws Exception {
        /*
         * output:
         *  =================================
         *  before,0
         *  =================================
         *  after,5
         */
        Integer integer = new Integer(10);
        Thread thread = new Thread(() -> {
            log("before,0");
            long startTime = System.currentTimeMillis();
            LockSupport.park(integer);
            log("after," + (System.currentTimeMillis() - startTime) / 1000);
        });

        thread.start();
        Thread.sleep(5000);
        LockSupport.unpark(thread);
        thread.join();
    }

    public static void testInterruptInRunningTime() throws Exception {
        Thread thread = new Thread(() -> {
            log("before");
            Thread.currentThread().interrupt();
            log("after");
        });
        thread.start();
        thread.join();

    }

    public static void testReentrantLockInterrupt() throws Exception {

        ReentrantLock reentrantLock = new ReentrantLock();

        new Thread(() -> {
            log("lock...");
            reentrantLock.lock();
            log("after lock");

        }).start();
        Thread thread1 = new Thread(() -> {
            log("lock...");
            try {
                //不能中断的
                reentrantLock.lock();
                //能够中断
//                reentrantLock.lockInterruptibly();

            } catch (Exception e) {
                e.printStackTrace();
            }

            log("after lock");
        });
        thread1.start();
        thread1.interrupt();
        thread1.join();
    }

    public static void testAQSCondition() throws Exception {
//        ReentrantLock reentrantLock = new ReentrantLock();
//        Condition condition = reentrantLock.newCondition();
//        reentrantLock.lock();
//        try{
//            //必须在reentrantLock获得锁的情况下
//            condition.wait();
//        }catch (Exception e){
//            e.printStackTrace();
//        }finally {
//          reentrantLock.unlock();
//        }

        ReentrantLock reentrantLock = new ReentrantLock();
        Condition condition = reentrantLock.newCondition();

        try {
            //抛出 IllegalMonitorStateException
//            condition.await();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//            reentrantLock.unlock();
        }


        Thread h1 = new Thread(() -> {
            reentrantLock.lock();
            try {
                log("before condition");
                condition.await();
                log("afters condition");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                log("condition finally");
                reentrantLock.unlock();
            }
        });
        Thread h2 = new Thread(() -> {
            reentrantLock.lock();
            try {
                log("before signal");
//                condition.signal();
                log("afters signal");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                log("signal finally");
                reentrantLock.unlock();
            }
        });

        h1.start();
        h2.start();
        TimeUnit.SECONDS.sleep(2);

    }

    private static final int COUNT_BITS = Integer.SIZE - 3;
    private static final int CAPACITY = (1 << COUNT_BITS) - 1;
    private static final int RUNNING = -1 << COUNT_BITS;
    private static final int SHUTDOWN = 0 << COUNT_BITS;
    private static final int STOP = 1 << COUNT_BITS;
    private static final int TIDYING = 2 << COUNT_BITS;
    private static final int TERMINATED = 3 << COUNT_BITS;

    public static void testPrintThreadPoolStatus() {
        log(Integer.toString(CAPACITY, 2));
        log(Integer.toString(RUNNING, 2));
        log(Integer.toString(SHUTDOWN, 2));
        log(Integer.toString(STOP, 2));
        log(Integer.toString(TIDYING, 2));
        log(Integer.toString(TERMINATED, 2));
        log("runStateOf");
        log(runStateOf(RUNNING));


    }

    private static int runStateOf(int c) {
        return c & ~CAPACITY;
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

            for (Type t : types) {
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


    public static void test_System_setIO_setOut() throws Exception {
        PrintStream fileOutputStream = new PrintStream(new File("standard_out"));
        System.setOut(fileOutputStream);
        System.out.println("==standard_out==");

    }

    public static void test_System_setIO_setIn() throws Exception {
        FileInputStream inputStream = new FileInputStream(new File("standard_out"));
        System.setIn(inputStream);

        int byteRead;
        byte[] bytes = new byte[1024];
        while ((byteRead = System.in.read(bytes)) != -1) {
            System.out.println(new String(bytes, 0, byteRead));
        }

    }

    public static void test_System_property() {
        log(System.getProperty("java.vendor.url"));
    }

    public static void test_system_separator() {
        log("line separator|" + System.lineSeparator() + "|");
    }


    public static void test_package_version() {

        /*
         * output:
         * =================================
         * 1.8.0_171=1.8
         * */
        log(Package.getPackage("java.lang").getImplementationVersion() + "="
                + Package.getPackage("java.lang").getSpecificationVersion());
    }

    public static void test_compiler() {

        log(System.getProperty("java.compiler"));
    }

    public static void testClass_native_getConstantPool() throws Exception {

        Class clazz = JDKTest.class;
        Method method = Class.class.getDeclaredMethod("getConstantPool");
        method.setAccessible(true);

        ConstantPool constantPool = (ConstantPool) method.invoke(clazz);
        log(constantPool.getSize());
    }

    public static void test_class_getAnnotatedInterfaces() {
        AnnotatedType[] annotatedTypes = JDKTest.class.getAnnotatedInterfaces();
        for (AnnotatedType annotatedType : annotatedTypes) {
            log(annotatedType.getType().getTypeName());
        }

        /*
         *print:
         * =================================
         * java.io.Serializable
         */

    }


    public static void test_classloader_protected_domain() {

        System.setSecurityManager(new SecurityManager());
        AccessController.checkPermission(new PropertyPermission("/Users/qibao/file1", "w"));


    }

    public static void testClassLoader() {

        /*这两个方法拿到的classLoader 是同一个*/
        InputStream inputStream = JDKTest.class.getClassLoader().getResourceAsStream("doc/basic/Error解读");
        System.out.println(inputStream == null);

        InputStream inputStream1 = JDKTest.class.getResourceAsStream("doc/basic/Error解读");
        System.out.println(inputStream1 == null);
    }

    public static void test_classLoader_getResource() {

        URL url = JDKTest.class.getClassLoader().getResource("/Users/qibao/wx");
        log(url == null);

        URL url1 = JDKTest.class.getClassLoader().getResource("doc/basic/Class解读");
        log(url1 == null);

    }

    public static void test_class_package() {

        Package packageZ = String.class.getPackage();
        log(packageZ.getName());

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

        log("int.class = " + int.class.getGenericSuperclass());

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
//        Field field = JDKTest.class.getDeclaredField("classLoader");
//        field.setAccessible(true);
//        log(field);


        /*
        * annotationType = sun.reflect.annotation.AnnotatedTypeFactory$AnnotatedTypeBaseImpl@78308db1
        =================================
        type = interface JDKTest$Foo1
            * */
        AnnotatedType[] annotatedTypes = Foo.class.getAnnotatedInterfaces();
        for (AnnotatedType annotationType : annotatedTypes) {
            log("annotationType = " + annotationType);
            log("type = " + annotationType.getType());
        }


        AnnotatedType annotatedType = Foo.class.getAnnotatedSuperclass();
        log("annotatedType0 = " + annotatedType);
        //type0 = class JDKTest$Foo2
        log("type0 = " + (annotatedType == null ? "" : annotatedType.getType()));


        method = JDKTest.class.getMethod("getFoo", Integer.class);

        AnnotatedType annotatedType1 = method.getAnnotatedReturnType();

        log("annotatedType1 = " + annotatedType1);
        //class java.lang.String
        log("type1 = " + (annotatedType1 == null ? "" : annotatedType1.getType()));


        annotatedTypes = method.getAnnotatedParameterTypes();

        for (AnnotatedType annotatedType2 : annotatedTypes) {
            log("annotatedType2 = " + annotatedType2);
            //type2 = class java.lang.Integer
            log("type2 = " + annotatedType2.getType());
        }


        annotatedTypes = method.getAnnotatedExceptionTypes();
        for (AnnotatedType annotatedType3 : annotatedTypes) {
            log("annotatedType3 = " + annotatedType3);
            //type3 = class java.lang.Exception
            log("type3 = " + annotatedType3.getType());
        }


        AnnotatedType annotatedType4 = method.getAnnotatedReceiverType();
        log("annotatedType4 = " + annotatedType4);
        //type4 = class JDKTest
        log("type4 = " + annotatedType4.getType());


        Field[] fields1 = Foo.class.getDeclaredFields();
        for (Field field : fields1) {
            log("ff = " + field);
        }


        Field[] fields2 = Foo.class.getFields();
        for (Field field : fields2) {
            log("ff1 = " + field);
        }

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

    public static void testInvocationHandler() {

        /*
        *   =================================
            before
            =================================
            foo3
            =================================
            after
            =================================
            class name = $Proxy0
        * */
        Foo3 foo3 = new Foo4();

        Foo5 foo5 = new Foo5(foo3);

        Foo3 foo31 = (Foo3) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                foo3.getClass().getInterfaces(), foo5);
        foo31.foo(1);
        log("class name = " + foo31.getClass().getName());
    }

    private interface Foo3 {
        void foo(int i);
    }

    private static class Foo4 implements Foo3 {
        @Override
        public void foo(int i) {
            log("foo3");
        }
    }

    private static class Foo5 implements InvocationHandler {

        Foo3 foo3;

        public Foo5(Foo3 foo3) {
            this.foo3 = foo3;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//            log("proxy = " + proxy);
//            log("method = " + method);
//            log("args = " + args);
            log("before ");
            Object returnValue = method.invoke(foo3, args);
//            log("returnValue = " + returnValue);
            log("after");
            return returnValue;
        }
    }


    @Retention(RetentionPolicy.RUNTIME)
    @Target(value = {ElementType.ANNOTATION_TYPE,
            ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.LOCAL_VARIABLE, ElementType.METHOD, ElementType.PACKAGE,
            ElementType.PARAMETER, ElementType.TYPE, ElementType.TYPE_PARAMETER,
            ElementType.TYPE_USE})
    @interface Constom {

    }


    @Constom
    private static class Foo extends @Constom Foo2 implements @Constom Foo1 {

        int i;
        public int k;

    }

    interface Foo1 {

    }

    private static class Foo2 {
        int j;
        public int m;
    }

    public @Constom String getFoo(JDKTest<K, V>this, @Constom Integer i) throws @Constom Exception {
        return "";
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
