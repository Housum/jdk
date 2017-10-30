###Collection 接口的骨架实现，大部分的集合都会继承这个抽象类
1. 这个接口完成了大部分的基础功能，需要注意的是虽然实现了add(E)方法，但是默认是抛出了UnSupportOperationException异常
这是因为很多的集合都不支持add操作（不可变的集合）.
2. 其他的基础类都是基于抽象方法实现的,所以关于抽象方法的实现至关重要.主要是size(),iterator()(大部分的元素查找都是
基于这个做的)
3.但是子类很多都会覆盖掉原有的实现的方法

```

/**
 * 1.如果要实现一个不可变的集合,那么只需要实现iterator()方法和size()方法，以及Iterator的hasNext()以及
 * next()方法
 * 2.如果要实现一个可变的集合,那么除了上面1中提到需要实现的方法外,还需要覆盖add方法,以及Iterator的remove方法
 * @since 1.2
 */

public abstract class AbstractCollection<E> implements Collection<E> {
    /**
     * 注意这里的构造函数
     */
    protected AbstractCollection() {
    }

    // Query Operations

    /**
     * 这个是一个抽象方法，返回的是元素的Iterator,十分的重要，因为很多的操作都是基于它的
     */
    public abstract Iterator<E> iterator();

    /**
     * 因为集合会基于各种不同的方式来进行实现，所以这个方法还是实现类提供会更好一些
     */
    public abstract int size();

    /**
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * 这里就是根据Iterator来进行的一个运算,这个查找是一个线性的
     * 可以采用Collections.binarySearch()进行查找，但是注意这个方法需要的是一个有序的集合
     * 抛出的异常可以查找接口的文档
     */
    public boolean contains(Object o) {
        Iterator<E> it = iterator();
        if (o==null) {
            while (it.hasNext())
                if (it.next()==null)
                    return true;
        } else {
            while (it.hasNext())
                if (o.equals(it.next()))
                    return true;
        }
        return false;
    }

    /**
     * 返回一个元素的数组
     */
    public Object[] toArray() {
        // Estimate size of array; be prepared to see more or fewer elements
        Object[] r = new Object[size()];
        Iterator<E> it = iterator();
        for (int i = 0; i < r.length; i++) {
            if (! it.hasNext()) // fewer elements than expected
                return Arrays.copyOf(r, i);
            r[i] = it.next();
        }
        return it.hasNext() ? finishToArray(r, it) : r;
    }

    /**
     * 1.返回一个数组包含了所有的元素，并和toArray()不同的是,返回的是一个类型确定的元素的
     * 数组,这里需要一个参数，这个参数就是储存元素的数组（如果传入的数组不能够储存集合中的元素的话
     * 那么就会重新的构建一个数组）.
     * 2.如果数组的所能储存的类型不是集合中的类型或者不是集合元素的父类型,那么将抛出ArrayStoreException
     */
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {
        // 返回集合的个数,但是这个值是有可能和真实的数量不同.
        int size = size();
        T[] r = a.length >= size ? a :
                  (T[])java.lang.reflect.Array
                  .newInstance(a.getClass().getComponentType(), size);
        Iterator<E> it = iterator();

        for (int i = 0; i < r.length; i++) {
        
            // 如果在遍历过程中有元素被删除了
            
            if (! it.hasNext()) { // 如果集合中的元素少于数组能储存的,那么分为三种情况来分析
                if (a == r) { //如果数组是传入的，那么对于的元素将被设置为null
                    r[i] = null; // null-terminate
                    
                     // 如果给定的数组不能储存(在遍历中有一些元素被删除,但是即使是这种情况下给定的数组a仍然
                     //不能储存集合的元素，那么重新设置数组的长度
                } else if (a.length < i) {
                     return Arrays.copyOf(r, i);
                )else {
                    //除去上面的两种情况，那么将元素都拷贝到传入的数组a中
                    System.arraycopy(r, 0, a, 0, i);
                    if (a.length > i) {
                        a[i] = null;
                    }
                }
                return a;
            }
            r[i] = (T)it.next();
        }
        // 如果在遍历过程中有元素的增加，那么将这部分的严肃添加到数组中去
        return it.hasNext() ? finishToArray(r, it) : r;
    }

    /**
     * 可以分配最大的数组容量,如果分配太大的数组会导致 OutOfMemoryError 超出了VM的限制
     */
    private static final int MAX_ARRAY_SIZE  = Integer.MAX_VALUE - 8;
    /**
     * 在toArray的时候iterator返回了一个比预期更多元素(多线程环境下增加了元素),
     * 这个时候就需要将这部分的元素都添加到数组中去
     */
    @SuppressWarnings("unchecked")
    private static <T> T[] finishToArray(T[] r, Iterator<?> it) {
        int i = r.length;
        while (it.hasNext()) {
            int cap = r.length;
            if (i == cap) {
                 //新的容量将是原来容量的1.5倍
                int newCap = cap + (cap >> 1) + 1;
                // overflow-conscious code
                //如果新的容量已近大于定义的MAX_ARRAY_SIZE，那么就不要添加到1.5倍 增加1
                if (newCap - MAX_ARRAY_SIZE > 0)
                    newCap = hugeCapacity(cap + 1);
                   //拷贝一个新的容量
                r = Arrays.copyOf(r, newCap);
            }
            r[i++] = (T)it.next();
        }
        // 如果现在还超过的话,那么将那部分给截取了 
        return (i == r.length) ? r : Arrays.copyOf(r, i);
    }


    private static int hugeCapacity(int minCapacity) {
        if (minCapacity < 0) // overflow
            throw new OutOfMemoryError
                ("Required array size too large");
        return (minCapacity > MAX_ARRAY_SIZE) ?
            Integer.MAX_VALUE :
            MAX_ARRAY_SIZE;
    }

    // Modification Operations 修改操作

    /**
     * 默认的实现是抛出一个UnsupportedOperationException异常
     * 同时也会抛出其他的很多的异常,详细的可以看Collection的定义
     */
    public boolean add(E e) {
        throw new UnsupportedOperationException();
    }

    /**
     * 这个方法是通过集合的Iterator的遍历每一个元素,如果找到给定的元素那么调用Iterator
     * 的删除方法,将元素给删除掉，如果Iterator不支持remove方法的话,那么抛出unsupportOperationException异常
     * 关于抛出的异常可以查看Collection的定义
     */
    public boolean remove(Object o) {
        Iterator<E> it = iterator();
        //判断需要删除的元素是否为null（有一些集合是支持null值的）
        if (o==null) {
            while (it.hasNext()) {
                if (it.next()==null) {
                    it.remove();
                    return true;
                }
            }
        } else {
            while (it.hasNext()) {
                if (o.equals(it.next())) {
                    it.remove();
                    return true;
                }
            }
        }
        return false;
    }


    // Bulk Operations 批量操作

    /**
     * 这个方法将会遍历给定的集合c，查看集合是否包含了c中的元素
     * 如果有一个不包含,那么返回false，否则返回true
     *
     */
    public boolean containsAll(Collection<?> c) {
        for (Object e : c)
            if (!contains(e))
                return false;
        return true;
    }

    /**
     * 将参数的集合c全部插入到集合中,注意只要一个元素插入成功,那么就会返回true，只有在
     * 增加全部失败的时候才会返回false，如果集合不支持add方法,那么抛出unsupportOperationException
     * @see #add(Object)
     */
    public boolean addAll(Collection<? extends E> c) {
        boolean modified = false;
        for (E e : c)
            if (add(e))
                modified = true;
        return modified;
    }

    /**
     * 遍历给定的集合c,查看本集合是否包含c中的元素,如果包含的话，那么使用Iterator的remove方法
     * 删除元素，注意只需要删除成功一个就会返回true，如果Iterator不支持remove,那么将会抛出
     * unsupportOperationException
     * @see #remove(Object)
     * @see #contains(Object)
     */
    public boolean removeAll(Collection<?> c) {
        Objects.requireNonNull(c);
        boolean modified = false;
        Iterator<?> it = iterator();
        while (it.hasNext()) {
            if (c.contains(it.next())) {
                it.remove();
                modified = true;
            }
        }
        return modified;
    }

    /**
     * 求集合和给定的集合c的交集,如果集合的Iterator不支持remove方法
     * 那么抛出unsupportOperationException异常
     * @see #remove(Object)
     * @see #contains(Object)
     */
    public boolean retainAll(Collection<?> c) {
        Objects.requireNonNull(c);
        boolean modified = false;
        Iterator<E> it = iterator();
        while (it.hasNext()) {
            if (!c.contains(it.next())) {
                it.remove();
                modified = true;
            }
        }
        return modified;
    }

    /**
     * 清理集合中的所有的元素,如果集合的Iterator不支持remove，那么将会抛出
     * UnsupportOperationException异常
     */
    public void clear() {
        Iterator<E> it = iterator();
        while (it.hasNext()) {
            it.next();
            it.remove();
        }
    }


    //  String conversion

    /** 
     * 返回集合的String表示
     */
    public String toString() {
        Iterator<E> it = iterator();
        if (! it.hasNext())
            return "[]";

        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (;;) {
            E e = it.next();
            sb.append(e == this ? "(this Collection)" : e);
            if (! it.hasNext())
                return sb.append(']').toString();
            sb.append(',').append(' ');
        }
    }

}

```