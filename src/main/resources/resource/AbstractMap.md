### Map的骨架实现

```
/**
 * 这个类是Map接口的骨架实现,这样子类可以通过继承这个骨架很很容易的实现自己的Map
 * 1.如果实现的是一个只读的接口,那么只需要实现这个基类,并且实现entrySet()方法,并且提供
 * 一个只读的视图(一般都是实现的AbstractSet),这个视图不支持add或者remove方法
 * 2.如果子类需要实现一个改变的类的话,你那么就必须覆盖put方法,不然的话就会抛出UOE
 * 同时也需要实现entrySet()方法，并且在entrySet().iterator必须是支持remove方法的
 *
 */

public abstract class AbstractMap<K,V> implements Map<K,V> {
    /**
     * 注意这里： 子类必须显式的提供构造函数
     */
    protected AbstractMap() {
    }

    // 查看操作

    /**
     */
    public int size() {
        return entrySet().size();
    }

    /**
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * 1.查看value中是否保存给定的value，如果存在的话那么返回true，如果
     * 没有找到的话 那么返回false
     * 2.这个方法的实现方式是获得map的entrySet，然后进行遍历,所以时间复杂度为
     * O(n)
     */
    public boolean containsValue(Object value) {
        Iterator<Entry<K,V>> i = entrySet().iterator();
        if (value==null) {
            while (i.hasNext()) {
                Entry<K,V> e = i.next();
                if (e.getValue()==null)
                    return true;
            }
        } else {
            while (i.hasNext()) {
                Entry<K,V> e = i.next();
                if (value.equals(e.getValue()))
                    return true;
            }
        }
        return false;
    }

    /**
     * 查看map中是否包含给定的key，其他的参考containsValue(obj)
     */
    public boolean containsKey(Object key) {
        Iterator<Map.Entry<K,V>> i = entrySet().iterator();
        if (key==null) {
            while (i.hasNext()) {
                Entry<K,V> e = i.next();
                if (e.getKey()==null)
                    return true;
            }
        } else {
            while (i.hasNext()) {
                Entry<K,V> e = i.next();
                if (key.equals(e.getKey()))
                    return true;
            }
        }
        return false;
    }

    /**
     * 查询key对应的value，如果没有查找到那么返回null
     */
    public V get(Object key) {
        Iterator<Entry<K,V>> i = entrySet().iterator();
        if (key==null) {
            while (i.hasNext()) {
                Entry<K,V> e = i.next();
                if (e.getKey()==null)
                    return e.getValue();
            }
        } else {
            while (i.hasNext()) {
                Entry<K,V> e = i.next();
                if (key.equals(e.getKey()))
                    return e.getValue();
            }
        }
        return null;
    }


    // Modification Operations

    /**
     * 将key和value设置到map中,默认的实现是抛出UOE
    public V put(K key, V value) {
        throw new UnsupportedOperationException();
    }

    /**
     * 1.将给定的key映射的记录删除,并且将value返回,如果不存在的话,那么将其删除
     * 2.这个方法实现的方式是获得entrySet的Iterator然后遍历找到给定的元素（如果存在）
     * 并且将其删除
     * 3，要求map是一个可变的,即Iterator支持remove方法，否则抛出UOE
     */
    public V remove(Object key) {
        Iterator<Entry<K,V>> i = entrySet().iterator();
        Entry<K,V> correctEntry = null;
        if (key==null) {
            while (correctEntry==null && i.hasNext()) {
                Entry<K,V> e = i.next();
                if (e.getKey()==null)
                    correctEntry = e;
            }
        } else {
            while (correctEntry==null && i.hasNext()) {
                Entry<K,V> e = i.next();
                if (key.equals(e.getKey()))
                    correctEntry = e;
            }
        }

        V oldValue = null;
        if (correctEntry !=null) {
            oldValue = correctEntry.getValue();
            i.remove();
        }
        return oldValue;
    }


    // Bulk Operations

    /**
     * 将所有的元素都增加到map中
     */
    public void putAll(Map<? extends K, ? extends V> m) {
        for (Map.Entry<? extends K, ? extends V> e : m.entrySet())
            put(e.getKey(), e.getValue());
    }

    /**
     * 清理所有的元素
     */
    public void clear() {
        entrySet().clear();
    }


    // Views

    /**
     * 1.对于key的set和value的collection都是在首次被初始化(lazy init ),并且只需要初始化一次
     * 因为他们的set都不做实际的储存,其实都是在代理Map的操作
     */
    transient Set<K>        keySet;
    transient Collection<V> values;

    /**
     * 返回一个key的Set,这是一个代理类，代理map中的操作
     */
    public Set<K> keySet() {
        Set<K> ks = keySet;
        if (ks == null) {
            ks = new AbstractSet<K>() {
                public Iterator<K> iterator() {
                    return new Iterator<K>() {
                        private Iterator<Entry<K,V>> i = entrySet().iterator();

                        public boolean hasNext() {
                            return i.hasNext();
                        }

                        public K next() {
                            return i.next().getKey();
                        }

                        public void remove() {
                            i.remove();
                        }
                    };
                }

                public int size() {
                    return AbstractMap.this.size();
                }

                public boolean isEmpty() {
                    return AbstractMap.this.isEmpty();
                }

                public void clear() {
                    AbstractMap.this.clear();
                }

                public boolean contains(Object k) {
                    return AbstractMap.this.containsKey(k);
                }
            };
            keySet = ks;
        }
        return ks;
    }

    /**
     * 同理
     */
    public Collection<V> values() {
        Collection<V> vals = values;
        if (vals == null) {
            vals = new AbstractCollection<V>() {
                public Iterator<V> iterator() {
                    return new Iterator<V>() {
                        private Iterator<Entry<K,V>> i = entrySet().iterator();

                        public boolean hasNext() {
                            return i.hasNext();
                        }

                        public V next() {
                            return i.next().getValue();
                        }

                        public void remove() {
                            i.remove();
                        }
                    };
                }

                public int size() {
                    return AbstractMap.this.size();
                }

                public boolean isEmpty() {
                    return AbstractMap.this.isEmpty();
                }

                public void clear() {
                    AbstractMap.this.clear();
                }

                public boolean contains(Object v) {
                    return AbstractMap.this.containsValue(v);
                }
            };
            values = vals;
        }
        return vals;
    }

    /**
     * 返回key与value的集合
     */
    public abstract Set<Entry<K,V>> entrySet();


    // Comparison and hashing

    /**
     */
    public boolean equals(Object o) {
        if (o == this)
            return true;

        if (!(o instanceof Map))
            return false;
        Map<?,?> m = (Map<?,?>) o;
        if (m.size() != size())
            return false;

        try {
            Iterator<Entry<K,V>> i = entrySet().iterator();
            while (i.hasNext()) {
                Entry<K,V> e = i.next();
                K key = e.getKey();
                V value = e.getValue();
                if (value == null) {
                    if (!(m.get(key)==null && m.containsKey(key)))
                        return false;
                } else {
                    if (!value.equals(m.get(key)))
                        return false;
                }
            }
        } catch (ClassCastException unused) {
            return false;
        } catch (NullPointerException unused) {
            return false;
        }

        return true;
    }

     */
    public int hashCode() {
        int h = 0;
        Iterator<Entry<K,V>> i = entrySet().iterator();
        while (i.hasNext())
            h += i.next().hashCode();
        return h;
    }

    /**
     */
    public String toString() {
        Iterator<Entry<K,V>> i = entrySet().iterator();
        if (! i.hasNext())
            return "{}";

        StringBuilder sb = new StringBuilder();
        sb.append('{');
        for (;;) {
            Entry<K,V> e = i.next();
            K key = e.getKey();
            V value = e.getValue();
            sb.append(key   == this ? "(this Map)" : key);
            sb.append('=');
            sb.append(value == this ? "(this Map)" : value);
            if (! i.hasNext())
                return sb.append('}').toString();
            sb.append(',').append(' ');
        }
    }

    /**
     */
    protected Object clone() throws CloneNotSupportedException {
        AbstractMap<?,?> result = (AbstractMap<?,?>)super.clone();
        result.keySet = null;
        result.values = null;
        return result;
    }

    /**
     */
    private static boolean eq(Object o1, Object o2) {
        return o1 == null ? o2 == null : o1.equals(o2);
    }

    //注意下面的两个类并没有和Map有任何的关联，

    /**
     * 1.Map映射的key和value储存的对象，可以通过setValue将元素进行替换
     * 2.在自己实现的时候会非常的有用
     *
     * @since 1.6
     */
    public static class SimpleEntry<K,V>
        implements Entry<K,V>, java.io.Serializable
    {
        private static final long serialVersionUID = -8499721149061103585L;

        private final K key;
        private V value;

        /**
         */
        public SimpleEntry(K key, V value) {
            this.key   = key;
            this.value = value;
        }

        /**
         */
        public SimpleEntry(Entry<? extends K, ? extends V> entry) {
            this.key   = entry.getKey();
            this.value = entry.getValue();
        }

         */
        public K getKey() {
            return key;
        }

        /**
         */
        public V getValue() {
            return value;
        }

        /**
         * 将替换之前的值给返回
         */
        public V setValue(V value) {
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }

        /**
         */
        public boolean equals(Object o) {
            if (!(o instanceof Map.Entry))
                return false;
            Map.Entry<?,?> e = (Map.Entry<?,?>)o;
            return eq(key, e.getKey()) && eq(value, e.getValue());
        }

        /**
         */
        public int hashCode() {
            return (key   == null ? 0 :   key.hashCode()) ^
                   (value == null ? 0 : value.hashCode());
        }

        /**
         */
        public String toString() {
            return key + "=" + value;
        }

    }

    /**
     * 实现一个最简单的不可变的Entry,SimpleImmutableEntry不支持setValue
     * 调用这个方法将会抛出UOE，对于一些需要返回线程安全的视图的情况下,这个是
     * 比较好用的
     *
     * @since 1.6
     */
    public static class SimpleImmutableEntry<K,V>
        implements Entry<K,V>, java.io.Serializable
    {
        private static final long serialVersionUID = 7138329143949025153L;

        private final K key;
        private final V value;

        /**
         * key - value
         */
        public SimpleImmutableEntry(K key, V value) {
            this.key   = key;
            this.value = value;
        }

        /**
         * 根据给定Entry 构造Entry
         */
        public SimpleImmutableEntry(Entry<? extends K, ? extends V> entry) {
            this.key   = entry.getKey();
            this.value = entry.getValue();
        }

        /**
         * 返回Key
         */
        public K getKey() {
            return key;
        }

        /**
         * 返回value
         */
        public V getValue() {
            return value;
        }

        /**不支持setValue
         * 
         */
        public V setValue(V value) {
            throw new UnsupportedOperationException();
        }

        /**
         * 对比两个Entry是否相等
         */
        public boolean equals(Object o) {
            if (!(o instanceof Map.Entry))
                return false;
            Map.Entry<?,?> e = (Map.Entry<?,?>)o;
            return eq(key, e.getKey()) && eq(value, e.getValue());
        }

        /**
         * 返回hashCode
         */
        public int hashCode() {
            return (key   == null ? 0 :   key.hashCode()) ^
                   (value == null ? 0 : value.hashCode());
        }

        /**
         * 返回toString
         */
        public String toString() {
            return key + "=" + value;
        }

    }

}


```