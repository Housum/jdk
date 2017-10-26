### BockingQueue �ӿ�
����ӿ����������������ģ����ʹ��
������Դ��:
```
/*

/**
 * ���һ����չ��Queue,����һ�������Ķ��У�
 * 1.��һЩ������һֱ�������ܹ�ִ��Ϊֹ���磺 put(..),take()��
 * 2.�Լ�һЩ�ܹ�����һ��ʱ��ķ���,����:offer(..),poll(..)
 * 3.BockingQueue ���������һ��nullֵ��ȥ����Ϊnullֵ����Ϊpollû�в������ص�һ�����
 * 4.�����һ�����������Ƶ�BlockingQueue��ʱ��,��ô����ͨ��remainingCapacity �鿴���ж���������put��������Ҫ������ֱ���ܹ����룩
 * 5.BlockingQueueһ������¶���ʹ�����������������У�����ͬʱ��Ϊ���̳���Collection�ӿڣ����Ի��ǿ���ͨ��remove(..)�Ƴ�һЩԪ�ص�
 *�����ǲ�����������������ֻ����ĳЩ����������ʹ�ã��������������ɵ���Ϣ��ȡ���ˣ����ʱ��Ϳ���ʹ��remove(..)��
 * 6.BlockingQueue���̰߳�ȫ�ģ�������������ԭ�ӵģ���������Щ����������addAll(..)��removeAll(..),retainAll(..),����ʵ��Ϊ
 * ��Щ�������ɹ��Ļ���ֱ���׳��쳣��
 */
public interface BlockingQueue<E> extends Queue<E> {
 
 
    /**
     * summary �� ��ÿһ�������������׳�һ�µļ����쳣��
     * 1.ClassCastException ����������Ͳ���ƥ��
     * 2.NullPointerException �������Ϊ��
     * 3.IllegalArgumentException ���������������BlockingQueue�Ĺ淶
     */
     
     
    /**
     * ���Ԫ�ص�������,������ܹ���ӽ�ȥ����ô�׳�IllegalStateException�쳣
     */
    boolean add(E e);

    /**
     * ���Ԫ�ص������У����ʧ����ô����false�������ӳɹ�����ô����true
     */
    boolean offer(E e);

    /**
     * ���Ԫ�ص������У������Ӳ��ˣ���һֱ�������ܹ���ӣ����ǿ��ܱ��ж�
     * �׳�InterruptedException
     */
    void put(E e) throws InterruptedException;

    /**
     * ���Ԫ�ص�������ȥ�����������ӣ������һ��ʱ�䣬����ɹ�����true�����򷵻�false
     * �п��ܱ��жϣ��׳�InterruptedException
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
     * ɾ��ָ����Ԫ�أ����ɾ���ɹ��Ļ�����ô����true�����򷵻�false
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