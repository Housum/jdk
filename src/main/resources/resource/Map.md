### Map 接口
```
/**
 * 1.键值对，一个map不能存放重复的Key，一个key最多对应一个值
 * 这个接口是替代Dictionary的
 * 2.接口提供了三种类型的视图：1，键 2值 3 键值对.
 * 3.map内部的顺序体现在他们的视图集合的Iterator的顺序，一些map是保证了顺序，比如：TreeMap,但是一些却不能保证顺序，比如:HashMap
 */
public interface Map<K,V> {
    // Query Operations

    /**
     * 返回Map中元素的数量  如果大于 Integer.MAX_VALUE 那么返回Integer.MAX_VALUE
     */
    int size();

    /**
     * 返回map是否为空
     */
    boolean isEmpty();

    /**
     *  如果map包含了这个key，那么返回true，否者返回false，
     *  如果类型不匹配，那么抛出ClassCastException(可选)
     *  如果key为null，那么抛出NPE（可选，不允许为null的时候）
     */
    boolean containsKey(Object key);

    /**
     *  如果map的值包含给定value的话，那么返回true，或者返回false
     *  如果类型不匹配，那么抛出ClassCastException(可选)
     *  如果key为null，那么抛出NPE（可选，不允许为null的时候）
     */
    boolean containsValue(Object value);

    /**
     * 1.返回key对应的value，如果没有的话，那么返回null（大部分的情况下爱）
     *
     * 2.但是在一些的情况，map可能会允许null值的插入,那么返回null值并不能说明
     *  map中不存在key对应的值，可以通过containKey来区分这两种情况
     *  如果类型不匹配，那么抛出ClassCastException(可选)
     *  如果key为null，那么抛出NPE（可选，不允许为null的时候）
     */
    V get(Object key);

    // Modification Operations

    /**
     * 1.在map中存放 key及value对，如果map中已经存在了key的记录，那么就将旧的值给覆盖掉
     * 2.如果存在旧值，那么就返回。如果不存在的话，那么返回null（如果允许为null的情况，那么就需要
     * 使用containsKey来区分这两种情况）
     * 3. 如果不支持这个方法，那么抛出UnsupportedOperationException异常
     *  如果类型不匹配，那么抛出ClassCastException(可选)
     *  如果key或者value为null，那么抛出NPE（不允许为null的情况下）
     * 如果参数的某些属性不符合，那么抛出IllegalArgumentException
     */
    V put(K key, V value);

    /**
     * 删除给定key的记录，并且返回value，如果不允许插入值为null的情况，那么
     * 返回null表示不存在这个记录吗，否则返回被删除的值。如果允许值为null的话，那么
     * 返回null并不能被认为不存在这个记录
     */
    V remove(Object key);


    // Bulk Operations

    /**
     * 将map中的所有的键值都拷贝到map中去，但是在插入过程中map修改的花，并没有定义
     * 如果不支持这个方法，那么抛出UnsupportedOperationException异常
     * 如果类型不匹配，那么抛出ClassCastException(可选)
     * 如果key或者value为null，那么抛出NPE（不允许为null的情况下）
     * 如果参数的某些属性不符合，那么抛出IllegalArgumentException
     */
    void putAll(Map<? extends K, ? extends V> m);

    /**
     * 清理所有的数据，如果不支持这个方法，那么抛出UnsupportedOperationException异常
     * 
     */
    void clear();


    // Views

    /**
     * 返回map的键的集合,这个集合是一个视图，两边的修改都是相互的影响，当已经获得这个set的Iterator
     * 之后，map修改的话并没有定义。这个set支持remove，removeAll，retainAll，clear
     * ,这些操作都反映到map上，但是这个set并不支持add以及addAll方法
     */
    Set<K> keySet();

    /**
     * 返回map的值的集合，这个集合是一个视图，两边的修改都是相互的影响，当已经获得这个set的Iterator
     * 之后，map修改的话并没有定义。这个set支持remove，removeAll，retainAll，clear
     * ,这些操作都反映到map上，但是这个set并不支持add以及addAll方法
     */
    Collection<V> values();

    /**
     * 返回map中的全部的键值对，和前面的一样和map是相互影响的，并且支持remove，removeAll，retainAll，clear
     * 但是不支持all以及addAll
     */
    Set<Map.Entry<K, V>> entrySet();

    /**
     * map中的键值对，Map中的方法Map.entrySet会返回这个Entry，这是唯一获取map中的Entry的方式，如果在已经
     * 获取Entry的情况下修改了map，那么他的行为是没有被定义的
     * @see Map#entrySet()
     */
    interface Entry<K,V> {
    
        /**
         * 单个键值对的Key，如果获取过程中被map删除了，那么抛出IllegalStateException（可选）
         */
        K getKey();

        /**
         * 获得单个键值对的值，如果获取过程中被map删除了，那么抛出IllegalStateException（可选）
         */
        V getValue();

        /**
         * 替换旧值，返回旧值
         * 如果不支持这个方法，那么抛出UnsupportedOperationException异常
         * 如果类型不匹配，那么抛出ClassCastException(可选)
         * 如果key或者value为null，那么抛出NPE（不允许为null的情况下）
         * 如果参数的某些属性不符合，那么抛出IllegalArgumentException
         */
        V setValue(V value);
        
        /**
         * 判断两个Entry是否相等
         */
        boolean equals(Object o);

        int hashCode();

        /**
         * 返回根据Map的键对比的Comparable
         * @since 1.8
         */
        public static <K extends Comparable<? super K>, V> Comparator<Map.Entry<K,V>> comparingByKey() {
            return (Comparator<Map.Entry<K, V>> & Serializable)
                (c1, c2) -> c1.getKey().compareTo(c2.getKey());
        }

        /** 同理
         * @since 1.8
         */
        public static <K, V extends Comparable<? super V>> Comparator<Map.Entry<K,V>> comparingByValue() {
            return (Comparator<Map.Entry<K, V>> & Serializable)
                (c1, c2) -> c1.getValue().compareTo(c2.getValue());
        }

        /**
         * 同理
         * @since 1.8
         */
        public static <K, V> Comparator<Map.Entry<K, V>> comparingByKey(Comparator<? super K> cmp) {
            Objects.requireNonNull(cmp);
            return (Comparator<Map.Entry<K, V>> & Serializable)
                (c1, c2) -> cmp.compare(c1.getKey(), c2.getKey());
        }

        /**
         * 同理
         * @since 1.8
         */
        public static <K, V> Comparator<Map.Entry<K, V>> comparingByValue(Comparator<? super V> cmp) {
            Objects.requireNonNull(cmp);
            return (Comparator<Map.Entry<K, V>> & Serializable)
                (c1, c2) -> cmp.compare(c1.getValue(), c2.getValue());
        }
    }

    // Comparison and hashing
    boolean equals(Object o);

    int hashCode();

    // Defaultable methods

    /**
     * 获得值，如果没有查询到，那么返回默认值
     * @since 1.8
     */
    default V getOrDefault(Object key, V defaultValue) {
        V v;
        return (((v = get(key)) != null) || containsKey(key))
            ? v
            : defaultValue;
    }

    /**
     *
     * 遍历map中的每一个键值对，并对一个键值对执行给定的action或者因为抛出unchecked异常而中断
     * 如果在遍历过程中元素被删除的话，那么抛出ConcurrentModificationException异常
     * @since 1.8
     */
    default void forEach(BiConsumer<? super K, ? super V> action) {
        Objects.requireNonNull(action);
        for (Map.Entry<K, V> entry : entrySet()) {
            K k;
            V v;
            try {
                k = entry.getKey();
                v = entry.getValue();
            } catch(IllegalStateException ise) {
                // this usually means the entry is no longer in the map.
                throw new ConcurrentModificationException(ise);
            }
            action.accept(k, v);
        }
    }

    /**
     * 遍历map中的每一个元素，并且执行BiFunction，第一个参数为k，第二个参数为v，返回值为v,返回的v会将旧值替代掉
     * 如果在遍历过程中元素被删除的话，那么抛出ConcurrentModificationException异常
     * @since 1.8
     */
    default void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
        Objects.requireNonNull(function);
        for (Map.Entry<K, V> entry : entrySet()) {
            K k;
            V v;
            try {
                k = entry.getKey();
                v = entry.getValue();
            } catch(IllegalStateException ise) {
                // this usually means the entry is no longer in the map.
                throw new ConcurrentModificationException(ise);
            }

            // ise thrown from function is not a cme.
            v = function.apply(k, v);

            try {
                entry.setValue(v);
            } catch(IllegalStateException ise) {
                // this usually means the entry is no longer in the map.
                throw new ConcurrentModificationException(ise);
            }
        }
    }

    /**
     * 如果key对应的参数不存在的话，那么进行设置，否则不进行任何的操作
     * @since 1.8
     */
    default V putIfAbsent(K key, V value) {
        V v = get(key);
        if (v == null) {
            v = put(key, value);
        }

        return v;
    }

    /**
     * 删除给定的key并且value为给定的记录
     * @since 1.8
     */
    default boolean remove(Object key, Object value) {
        Object curValue = get(key);
        if (!Objects.equals(curValue, value) ||
            (curValue == null && !containsKey(key))) {
            return false;
        }
        remove(key);
        return true;
    }

    /**
     * 如果ke对应的元素存在并且对应的元素为oldValue，那么利用newValue代替oldValue
     * @since 1.8
     */
    default boolean replace(K key, V oldValue, V newValue) {
        Object curValue = get(key);
        if (!Objects.equals(curValue, oldValue) ||
            (curValue == null && !containsKey(key))) {
            return false;
        }
        put(key, newValue);
        return true;
    }

    /**
     * 如果存在的话，那就进行替代，最后返回的是key对应的value
     * @since 1.8
     */
    default V replace(K key, V value) {
        V curValue;
        if (((curValue = get(key)) != null) || containsKey(key)) {
            curValue = put(key, value);
        }
        return curValue;
    }

    /**
     * 如果key对应的记录不存在，那么进行compute
     * @since 1.8
     */
    default V computeIfAbsent(K key,
            Function<? super K, ? extends V> mappingFunction) {
        Objects.requireNonNull(mappingFunction);
        V v;
        if ((v = get(key)) == null) {
            V newValue;
            if ((newValue = mappingFunction.apply(key)) != null) {
                put(key, newValue);
                return newValue;
            }
        }

        return v;
    }

    /**
     * 如果key对应的value存在，那么执行compute
     * @since 1.8
     */
    default V computeIfPresent(K key,
            BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        Objects.requireNonNull(remappingFunction);
        V oldValue;
        if ((oldValue = get(key)) != null) {
            V newValue = remappingFunction.apply(key, oldValue);
            if (newValue != null) {
                put(key, newValue);
                return newValue;
            } else {
                remove(key);
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * key对应的value的键值对进行BiFunction，如果计算结果为null，那么将记录删除，否则用
     * 计算出来的代替旧值
     * @since 1.8
     */
    default V compute(K key,
            BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        Objects.requireNonNull(remappingFunction);
        V oldValue = get(key);

        V newValue = remappingFunction.apply(key, oldValue);
        if (newValue == null) {
            // delete mapping
            if (oldValue != null || containsKey(key)) {
                // something to remove
                remove(key);
                return null;
            } else {
                // nothing to do. Leave things as they were.
                return null;
            }
        } else {
            // add or replace old mapping
            put(key, newValue);
            return newValue;
        }
    }

    /**
     * 1.如果key的记录不存在话或者key对应的值为null，那么就将key对应的value作为键值对存入到map中去
     * 2.否则使用BiFunction的返回值作为value代替原来的值，如果BiFunction返回的是null，那么在map中将记录删除
     * BiFunction有两个参数，第一个参数为旧值  第二个参数为传入的新值
     * 3.典型用法: map.merge(key, msg, String::concat)
     * 如果不支持这个方法，那么抛出UnsupportedOperationException异常
     * 如果类型不匹配，那么抛出ClassCastException(可选)
     * 如果key或者value为null，那么抛出NPE（不允许为null的情况下）
     * @since 1.8 
     */
    default V merge(K key, V value,
            BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        Objects.requireNonNull(remappingFunction);
        Objects.requireNonNull(value);
        V oldValue = get(key);
        V newValue = (oldValue == null) ? value :
                   remappingFunction.apply(oldValue, value);
        if(newValue == null) {
            remove(key);
        } else {
            put(key, newValue);
        }
        return newValue;
    }
}

```