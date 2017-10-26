###Collection 的方法
```
/*
 * @since 1.2
 */

public interface Collection<E> extends Iterable<E> {
    // Query Operations 查询方法
 
    /**
     * 返回集合中的元素个数，如果个数大于Integer.MAX_VALUE 的话，那么返回Integer.MAX_VALUE
     */
    int size();

    /**
     * 返回集合是否为空 
     */
    boolean isEmpty();

    /**
     * 返回集合是否包含o这个元素，
     * 如果对象的类型不能匹配，那么抛出ClassCastException（可选）
     * 如果传入对象类型为空,那么抛出NullPointerException（可选）
     */
    boolean contains(Object o);

    /**
     * 返回一个迭代子，而这个迭代子的定义并没有十分强烈的规范
     */
    Iterator<E> iterator();

    /**
     * 返回一个代表各个元素的数组，但是数组中的元素并不是集合中的引用。
     */
    Object[] toArray();

    /**
     * 和toArray（）类似，但是返回了具体的类型，并且带有一个参数，对于这个参数还是需要注意一下：
     * 1.如果传入为null的话，那么将抛出NPE
     * 2.如果传入的数组的类型不能匹配上集合中的类型的话，那么将会抛出ArrayStoreException
     * 3.还有一点关于性能上的-如果传入的数组长度不能存放集合中的元素的话，那么将会创建一个新的数组，但是
     * 如果长度的大于或者等于集合的元素的话，那么就使用传入的数组，多出的那部分将会被null填充
     */
    <T> T[] toArray(T[] a);

    // Modification Operations 修改方法

    /**
     *  添加元素到集合中去，如果添加成功的话，那么返回true，否则返回false
     *  根据实现的不同，选择性的
     *  1. 如果不支持add方法，抛出UnsupportedOperationException
     *  2.如果插入的类型不能匹配,那么抛出ClassCastException
     *  3.如果集合不允许插入空的元素，并且插入了null值，那么抛出NullPointerException
     *  4.如果因为插入元素的某些属性导致不能被插入，那么抛出IllegalArgumentException
     *  5.如果因为插入时约束不能被插入，那么抛出IllegalStateException
     */
    boolean add(E e);

    /**
     *
     * 从集合中删除一个元素,依赖于equal方法
     *和add一样，会抛出ClassCastException，NullPointerException，UnsupportedOperationException 异常
     */
    boolean remove(Object o);


    // Bulk Operations  处理大部分的元素

    /**
     * @throws ClassCastException if the types of one or more elements
     *         in the specified collection are incompatible with this
     *         collection
     *         (<a href="#optional-restrictions">optional</a>)
     * @throws NullPointerException if the specified collection contains one
     *         or more null elements and this collection does not permit null
     *         elements
     *         (<a href="#optional-restrictions">optional</a>),
     *         or if the specified collection is null.
     */
    boolean containsAll(Collection<?> c);

    /**
     *
     * @param c collection containing elements to be added to this collection
     * @return <tt>true</tt> if this collection changed as a result of the call
     * @throws UnsupportedOperationException if the <tt>addAll</tt> operation
     *         is not supported by this collection
     * @throws ClassCastException if the class of an element of the specified
     *         collection prevents it from being added to this collection
     * @throws NullPointerException if the specified collection contains a
     *         null element and this collection does not permit null elements,
     *         or if the specified collection is null
     * @throws IllegalArgumentException if some property of an element of the
     *         specified collection prevents it from being added to this
     *         collection
     * @throws IllegalStateException if not all the elements can be added at
     *         this time due to insertion restrictions
     * @see #add(Object)
     */
    boolean addAll(Collection<? extends E> c);

    /**
     * @throws UnsupportedOperationException if the <tt>removeAll</tt> method
     *         is not supported by this collection
     * @throws ClassCastException if the types of one or more elements
     *         in this collection are incompatible with the specified
     *         collection
     *         (<a href="#optional-restrictions">optional</a>)
     * @throws NullPointerException if this collection contains one or more
     *         null elements and the specified collection does not support
     *         null elements
     *         (<a href="#optional-restrictions">optional</a>),
     *         or if the specified collection is null
     */
    boolean removeAll(Collection<?> c);

    /**
     * @since 1.8
     */
    default boolean removeIf(Predicate<? super E> filter) {
        Objects.requireNonNull(filter);
        boolean removed = false;
        final Iterator<E> each = iterator();
        while (each.hasNext()) {
            if (filter.test(each.next())) {
                each.remove();
                removed = true;
            }
        }
        return removed;
    }

    /**
     * 求交集
     */
    boolean retainAll(Collection<?> c);

    /**
     * 清楚所有的元素
     */
    void clear();


    // Comparison and hashing

    /**
     * 对于两个集合是都相等
     */
    boolean equals(Object o);

    /**
     * 返回集合的HashCode
     */
    int hashCode();

    /**
     *
     * @return a {@code Spliterator} over the elements in this collection
     * 1.返回一个Spliterator,一般情况下，实现类都需要自己去实现。
     * 如果将要创建的这个Spliterator的characteristic只有SIZED的话，并且集合中是没有元素的话
     * 那么是不需要提供Spliterator
     * 2.为了能使stream()以及parallelStream()能够返回一个延迟加载的Stream,Spliterator的characteristic
     * 不要使用IMMUTABLE或者CONCURENT,否则Spliterator是一个延迟绑定的也是可以的，如果前面的条件都不满足的话，
     * 那么实现类必须在文档中说明清楚它的绑定策略以及结构的影响，并且同时覆盖stream()以及parallelStream方法，
     * 3.同时默认创建的是一个快速失败，并且characteristic为SIZED和SUBSIZED的Spliterator
     * @since 1.8
     */
    @Override
    default Spliterator<E> spliterator() {
        return Spliterators.spliterator(this, 0);
    }

    /**
     * 返回一个顺序的序列 
     */
    default Stream<E> stream() {
        return StreamSupport.stream(spliterator(), false);
    }

    /**
     * 返回并行的Stream
     * @since 1.8
     */
    default Stream<E> parallelStream() {
        return StreamSupport.stream(spliterator(), true);
    }
}

```