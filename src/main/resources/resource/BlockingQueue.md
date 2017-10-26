### BockingQueue 接口
这个接口最多生产者消费者模型中使用
下面是源码:
```
/*

/**
 * 这个一个扩展的Queue,这是一个堵塞的队列，
 * 1.有一些操作会一直堵塞到能够执行为止例如： put(..),take()。
 * 2.以及一些能够堵塞一段时间的方法,例如:offer(..),poll(..)
 * 3.BockingQueue 不允许添加一个null值进去，因为null值是作为poll没有参数返回的一个结果
 * 4.如果是一个有容量限制的BlockingQueue的时候,那么可以通过remainingCapacity 查看还有多少容量（put操作不需要堵塞，直接能够加入）
 * 5.BlockingQueue一般情况下都是使用在生产者消费者中，但是同时因为它继承了Collection接口，所以还是可以通过remove(..)移除一些元素的
 *，但是不建议这样做，这种只是在某些特殊的情况下使用，比如生产者生成的消息被取消了，这个时候就可以使用remove(..)了
 * 6.BlockingQueue是线程安全的，单个操作都是原子的，（除了那些批量操作，addAll(..)，removeAll(..),retainAll(..),可以实现为
 * 这些操作不成功的话，直接抛出异常）
 */
public interface BlockingQueue<E> extends Queue<E> {
 
 
    /**
     * summary ： 对每一个方法，都会抛出一下的几种异常：
     * 1.ClassCastException 如果参数类型不能匹配
     * 2.NullPointerException 如果参数为空
     * 3.IllegalArgumentException 如果参数不能满足BlockingQueue的规范
     */
     
     
    /**
     * 添加元素到队列中,如果不能够添加进去，那么抛出IllegalStateException异常
     */
    boolean add(E e);

    /**
     * 添加元素到队列中，如果失败那么返回false，如果添加成功，那么返回true
     */
    boolean offer(E e);

    /**
     * 添加元素到队列中，如果添加不了，则一直堵塞到能够添加，但是可能被中断
     * 抛出InterruptedException
     */
    void put(E e) throws InterruptedException;

    /**
     * 添加元素到队列中去，如果不能添加，则堵塞一段时间，如果成功返回true，否则返回false
     * 有可能被中断，抛出InterruptedException
     */
    boolean offer(E e, long timeout, TimeUnit unit)
        throws InterruptedException;

    /**
     * 获得队首的元素，如果不能获得，那么就会一直堵塞，可中断
     */
    E take() throws InterruptedException;

    /**
     * 获得对首的元素，如果不能获得，那么堵塞给定的时间
     */
    E poll(long timeout, TimeUnit unit)
        throws InterruptedException;

    /**
     * 返回还能保存多少元素
     */
    int remainingCapacity();

    /**
     * 删除指定的元素，如果删除成功的话，那么返回true，否则返回false
     */
    boolean remove(Object o);

    /**
     * 返回是否包含给定的元素
     */
    public boolean contains(Object o);

    /**
     * 将剩下的元素都放入到c集合中
     */
    int drainTo(Collection<? super E> c);

    /**
     * 将一部分元素放入到c集合中，最多能达到maxElement 
     */
    int drainTo(Collection<? super E> c, int maxElements);
}

```