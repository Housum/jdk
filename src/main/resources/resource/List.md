### List 接口
```
/*
 * 方法后面有，‘<-’ 标示的为Collection没有的方法
 * @see Collection
 * @see Set
 * @see ArrayList
 * @see LinkedList
 * @see Vector
 * @see Arrays#asList(Object[])
 * @see Collections#nCopies(int, Object)
 * @see Collections#EMPTY_LIST
 * @see AbstractList
 * @see AbstractSequentialList
 * @since 1.2
 */

public interface List<E> extends Collection<E> {
    // Query Operations

    /**
     */
    int size();

    /**
     */
    boolean isEmpty();

    /**
     */
    boolean contains(Object o);

    /**
     */
    Iterator<E> iterator();

    /**
     */
    Object[] toArray();

    /**
     */
    <T> T[] toArray(T[] a);


    // Modification Operations

    /**
     */
    boolean add(E e);

    /**
     */
    boolean remove(Object o);


    // Bulk Modification Operations

    /**
     */
    boolean containsAll(Collection<?> c);

    /**
     */
    boolean addAll(Collection<? extends E> c);

    /**
     */
    boolean addAll(int index, Collection<? extends E> c); <-

    /**
     */
    boolean removeAll(Collection<?> c);

    /**
     */
    boolean retainAll(Collection<?> c);

    /**
     * @since 1.8
     */
    default void replaceAll(UnaryOperator<E> operator) {
        Objects.requireNonNull(operator);
        final ListIterator<E> li = this.listIterator();
        while (li.hasNext()) {
            li.set(operator.apply(li.next()));
        }
    }

    /**
     * @since 1.8
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    default void sort(Comparator<? super E> c) {
        Object[] a = this.toArray();
        Arrays.sort(a, (Comparator) c);
        ListIterator<E> i = this.listIterator();
        for (Object e : a) {
            i.next();
            i.set((E) e);
        }
    }

    /**
     */
    void clear();


    // Comparison and hashing

    /**
     */
    boolean equals(Object o);

    /**
     */
    int hashCode();


    // Positional Access Operations

    /**
     * 获得指定索引的元素，如果超出的话，那么抛出IndexOutOfBoundsException
     */
    E get(int index); <-

    /**
     * 在指定索引处设置值
     * 1.超出范围，抛出IndexOutOfBoundsException
     * 2.参数不合法的话，抛出IllegalArgumentException（可选）
     * 3.出现类型不匹配，抛出ClassCastException
     * 4.如果实现类不支持这个方法的话，那么抛出UnsupportedOperationException
     * 5. 如果参数为null，那么抛出NPE
     */
    E set(int index, E element); <-

    /**
     * 将参数插入到指定的位置，同时因为subList的子序列只是一个视图，所以也会被改变
     * {@link set(..)}  出现这些情况下，都会抛出相同异常
     */
    void add(int index, E element);<-

    /**
     */
    E remove(int index);


    // Search Operations

    /**
     * 返回第一个指定元素出现的问题在
     */
    int indexOf(Object o); <-

    /**
     * 最后的指定元素所在的位置，否则返回-1
     */
    int lastIndexOf(Object o); <-


    // List Iterators

    /**
     */
    ListIterator<E> listIterator();

    /**
     */
    ListIterator<E> listIterator(int index); <-

    // View

    /**
     * 返回一个子视图,这个字视图是使用的都是原本的list的元素，
     */
    List<E> subList(int fromIndex, int toIndex); <-

    /**
     * @since 1.8
     */
    @Override
    default Spliterator<E> spliterator() {
        return Spliterators.spliterator(this, Spliterator.ORDERED);
    }
}

```