### Deque �ӿ�
```
/**
 * ˫�˶��У�һ�����������һ��û�г������Ƶģ�����ʵ�������ѡ���Ե�
 * ʵ��Ϊ�г������Ƶġ�ͬʱ��Ҳ֧��ջ����ʽ(stack),ֻ��Ҫʹ��
 * addFirst(..),offerFirst(..),removeFirst(),pollFirst(),getFirst(),peekFirst()
 */
public interface Deque<E> extends Queue<E> {
    /**
     *  ����Ԫ�ص����ף�������ܲ��룬���׳�IllegalStateException�쳣
     */
    void addFirst(E e);

    /**
     * ����Ԫ�ص���β��������ܲ��룬���׳�IllegalStateException�쳣
     */
    void addLast(E e);

    /**
     * ����Ԫ�ص�����,������ܲ��룬�򷵻�false
     */
    boolean offerFirst(E e);

    /**
     *����Ԫ�ص���β��������ܲ��룬�򷵻�false
     */
    boolean offerLast(E e);

    /**
     *  ɾ�����׵�Ԫ��,���ҷ��أ� ��������ڵĻ�����ô�׳�NoSuchElementException
     */
    E removeFirst();

    /**
     * ɾ����β��Ԫ�أ����ҷ��� ��������ڵĻ�����ô�׳�NoSuchElementException
     */
    E removeLast();

    /**
     * ɾ�����׵�Ԫ�أ����ҷ��أ���������ڵĻ�,��ô����null
     */
    E pollFirst();

    /**
     * ɾ����β��Ԫ�أ����ҷ��أ���������ڵĻ�����ô����null 
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
     * ɾ������������Ԫ�أ���������ڣ���ô���в����κεĸı䣬
     * ɾ���ɹ��Ļ�������true
     */
    boolean removeFirstOccurrence(Object o);

    /**
     * ɾ�����β�����Ԫ��o����������ڣ���ô���в����κεĸı䣬
     *                  ɾ���ɹ��Ļ�������true
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
     * ���ض��еĸ���
     */
    public int size();

    /**
     * ����һ���Ӷ��׵���β��Iterator
     */
    Iterator<E> iterator();

    /**
     * ����һ���Ӷ�β�����׵�Iterator 
     */
    Iterator<E> descendingIterator();

}

```