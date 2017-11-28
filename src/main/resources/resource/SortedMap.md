一个Key有序的Map接口，提供了很多关于排序的方法

```
/**
 * 1. 这个Map在他的key上会有序的,排序的规则依据的是对象是按照实现的Comparable接口进行排序
 * 或者Map在创建的时候指定了Comparator,那么就使用Comparator进行数据的排序,这个排序会在
 * entrySet,keySet,values 方法表现出来。除了具有这些性质之外，还增加了一些方便排序的方法
 * 这个接口和SortedSet类似
 * 2. 所有插入的元素的key要不必须继承Comparable接口或者能够被定义的comparator接收,
 * 更多的，两个元素必须能够互相对比，可能抛出异常compareTo或者compare方法的，所以两个
 * 键是否相等是依靠这个两个方法的
 * 3. 如果sortedMap正确的实现了Map接口,那么这个有序的列表必须满足定义:consistent with equals（
 * 可以从Comparator 或者Comparable 中了解是啥回事）， 这是因为Map接口是依赖equals操作的，但是sortMap
 * 对比是依赖compareTo 或者 compare方法,所以认为两个Key是否相等是依赖于这两个方法的(从sortMap这边
 * 看起来的相等).所以在树形的map中即使不满足consistent with equals也是正常的,这其实只是它没有满足Map接口的
 * 一些定义
 * <p>Note that the ordering maintained(被保持) by a sorted map (whether or not an
 * explicit comparator is provided) must be <em>consistent with equals</em> if
 * the sorted map is to correctly implement the {@code Map} interface.  (See
 * the {@code Comparable} interface or {@code Comparator} interface for a
 * precise definition of <em>consistent with equals</em>.)  This is so because
 * the {@code Map} interface is defined in terms of(依照，按照) the {@code equals}
 * operation, but a sorted map performs all key comparisons(比较) using its
 * {@code compareTo} (or {@code compare}) method, so two keys that are
 * deemed(认为) equal by this method are, from the standpoint of the sorted map,
 * equal.  The behavior of a tree map <em>is</em> well-defined even if its
 * ordering is inconsistent with equals; it just fails to obey（服从） the general
 * contract of the {@code Map} interface.
 *
 * 4.所有的实现类都必须实现四个标准的构造器(但这明显不是强约束的,因为接口并不能定义构造器)
 * 构造器如下:
 * 4.1 一个空的构造器，这将会创建一个空的SortMap,并且它的key将会按照他们的自然排序进行key的
 * 排序
 * 4.2 只有一个参数的构造器,参数为Comparator,这个SortMap的key排序就是依赖于Comparator
 * 4.3 只有一个参数的构造器,参数为Map,将所有的元素都放入到SortMap，除此之外,按照自然排序
 * 4.4 只有一个参数的构造器.参数为SortMap,此Map将使用SortMap的排序方式以及将元素都填入到此Map中
 *
 * 5.注意: subMap的方法都是有有边界约束的,一般都是<include,exclude> 
 */

public interface SortedMap<K,V> extends Map<K,V> {
    /**
     * 返回SortMap所使用的Comparator,如果没有的话,那么使用的是Comparable接口的
     * 进行排序
     */
    Comparator<? super K> comparator();

    /**
     * 1. 这个方法返回的一个包含fromKey不包含toKey的子map，如果fromKey和toKey是相等
     * 的话，那么返回的Map是空的,返回的Map是一个视图.所以还是依赖于之前的Map的,所有的
     * 改变都会体现到主Map上，并且这个返回的Map支持所有的Map操作
     * 
     * 2. 如果将一个超出范围的值插入到返回的Map中，那么将会抛出IllegalArgumentException
     *    如果两个键通过map的比较器进行对比,那么将抛出ClassCastException
     *    如果参数为空,那么抛出NPE
          如果fromKey超出了toKey或者超出了本Map的范围(比如是一个subMap返回的SortMap),那么将抛出IllegalArgumentException，
     */
    SortedMap<K,V> subMap(K fromKey, K toKey);

    /**
     * 返回key小于toKey的Map元素, 其他的可以参考 {@link subMap}
     */
    SortedMap<K,V> headMap(K toKey);

    /**
     * 返回Key大于等于fromKey的Map元素，其他的可以参考 {@link subMap}
     */
    SortedMap<K,V> tailMap(K fromKey);

    /**
     * 返回Map中的第一个(Key最小的)key
     * @throws NoSuchElementException 如果Key不存在
     */
    K firstKey();

    /**
     * 返回最大的Key
     * @throws NoSuchElementException 如果Key不存在
     */
    K lastKey();

    /**
     *  返回一个正序的Key的集合,这个集合是一个视图,所以在map中的修改都能
     *  反应到Set中，如果在Set迭代的过程中,map将元素删除了，那么结果是没有
     *  被定义的，这个Set支持remove,removeAll,retainAll方法,但是不支持add
     *  以及addAll方法
     */
    Set<K> keySet();

    /**
     * 返回一个集合,这个集合中是map所有的值(正序),这个集合是map的一个视图,其他的
     * 性质和keySet()方法相同
     */
    Collection<V> values();

    /**
     * 返回所有元素的Key-Value(正序),其他的性质参考keySet()方法
     */
    Set<Map.Entry<K, V>> entrySet();
}

```