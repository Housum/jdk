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
     * Retrieves and removes the head of this queue, waiting if necessary
     * until an element becomes available.
     *
     * @return the head of this queue
     * @throws InterruptedException if interrupted while waiting
     */
    E take() throws InterruptedException;

    /**
     * Retrieves and removes the head of this queue, waiting up to the
     * specified wait time if necessary for an element to become available.
     *
     * @param timeout how long to wait before giving up, in units of
     *        {@code unit}
     * @param unit a {@code TimeUnit} determining how to interpret the
     *        {@code timeout} parameter
     * @return the head of this queue, or {@code null} if the
     *         specified waiting time elapses before an element is available
     * @throws InterruptedException if interrupted while waiting
     */
    E poll(long timeout, TimeUnit unit)
        throws InterruptedException;

    /**
     * Returns the number of additional elements that this queue can ideally
     * (in the absence of memory or resource constraints) accept without
     * blocking, or {@code Integer.MAX_VALUE} if there is no intrinsic
     * limit.
     *
     * <p>Note that you <em>cannot</em> always tell if an attempt to insert
     * an element will succeed by inspecting {@code remainingCapacity}
     * because it may be the case that another thread is about to
     * insert or remove an element.
     *
     * @return the remaining capacity
     */
    int remainingCapacity();

    /**
     * 删除指定的元素，如果删除成功的话，那么返回true，否则返回false
     */
    boolean remove(Object o);

    /**
     * Returns {@code true} if this queue contains the specified element.
     * More formally, returns {@code true} if and only if this queue contains
     * at least one element {@code e} such that {@code o.equals(e)}.
     *
     * @param o object to be checked for containment in this queue
     * @return {@code true} if this queue contains the specified element
     * @throws ClassCastException if the class of the specified element
     *         is incompatible with this queue
     *         (<a href="../Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if the specified element is null
     *         (<a href="../Collection.html#optional-restrictions">optional</a>)
     */
    public boolean contains(Object o);

    /**
     * Removes all available elements from this queue and adds them
     * to the given collection.  This operation may be more
     * efficient than repeatedly polling this queue.  A failure
     * encountered while attempting to add elements to
     * collection {@code c} may result in elements being in neither,
     * either or both collections when the associated exception is
     * thrown.  Attempts to drain a queue to itself result in
     * {@code IllegalArgumentException}. Further, the behavior of
     * this operation is undefined if the specified collection is
     * modified while the operation is in progress.
     *
     * @param c the collection to transfer elements into
     * @return the number of elements transferred
     * @throws UnsupportedOperationException if addition of elements
     *         is not supported by the specified collection
     * @throws ClassCastException if the class of an element of this queue
     *         prevents it from being added to the specified collection
     * @throws NullPointerException if the specified collection is null
     * @throws IllegalArgumentException if the specified collection is this
     *         queue, or some property of an element of this queue prevents
     *         it from being added to the specified collection
     */
    int drainTo(Collection<? super E> c);

    /**
     * Removes at most the given number of available elements from
     * this queue and adds them to the given collection.  A failure
     * encountered while attempting to add elements to
     * collection {@code c} may result in elements being in neither,
     * either or both collections when the associated exception is
     * thrown.  Attempts to drain a queue to itself result in
     * {@code IllegalArgumentException}. Further, the behavior of
     * this operation is undefined if the specified collection is
     * modified while the operation is in progress.
     *
     * @param c the collection to transfer elements into
     * @param maxElements the maximum number of elements to transfer
     * @return the number of elements transferred
     * @throws UnsupportedOperationException if addition of elements
     *         is not supported by the specified collection
     * @throws ClassCastException if the class of an element of this queue
     *         prevents it from being added to the specified collection
     * @throws NullPointerException if the specified collection is null
     * @throws IllegalArgumentException if the specified collection is this
     *         queue, or some property of an element of this queue prevents
     *         it from being added to the specified collection
     */
    int drainTo(Collection<? super E> c, int maxElements);
}

```