### Set 接口

Set接口继承了Collection,但是并没有增加方法,只是将Collection的接口方法重新定义的一番
比如： Set最重要的特性就是不能添加重复的元素.又比如： 如果add元素的时候set中已近存在该元素的话
那么可以根绝子类的不同做出不同的反应。
```
/**
 * Set是一个不能重复的集合，最多也只能有一个元素,
 * 至于是否允许为null值，还是需要看实现类的设计
 * @see Collection
 * @see List
 * @see SortedSet
 * @see HashSet
 * @see TreeSet
 * @see AbstractSet
 * @see Collections#singleton(java.lang.Object)
 * @see Collections#EMPTY_SET
 * @since 1.2
 */

public interface Set<E> extends Collection<E> {
    // Query Operations

    /**
     * 返回集合的个数，如果大于Integer.MAX_VALUE 那么返回Integer.MAX_VALUE
     */
    int size();

    /**
     * 返回集合是否为空
     */
    boolean isEmpty();

    /**
     * 返回是否包含给定的对象，如果包含那么返回true
     * 1.如果给定的元素为null，那么抛出NPE(可选)
     * 2.如果类型不匹配，那么抛出ClassCastException
     */
    boolean contains(Object o);

    /**
     * 返回改集合的Iterator，该迭代子是没有特定的顺序的，除非定义的子类规定了顺序
     */
    Iterator<E> iterator();

    /**
     * 参考Collection的定义，将产生一个新的数(不依赖原来的集合)，产生的顺序和
     * 它返回的Iterator的顺序相同
     */
    Object[] toArray();

    /**
     * 返回一个给定类型的数组，注意如果a的容量大于该集合的size，那么多出来的部分将都是null
     * 如果小于的话，那么将重新分配
     * 1.如果集合的元素不是T的子类，那么将抛出ArrayStoreException异常
     * 2.如果集合a为null，那么抛出NPE
     * @throws NullPointerException if the specified array is null
     */
    <T> T[] toArray(T[] a);


    // Modification Operations

    /**
     * 添加一个元素到集合中去，如果集合中已经存在改元素了，那么元素添加失败，保持原有的元素(选择性)
     * 并且返回false，对于一些不支持的元素可以拒绝添加，抛出unchecked的异常
     * 1.如果该操作不被支持，那么抛出UnsupportedOperationException
     * 2.如果该元素类型不匹配，那么抛出ClassCastException
     * 3.如果该集合不支持null元素，那么抛出NPE
     * 4.如果元素不符合要求，那么抛出IllegalArgumentException
     */
    boolean add(E e);


    /**
     * 删除给定的元素，如果存在并且删除成功，那么返回true，否则返回false
     * 1.ClassCastException （可选）
     * 2.NullPointerException（可选）
     * 3.UnsupportedOperationException（可选）
     */
    boolean remove(Object o);


    // Bulk Operations

    /**
     * 参照 Collection
     */
    boolean containsAll(Collection<?> c);

    /**
     *参照 Collection
     */
    boolean addAll(Collection<? extends E> c);

    /**
     * 求交集 参照 Collection
     */
    boolean retainAll(Collection<?> c);

    /**
     * 删除给定的元素集合 参照 Collection
     */
    boolean removeAll(Collection<?> c);

    /**
     * 清理所有的元素  参照 Collection
     */
    void clear();


    // Comparison and hashing

    /**
     */
    boolean equals(Object o);

    /**
     */
    int hashCode();

    /** 返回一个Spliterator 属性是：SIZED,SUBSIZED,DISTINCT
     * @since 1.8
     */
    @Override
    default Spliterator<E> spliterator() {
        return Spliterators.spliterator(this, Spliterator.DISTINCT);
    }
}

```