###AbstractSequentialList List的骨架实现
这个基类适合顺序存储的实现（like : LinkedList) ，继承了AbstractList
```

package java.util;

/** 
 * 1.这个基类实现了List的骨架,这样子类实现一个顺序存储的列表将会十分的容易，
 * 只需要实现ListIterator(listIterator方法返回),对于存取的方法，改基类都覆盖了原来的
 * 实现，提供了自己的实现以便实现适合自己的方法操作元素（
 * 主要是三个抽象方法:set(int index, E element),get(int index),remove(int index)）
 * 2.实现的ListIterator必须实现hasNext,next ，hasPrevious,previous 方法
 * 3.如果实现的是一个可以被修改的列表,那么需要实现iterator的set方法如果实现的是一个可变
 * 容量的列表,那么需要实现iterator的add以及remove方法
 * 4.子类需要提供一个空的没有参数的构造函数(Collection 的规定)
 * @see Collection
 * @see List
 * @see AbstractList
 * @see AbstractCollection
 * @since 1.2
 */

public abstract class AbstractSequentialList<E> extends AbstractList<E> {
    /**
     * 唯一的构造器  子类需要调用（Collection的定义）
     */
    protected AbstractSequentialList() {
    }

    /**
     * 获取指定索引的元素,在实现是先获得列表的ListIterator 然后调用next方法获得元素的
     * 为什么这样实现? 因为这个基类是为了给顺序存储的列表实现的,那么查询元素时间复杂度为O(n)
     * 那么底层的ListIterator就是按照遍历去查找的.（如果是随机储存的结构,那么就不需要遍历
     * 只需要定位到指定的元素就可以了）
     */
    public E get(int index) {
        try {
            return listIterator(index).next();
        } catch (NoSuchElementException exc) {
            throw new IndexOutOfBoundsException("Index: "+index);
        }
    }

    /**
     * 替换给定索引的元素(可选的操作)
     * 1. 实现是首先获得列表的ListIterator ，然后遍历到指定索引的元素,然后调用
     * 它的set方法替换元素
     * 2.如果ListIterator没有实现set方法,那么将会抛出UOE
     */
    public E set(int index, E element) {
        try {
            ListIterator<E> e = listIterator(index);
            E oldVal = e.next();
            e.set(element);
            return oldVal;
        } catch (NoSuchElementException exc) {
            throw new IndexOutOfBoundsException("Index: "+index);
        }
    }

    /**
     * 1.将元素添加到指定索引的位置,原先的元素将会在新元素的右边
     * 2.对于抛出的异常可以查看基类中的定义（AbstractList）
     */
    public void add(int index, E element) {
        try {
            listIterator(index).add(element);
        } catch (NoSuchElementException exc) {
            throw new IndexOutOfBoundsException("Index: "+index);
        }
    }

    /**
     * 1.将指定索引的元素进行删除,将右边的参数都往左边移动
     * 2.实现的方式是首先通过ListIterator(index)获得ListIterator，然后在进行删除操作
     */
    public E remove(int index) {
        try {
            ListIterator<E> e = listIterator(index);
            E outCast = e.next();
            e.remove();
            return outCast;
        } catch (NoSuchElementException exc) {
            throw new IndexOutOfBoundsException("Index: "+index);
        }
    }


    // Bulk Operations

    /** 
     * 1.将给定的集合都插入到列表中,指定的索引及索引右边的元素都向右移动,被插入的元素
     * 的顺序是集合的Iterator的顺序
     * 2.方法的实现和前面的方式都是一样的,先获取ListIterator,然后通过它的add方法添加
     * 元素
     * 3.如果ListIterator没有实现add方法的话,那么将会抛出UOE
     */
    public boolean addAll(int index, Collection<? extends E> c) {
        try {
            boolean modified = false;
            ListIterator<E> e1 = listIterator(index);
            Iterator<? extends E> e2 = c.iterator();
            while (e2.hasNext()) {
                e1.add(e2.next());
                modified = true;
            }
            return modified;
        } catch (NoSuchElementException exc) {
            throw new IndexOutOfBoundsException("Index: "+index);
        }
    }


    // Iterators

    /**
     * 事实上最后调用的将是listIterator(index)
     * 这里并没有继承AbstractList,这是因为AbstractList是适合于随机储存结构的列表,
     * 但是不支持顺序储存的列表
     */
    public Iterator<E> iterator() {
        return listIterator();
    }

    /**
     * 1.重新的覆盖基类的方法，因为在基类这个方法都是基于随机存储的,不太适合顺序存储
     * 2.iterator() 方法最终也会调用这个方法获得ListIterator
     */
    public abstract ListIterator<E> listIterator(int index);
}

```