### Deque 接口

```
/**
 * 双端队列，一般情况，都是一个没有长度限制的，但是实现类可以选择性的
 * 实现为有长度限制的。同时它也支持栈的形式(stack),只需要使用
 * addFirst(..),offerFirst(..),removeFirst(),pollFirst(),getFirst(),peekFirst()
 */
public interface Deque<E> extends Queue<E> {
    /**
     *  插入元素到队首，如果不能插入，则抛出IllegalStateException异常
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
     * 类似于Queue的element 查看队首的元素，如果不存在那么抛出NoSuchElementException
     */
    E getFirst();

    /**
     *类似于Queue的element 查看队尾的元素，如果不存在那么抛出NoSuchElementException
     */
    E getLast();

    /**
     * 从队首查看数据
     */
    E peekFirst();

    /**
     * 从队尾查看数据
     */
    E peekLast();

    /**
     * 删除离队首最近的元素，如果不存在，那么队列不做任何的改变，
     * 删除成功的话，返回true
     */
    boolean removeFirstOccurrence(Object o);

    /**
     * 删除离对尾最近的元素o，如果不存在，那么队列不在任何的改变，
     *                  删除成功的话，返回true
     */
    boolean removeLastOccurrence(Object o);

    // *** 继承 Queue 的方法 ***

    /**
     * 相当于addLast
     *
     */
    boolean add(E e);

    /**
     * 相当于offerLast
     */
    boolean offer(E e);

    /**
     * 相当于removeFirst
     */
    E remove();

    /**
     * 相当于pollFirst
     */
    E poll();

    /**
     * 相当于getFirst
     */
    E element();

    /**
     * 相当于peekFirst
     */
    E peek();


    // *** 栈的实现  ***

    /**
     * 相当于addFirst
     */
    void push(E e);

    /**
     * 这个方法相当于removeFirst
     * this method is equivalent to {@link #removeFirst} 
     */
    E pop();


    //下面都是继承Collection的方法

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