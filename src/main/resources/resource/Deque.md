### Deque 接口
```
/**
 * 双端队列，一般情况，都是一个没有长度限制的，但是实现类可以选择性的
 * 实现为有长度限制的。同时它也支持栈的形式(stack),只需要使用
 * addFirst(..),offerFirst(..),removeFirst(),pollFirst(),getFirst(),peekFirst()
 */
public interface Deque<E> extends Queue<E> {
    /**
     *  插入元素到对首，如果不能插入，则抛出IllegalStateException异常
     */
    void addFirst(E e);

    /**
     * 插入元素到对尾，如果不能插入，则抛出IllegalStateException异常
     */
    void addLast(E e);

    /**
     * 插入元素到对首,如果不能插入，则返回false
     */
    boolean offerFirst(E e);

    /**
     *插入元素到对尾，如果不能插入，则返回false
     */
    boolean offerLast(E e);

    /**
     *  删除对首的元素,并且返回， 如果不存在的话，那么抛出NoSuchElementException
     */
    E removeFirst();

    /**
     * 删除对尾的元素，并且返回 如果不存在的话，那么抛出NoSuchElementException
     */
    E removeLast();

    /**
     * 删除对首的元素，并且返回，如果不存在的话,那么返回null
     */
    E pollFirst();

    /**
     * 删除对尾的元素，并且返回，如果不存在的话，那么返回null 
     */
    E pollLast();

    /**
     * @throws NoSuchElementException if this deque is empty
     */
    E getFirst();

    /**
     * @throws NoSuchElementException if this deque is empty
     */
    E getLast();

    /**
     */
    E peekFirst();

    /**
     */
    E peekLast();

    /**
     * 删除离队首最近的元素，如果不存在，那么队列不在任何的改变，
     * 删除成功的话，返回true
     */
    boolean removeFirstOccurrence(Object o);

    /**
     * 删除离对尾最近的元素o，如果不存在，那么队列不在任何的改变，
     *                  删除成功的话，返回true
     */
    boolean removeLastOccurrence(Object o);

    // *** Queue methods ***

    /**
     *
     * <p>This method is equivalent to {@link #addLast}.
     *
     */
    boolean add(E e);

    /**
     * <p>This method is equivalent to {@link #offerLast}.
     */
    boolean offer(E e);

    /**
     * <p>This method is equivalent to {@link #removeFirst()}.
     * @throws NoSuchElementException if this deque is empty
     */
    E remove();

    /**
     * <p>This method is equivalent to {@link #pollFirst()}.
     * @return the first element of this deque, or {@code null} if
     *         this deque is empty
     */
    E poll();

    /**
     * <p>This method is equivalent to {@link #getFirst()}.
     * @throws NoSuchElementException if this deque is empty
     */
    E element();

    /**
     * <p>This method is equivalent to {@link #peekFirst()}.
     * @return the head of the queue represented by this deque, or
     *         {@code null} if this deque is empty
     */
    E peek();


    // *** Stack methods ***

    /**
     * <p>This method is equivalent to {@link #addFirst}.
     * @throws IllegalStateException if the element cannot be added at this
     */
    void push(E e);

    /**
     * this method is equivalent to {@link #removeFirst} 
     */
    E pop();


    // *** Collection methods ***

    /**
     */
    boolean remove(Object o);

    /**
     */
    boolean contains(Object o);

    /**
     * 返回队列的个数
     */
    public int size();

    /**
     * 返回一个从对首到对尾的Iterator
     */
    Iterator<E> iterator();

    /**
     * 返回一个从对尾到对首的Iterator 
     */
    Iterator<E> descendingIterator();

}

```