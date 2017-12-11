Navigable 接口,这个接口主要设计为定位元素的,扩展自SortMap
```
/**
 * 1.扩展自SortMap的一个接口并且添加了很多的搜索最接近指定目标，以下的方法:
 * {@code lowerEntry}, {@code floorEntry}, {@code ceilingEntry},{@code higherEntry}
 * 返回低于,低于或等于,高于或等于,高于的元素，如果没有查找到元素的话,那么返回null，同理
 * {@code lowerKey}, {@code floorKey}, {@code ceilingKey}, {@code higherKey} 返回最近的Key
 * 这些方法都是设计成定位元素的（不是遍历map）
 * 
 * 2.NavigableMap能够通过正序或者倒叙的方式进行操作和便利，方法descendingMap方法
 * 返回的就是一个倒序的NavigableMap，但是正序的操作可能会被倒叙的操作速度会更快
 * 这个类中的{@code subMap}, {@code headMap}, {@code tailMap} 和SortMap中同名
 * 的方法是不同的，它包含了一个boolean的参数，可以选择性的指定是否包含右边(大的一方)
 * 并且他的subMap返回的必须是NavigableMap！
 *
 * 3.增加的方法@code firstEntry}, {@code pollFirstEntry}, {@code lastEntry},  {@code pollLastEntry} 
 * 可以返回或者删除最大或者最小的值，如果不存在的话,那么返回null
 * 
 * 4.对于所有返回的元素都建议实现于Map.Entry并且不要实现setValue方法，但是可以通过
 * put方法修改关联的值
 * 
 * 5. 对于subMap,headMap,tailMap (从SortMap继承而来的)的方法可以适当的进行翻新
 * 使其返回的值继承自NavigableMap，其他的情况keySet()也可以返回NavigableSet
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 */
public interface NavigableMap<K,V> extends SortedMap<K,V> {
    /**
     * 返回Map中小于Key的并且最接近的Key的值，如果不存在的话 那么返回null
     */
    Map.Entry<K,V> lowerEntry(K key);

    /**
     * 和lowerEntry(K key)类似 但是返回的不是map的值而是map的key
     */
    K lowerKey(K key);

    /**
     * 返回Map中低于或者等于key的值，如果不存在的话 返回null
     */
    Map.Entry<K,V> floorEntry(K key);

    /**
     * 和floorEntry(K key)类似 但是返回的是不是值,而不是key，如果不存在
     * 也是返回null
     */
    K floorKey(K key);

    /**
     * 和floorEntry刚好相反,返回的是大于或者等于给定key的值，如果不存在的话,那么返回
     * null
     */
    Map.Entry<K,V> ceilingEntry(K key);

    /**
     * 和ceilingEntry(K key) 类似，但是返回的Key而不是单个的元素
     */
    K ceilingKey(K key);

    /**
     * 返回大于给定key的值，如果不存在的话,那么返回null
     */
    Map.Entry<K,V> higherEntry(K key);

    /**
     * 和higherEntry(K key)类似,但是返回的是key，如果不存在那么返回null
     */
    K higherKey(K key);

    /**
     * 返回Map中key最小的那个值(利用SortedMap的比较方式比较出来的),如果Map
     * 是空的话,那么返回null
     */
    Map.Entry<K,V> firstEntry();

    /**
     * 返回Map中key最大的哪个值(利用SortedMap的比较方式比较出来的)
     * 是空的话,那么返回null
     */
    Map.Entry<K,V> lastEntry();

    /**
     * 和firstEntry的区别是会将元素弹出，
     */
    Map.Entry<K,V> pollFirstEntry();

    /**
     *和lastEntry的区别是会将元素弹出
     */
    Map.Entry<K,V> pollLastEntry();

    /**
     * 返回的Map是本Map的反转,返回的map是一个视图,所以在两边的任何修改操作都是相互影响的
     * 如果在迭代的过程中map删除了元素（除非是iterator自己删除的）这种情况下,结果是没有
     * 定义的
     * Returns a reverse order view of the mappings contained in this map.
     * The descending map is backed by this map, so changes to the map are
     * reflected in the descending map, and vice-versa.  If either map is
     * modified while an iteration over a collection view of either map
     * is in progress (except through the iterator's own {@code remove}
     * operation), the results of the iteration are undefined.
     *
     * <p>The returned map has an ordering equivalent to
     * <tt>{@link Collections#reverseOrder(Comparator) Collections.reverseOrder}(comparator())</tt>.
     * The expression {@code m.descendingMap().descendingMap()} returns a
     * view of {@code m} essentially equivalent to {@code m}.
     *
     * @return a reverse order view of this map
     */
    NavigableMap<K,V> descendingMap();

    /**
     * 返回的map的key的集合，返回的集合是map键的一个视图,所以对于
     * 集合中key的修改都反映到map中，反之亦然。如果在集合进行迭代操作的时候map对元素进行的修改
     * 那么迭代的操作结果将是未知的. 同时这个集合支持删除的操作,删除会反映到map中去,同理map中的元素
     * 删除也会反映到集合中.但是集合不支持add和addAll操作
     * Returns a {@link NavigableSet} view of the keys contained in this map.
     * The set's iterator returns the keys in ascending order.
     * The set is backed by the map, so changes to the map are reflected in
     * the set, and vice-versa.  If the map is modified while an iteration
     * over the set is in progress (except through the iterator's own {@code
     * remove} operation), the results of the iteration are undefined.  The
     * set supports element removal, which removes the corresponding mapping
     * from the map, via the {@code Iterator.remove}, {@code Set.remove},
     * {@code removeAll}, {@code retainAll}, and {@code clear} operations.
     * It does not support the {@code add} or {@code addAll} operations.
     *
     * @return a navigable set view of the keys in this map
     */
    NavigableSet<K> navigableKeySet();

    /**
     * 和descendingMap()类似 返回一个倒叙的key的集合，其他的性质和navigableKeySet()描述的相同
     * Returns a reverse order {@link NavigableSet} view of the keys contained in this map.
     * The set's iterator returns the keys in descending order.
     * The set is backed by the map, so changes to the map are reflected in
     * the set, and vice-versa.  If the map is modified while an iteration
     * over the set is in progress (except through the iterator's own {@code
     * remove} operation), the results of the iteration are undefined.  The
     * set supports element removal, which removes the corresponding mapping
     * from the map, via the {@code Iterator.remove}, {@code Set.remove},
     * {@code removeAll}, {@code retainAll}, and {@code clear} operations.
     * It does not support the {@code add} or {@code addAll} operations.
     *
     * @return a reverse order navigable set view of the keys in this map
     */
    NavigableSet<K> descendingKeySet();

    /**
     * 1. 返回一个子的map,和SortedMap中有非常大的不同点在于: 可以指定是否包含两边
     * 的值，如果fromKey和toKey都相等的时候,那么将会返回空的map（除非两个boolean类型的
     * 参数都为true）
     * 2. 返回的map是主map的一个视图,所以子map和主map的修改是相互影响的，返回的子map支持
     * 所有在主map中使用的方法
     * 3.如果将一个key超出范围的元素插入到子map中的话,那么将抛出IllegalArgumentException异常
     *
     * @param fromKey low endpoint of the keys in the returned map
     * @param fromInclusive {@code true} if the low endpoint
     *        is to be included in the returned view
     * @param toKey high endpoint of the keys in the returned map
     * @param toInclusive {@code true} if the high endpoint
     *        is to be included in the returned view
     * @return a view of the portion of this map whose keys range from
     *         {@code fromKey} to {@code toKey}
     * @throws ClassCastException if {@code fromKey} and {@code toKey}
     *         cannot be compared to one another using this map's comparator
     *         (or, if the map has no comparator, using natural ordering).
     *         Implementations may, but are not required to, throw this
     *         exception if {@code fromKey} or {@code toKey}
     *         cannot be compared to keys currently in the map.
     * @throws NullPointerException if {@code fromKey} or {@code toKey}
     *         is null and this map does not permit null keys
     * @throws IllegalArgumentException if {@code fromKey} is greater than
     *         {@code toKey}; or if this map itself has a restricted
     *         range, and {@code fromKey} or {@code toKey} lies
     *         outside the bounds of the range
     */
    NavigableMap<K,V> subMap(K fromKey, boolean fromInclusive,
                             K toKey,   boolean toInclusive);

    /**
     * 和SortedMap中的headMap类型,但是多了一个布尔值设置是否包含边界
     * Returns a view of the portion of this map whose keys are less than (or
     * equal to, if {@code inclusive} is true) {@code toKey}.  The returned
     * map is backed by this map, so changes in the returned map are reflected
     * in this map, and vice-versa.  The returned map supports all optional
     * map operations that this map supports.
     *
     * <p>The returned map will throw an {@code IllegalArgumentException}
     * on an attempt to insert a key outside its range.
     *
     * @param toKey high endpoint of the keys in the returned map
     * @param inclusive {@code true} if the high endpoint
     *        is to be included in the returned view
     * @return a view of the portion of this map whose keys are less than
     *         (or equal to, if {@code inclusive} is true) {@code toKey}
     * @throws ClassCastException if {@code toKey} is not compatible
     *         with this map's comparator (or, if the map has no comparator,
     *         if {@code toKey} does not implement {@link Comparable}).
     *         Implementations may, but are not required to, throw this
     *         exception if {@code toKey} cannot be compared to keys
     *         currently in the map.
     * @throws NullPointerException if {@code toKey} is null
     *         and this map does not permit null keys
     * @throws IllegalArgumentException if this map itself has a
     *         restricted range, and {@code toKey} lies outside the
     *         bounds of the range
     */
    NavigableMap<K,V> headMap(K toKey, boolean inclusive);

    /**
     * 返回一个子的Map，这个Map的值是从key大于fromKey或者等于fromKey开始(取决于inclusive
     * 设置的值)，返回的Map是当前这个Map的一个视图，所以无论是哪个Map的修改对方都能看到，并且
     * 返回的Map支持所有当前Map的操作，但是向返回的Map中插入一个不在Key不在范围内的值的话，那么
     * 将会抛出IllegalArgumentException异常
     */
    NavigableMap<K,V> tailMap(K fromKey, boolean inclusive);

    /**
     * 继承自SortedMap
     * {@inheritDoc}
     *
     * <p>Equivalent to {@code subMap(fromKey, true, toKey, false)}.
     *
     * @throws ClassCastException       {@inheritDoc}
     * @throws NullPointerException     {@inheritDoc}
     * @throws IllegalArgumentException {@inheritDoc}
     */
    SortedMap<K,V> subMap(K fromKey, K toKey);

    /**
     * 继承自SortedMap 
     * {@inheritDoc}
     *
     * <p>Equivalent to {@code headMap(toKey, false)}.
     *
     * @throws ClassCastException       {@inheritDoc}
     * @throws NullPointerException     {@inheritDoc}
     * @throws IllegalArgumentException {@inheritDoc}
     *  
     */
    SortedMap<K,V> headMap(K toKey);

    /**
     *继承自SortedMap 
     * {@inheritDoc}
     *
     * <p>Equivalent to {@code tailMap(fromKey, true)}.
     *
     * @throws ClassCastException       {@inheritDoc}
     * @throws NullPointerException     {@inheritDoc}
     * @throws IllegalArgumentException {@inheritDoc}
     * 
     */
    SortedMap<K,V> tailMap(K fromKey);
}

```