### AbstractList 的骨架实现类
```

/**
 * 1.这是一个List的骨架实现,如果要实现一个随机存储的集合（比如依赖于数组的）
 * 那么就可以直接的继承这个抽象类,如果是一个顺序存储的(比如LinkedList)那么
 * AbstractSequentialList比这个类更加好
 * 2.如果需要实现一个不可变的list，那么继承这个抽象类，并且实现两个抽象方法:get(index)
 * size()方法就可以了，如果实现的是可变的list，那么就需要实现set（index,e） add(index,e)
 * remove(index)方法，否则会抛出UnsuportedOperationException异常
 * 3.这里可以注意到一点：这里 提供了迭代子iterator listIterator的实现，子类不需要自己再去实现了，
 * 但是这两个都是基于集合的
 * {@link #get(int)},
 * {@link #set(int, Object) set(int, E)},
 * {@link #add(int, Object) add(int, E)} and
 * {@link #remove(int)}.
 * 4.虽然大部分方法都提供了实现,但是子类可以覆盖这些方法，提供更加详细的设计
 */

public abstract class AbstractList<E> extends AbstractCollection<E> implements List<E> {
    /**
     * 注意这里的访问权限,隐式的告诉子类必须实现
     */
    protected AbstractList() {
    }

    /**
     * 添加元素到list中（可选）
     * 
     * 1.对于不同的子类有不同实现，有一些子类不支持添加null值，有一些子类不支持某些
     * 元素，子类需要在文档中说明.这个方法实际上调用的是 add(size(), e)
     * 如果不支持 add(size(), e) 那么这个方法回抛出UnsupportedOperationException
     * 2.其他抛出的异常可以查看List的文档
     */
    public boolean add(E e) {
        add(size(), e);
        return true;
    }

    /**
     * 1.因为对于子类来说,实现的方式是有很多的,所以这个操作很关键,所以让不同的子类自己来进行实现
     * 2.抛出的异常可以查看接口的定义
     */
    abstract public E get(int index);

    /**
     * 虽然实现了改方法，但是却抛出了异常
     */
    public E set(int index, E element) {
        throw new UnsupportedOperationException();
    }

    /**
     * 默认抛出UOE
     */
    public void add(int index, E element) {
        throw new UnsupportedOperationException();
    }

    /**
     * 默认抛出UOE
     */
    public E remove(int index) {
        throw new UnsupportedOperationException();
    }


    // Search Operations

    /**
     * 这个方法會獲取ListIterator,然后從第一個遍历列表中的元素查询是否包含该元素
     */
    public int indexOf(Object o) {
        ListIterator<E> it = listIterator();
        if (o==null) {
            while (it.hasNext())
                if (it.next()==null)
                    return it.previousIndex();
        } else {
            while (it.hasNext())
                if (o.equals(it.next()))
                    return it.previousIndex();
        }
        return -1;
    }

    /**
     * 會先獲取列表的ListIterator，然後從後面前面遍历找出给定的元素
     */
    public int lastIndexOf(Object o) {
        ListIterator<E> it = listIterator(size());
        if (o==null) {
            while (it.hasPrevious())
                if (it.previous()==null)
                    return it.nextIndex();
        } else {
            while (it.hasPrevious())
                if (o.equals(it.previous()))
                    return it.nextIndex();
        }
        return -1;
    }


    // Bulk Operations

    /**
     * 清理列表中所有的元素,这个方法依赖remove（index）
     * 如果没有被覆盖,那么会抛出UOE
     */
    public void clear() {
        removeRange(0, size());
    }

    /**
     * 将参数中的所有的元素都插入到列表中,这个方法是依赖于add(index,E)
     * 这个方法的,如果没有实现add（index，e）将会抛出UOE
     */
    public boolean addAll(int index, Collection<? extends E> c) {
        rangeCheckForAdd(index);
        boolean modified = false;
        for (E e : c) {
            add(index++, e);
            modified = true;
        }
        return modified;
    }


    // Iterators

    /**
     * 1.返回一个列表的Iterator,迭代子是依赖于集合中的元素（只是一个视图），并且迭代子的
     * 的方法都是依赖于列表中的方法: size(),get(index),remove(index).如果不支持
     * 的话,那么抛出UOE
     * 2.注意： 如果获取了迭代子之后,列表出现删除元素的情况下,默认会抛出ConcurrentModificationException异常
     * 通过modCount实现 
     */
    public Iterator<E> iterator() {
        return new Itr();
    }

    /**
     * 1.和iterator()类似，但是返回的是一个ListIterator，同时依赖的方法也是相同的
     * 2.ConcurrentModificationException 异常抛出的场景是一样的
     */
    public ListIterator<E> listIterator() {
        return listIterator(0);
    }

    /**
     * @see ListIterator(),不同点在于可以指定开始迭代的索引，如果超出了索引
     * 值,那么抛出IndexOutOfBoundsException异常
     */
    public ListIterator<E> listIterator(final int index) {
        rangeCheckForAdd(index);

        return new ListItr(index);
    }

    /**
     *  默认的Iterator的实现
     **/
    private class Itr implements Iterator<E> {
        /**
         * 下一次返回的元素的索引
         */
        int cursor = 0;

        /**
         * 返回上一次返回的元素的索引,如果元素被删除的话,那么会被重设置为-1
         */
        int lastRet = -1;

        /**
         * iterator可以根据这个只来判断并发的修改
         * 具体： 在列表删除元素的时候,那么modCount会被修改,这样
         * expectedModCount就会和modCount不一样,这样就能发现并发的修改
         */
        int expectedModCount = modCount;

        
        public boolean hasNext() {
            return cursor != size();
        }

        public E next() {
            checkForComodification();
            try {
                int i = cursor;
                //依赖列表
                E next = get(i);
                lastRet = i;
                cursor = i + 1;
                return next;
            } catch (IndexOutOfBoundsException e) {
                //判断是否是因为并发修改导致的,那么抛出ConcurrentModificationException异常（
                checkForComodification();
                throw new NoSuchElementException();
            }
        }

        public void remove() {
            if (lastRet < 0)
                throw new IllegalStateException();
            checkForComodification();

            try {
                //依赖列表的删除方法
                AbstractList.this.remove(lastRet);
                if (lastRet < cursor)
                    cursor--;
                lastRet = -1;
                //本身删除元素并不会导致并发异常 
                expectedModCount = modCount;
            } catch (IndexOutOfBoundsException e) {
                throw new ConcurrentModificationException();
            }
        }

        final void checkForComodification() {
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
        }
    }
    
    /**
     * 默认的ListIterator  
     */
    private class ListItr extends Itr implements ListIterator<E> {
        ListItr(int index) {
            cursor = index;
        }

        
        public boolean hasPrevious() {
            return cursor != 0;
        }
        
        public E previous() {
            checkForComodification();
            try {
                int i = cursor - 1;
                E previous = get(i);
                lastRet = cursor = i;
                return previous;
            } catch (IndexOutOfBoundsException e) {
                checkForComodification();
                throw new NoSuchElementException();
            }
        }

        public int nextIndex() {
            return cursor;
        }

        public int previousIndex() {
            return cursor-1;
        }

        public void set(E e) {
            if (lastRet < 0)
                throw new IllegalStateException();
            checkForComodification();

            try {
                AbstractList.this.set(lastRet, e);
                expectedModCount = modCount;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        public void add(E e) {
            checkForComodification();

            try {
                int i = cursor;
                AbstractList.this.add(i, e);
                lastRet = -1;
                cursor = i + 1;
                expectedModCount = modCount;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }
    }

    /**
    * 返回一个列表的子列表，这儿列表从索引fromIndex 到 toIndex -1 
    */
    public List<E> subList(int fromIndex, int toIndex) {
       //这里为什么需要判断RandomAccess呢？ 因为查看RandomAccess接口我们可以发现
       // 很多的集合工具类利用这个接口来判断一个集合的实现方式:是基于随机储存；还是基于顺序存储来选择不同算法
        return (this instanceof RandomAccess ?
                new RandomAccessSubList<>(this, fromIndex, toIndex) :
                new SubList<>(this, fromIndex, toIndex));
    }

    // Comparison and hashing

    /**
     * 如果传入的是集合并且集合的size相同,并且每一个索引的值是相等（equal）
     * 那么返回true 否则返回false
     */
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof List))
            return false;

        ListIterator<E> e1 = listIterator();
        ListIterator<?> e2 = ((List<?>) o).listIterator();
        while (e1.hasNext() && e2.hasNext()) {
            E o1 = e1.next();
            Object o2 = e2.next();
            if (!(o1==null ? o2==null : o1.equals(o2)))
                return false;
        }
        return !(e1.hasNext() || e2.hasNext());
    }

    /**
     * 返回hashCode
     */
    public int hashCode() {
        int hashCode = 1;
        for (E e : this)
            hashCode = 31*hashCode + (e==null ? 0 : e.hashCode());
        return hashCode;
    }

    /**
     * 删除一个范围的元素,在clear以及SubList中使用到
     */
    protected void removeRange(int fromIndex, int toIndex) {
        ListIterator<E> it = listIterator(fromIndex);
        for (int i=0, n=toIndex-fromIndex; i<n; i++) {
            it.next();
            it.remove();
        }
    }

    /**
     * 1.这个字段记录列表结构改变的次数,如果没有这个的话,那么在迭代子迭代的过程总结构的改变
     * 将会导致不正确的结果
     * 2.这个值的使用场景是在iterator或者listIterator方法返回迭代子之后判断是否有并发修改
     * 的情况,如果存在并发修改的话,那么在调用next,remove,previous,set,add 方法的时候将会
     * 抛出ConcurrentModificationException异常（快速的失败）,而不是在并发修改的情况下
     * 没有任何的反应
     * 3. 这个值的用法是可选的，如果是子类需要实现一个快速失败的迭代子,那么可以在子类的remove(e)
     * 和add(index,e)方法(或者其他会改变其结构的方法)的时候增加modCount的值!.例子可以参考ArrayList,LinkedList
     * 4.如果不需要支持快速失败,那么可以选择忽略这个字段
     * 5.单独调用add(index,e)或者remove(index)不能增加超出1 不然可能不正确的ConcurrentModificationExceptions
     *
     */
    protected transient int modCount = 0;

    private void rangeCheckForAdd(int index) {
        if (index < 0 || index > size())
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }

    private String outOfBoundsMsg(int index) {
        return "Index: "+index+", Size: "+size();
    }
}

/**
 * 方法subList()的方法子列表，这里也使用到了modCount，进行并发修改的判断
 */
class SubList<E> extends AbstractList<E> {
    private final AbstractList<E> l;
    private final int offset;
    private int size;

    /**
     *fromIndex 作为开始的索引,toIndex -1  作为最后一个元素的索引 toIndex - fromIndx 作为size
     */
    SubList(AbstractList<E> list, int fromIndex, int toIndex) {
        if (fromIndex < 0)
            throw new IndexOutOfBoundsException("fromIndex = " + fromIndex);
        if (toIndex > list.size())
            throw new IndexOutOfBoundsException("toIndex = " + toIndex);
        if (fromIndex > toIndex)
            throw new IllegalArgumentException("fromIndex(" + fromIndex +
                                               ") > toIndex(" + toIndex + ")");
        l = list;
        offset = fromIndex;
        size = toIndex - fromIndex;
        this.modCount = l.modCount;
    }

    public E set(int index, E element) {
        rangeCheck(index);
        checkForComodification();
        return l.set(index+offset, element);
    }

    public E get(int index) {
        rangeCheck(index);
        checkForComodification();
        return l.get(index+offset);
    }

    public int size() {
        checkForComodification();
        return size;
    }

    public void add(int index, E element) {
        rangeCheckForAdd(index);
        checkForComodification();
        l.add(index+offset, element);
        this.modCount = l.modCount;
        size++;
    }

    public E remove(int index) {
        rangeCheck(index);
        checkForComodification();
        E result = l.remove(index+offset);
        this.modCount = l.modCount;
        size--;
        return result;
    }

    protected void removeRange(int fromIndex, int toIndex) {
        checkForComodification();
        l.removeRange(fromIndex+offset, toIndex+offset);
        this.modCount = l.modCount;
        size -= (toIndex-fromIndex);
    }

    public boolean addAll(Collection<? extends E> c) {
        return addAll(size, c);
    }

    public boolean addAll(int index, Collection<? extends E> c) {
        rangeCheckForAdd(index);
        int cSize = c.size();
        if (cSize==0)
            return false;

        checkForComodification();
        l.addAll(offset+index, c);
        this.modCount = l.modCount;
        size += cSize;
        return true;
    }

    public Iterator<E> iterator() {
        return listIterator();
    }

    public ListIterator<E> listIterator(final int index) {
        checkForComodification();
        rangeCheckForAdd(index);

        return new ListIterator<E>() {
            private final ListIterator<E> i = l.listIterator(index+offset);

            public boolean hasNext() {
                return nextIndex() < size;
            }

            public E next() {
                if (hasNext())
                    return i.next();
                else
                    throw new NoSuchElementException();
            }

            public boolean hasPrevious() {
                return previousIndex() >= 0;
            }

            public E previous() {
                if (hasPrevious())
                    return i.previous();
                else
                    throw new NoSuchElementException();
            }

            public int nextIndex() {
                return i.nextIndex() - offset;
            }

            public int previousIndex() {
                return i.previousIndex() - offset;
            }

            public void remove() {
                i.remove();
                SubList.this.modCount = l.modCount;
                size--;
            }

            public void set(E e) {
                i.set(e);
            }

            public void add(E e) {
                i.add(e);
                SubList.this.modCount = l.modCount;
                size++;
            }
        };
    }
    
    public List<E> subList(int fromIndex, int toIndex) {
        return new SubList<>(this, fromIndex, toIndex);
    }

    private void rangeCheck(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }

    private void rangeCheckForAdd(int index) {
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }

    private String outOfBoundsMsg(int index) {
        return "Index: "+index+", Size: "+size;
    }

    private void checkForComodification() {
        if (this.modCount != l.modCount)
            throw new ConcurrentModificationException();
    }
}

/**
 * 虽然这里看起来没有实现其他的操作，只是添加了一个RandomAccess接口,但是其实是非常重要的
 * 因为很多的算法都是基于RandomAccess这个接口来的，例子： Collections.sort(c)
 */
class RandomAccessSubList<E> extends SubList<E> implements RandomAccess {
    RandomAccessSubList(AbstractList<E> list, int fromIndex, int toIndex) {
        super(list, fromIndex, toIndex);
    }

    public List<E> subList(int fromIndex, int toIndex) {
        return new RandomAccessSubList<>(this, fromIndex, toIndex);
    }
}

```