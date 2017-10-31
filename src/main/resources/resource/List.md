### List �ӿ�
```
/*
 * ���������У���<-�� ��ʾ��ΪCollectionû�еķ���
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
     * ���ָ��������Ԫ�أ���������Ļ�����ô�׳�IndexOutOfBoundsException
     */
    E get(int index); <-

    /**
     * ��ָ������������ֵ
     * 1.������Χ���׳�IndexOutOfBoundsException
     * 2.�������Ϸ��Ļ����׳�IllegalArgumentException����ѡ��
     * 3.�������Ͳ�ƥ�䣬�׳�ClassCastException
     * 4.���ʵ���಻֧����������Ļ�����ô�׳�UnsupportedOperationException
     * 5. �������Ϊnull����ô�׳�NPE
     */
    E set(int index, E element); <-

    /**
     * ���������뵽ָ����λ�ã�ͬʱ��ΪsubList��������ֻ��һ����ͼ������Ҳ�ᱻ�ı�
     * {@link set(..)}  ������Щ����£������׳���ͬ�쳣
     */
    void add(int index, E element);<-

    /**
     */
    E remove(int index);


    // Search Operations

    /**
     * ���ص�һ��ָ��Ԫ�س��ֵ�������
     */
    int indexOf(Object o); <-

    /**
     * ����ָ��Ԫ�����ڵ�λ�ã����򷵻�-1
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
     * ����һ������ͼ,�������ͼ��ʹ�õĶ���ԭ����list��Ԫ�أ�
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