###ListIterator接口 
```

package java.util;

/**
 * 注意： LIstIterator的remove和set(..)方法都是作用于next(..)或者previous(..)返回的那一个元素上
 *
 * @see Collection
 * @see List
 * @see Iterator
 * @see Enumeration
 * @see List#listIterator()
 * @since   1.2
 */
public interface ListIterator<E> extends Iterator<E> {
    // Query Operations

    boolean hasNext();

    E next();

    /**
     */
    boolean hasPrevious();

    /**
     * 返回前一个元素 
     * @throws NoSuchElementException if the iteration has no previous
     *         element
     */
    E previous();

    /**
     * 下一个元素的索引
     */
    int nextIndex();
     /**
      * 前一个元素的索引
      */
    int previousIndex();


    // Modification Operations

    /**
     * 删除next或者previous的那个元素，出现以下几种情况将会抛出IllegalStateException：
     * next或者previous方法没有被调用过；或者在next方法或者previous方法被调用之后，add或者remove方法被调用了
     */
    void remove();

    /**
     * 替换那个被next或者previous替换的元素，IllegalStateException异常出现以下几种情况出现:
     * next或者previous都没有被调用，或者在next或者previous被调用之后，remove或则add方法被调用乐
     */
    void set(E e);

    /**
     * 将添加到next()返回的元素的前面,如果在添加之后，调用previous将会被访问到
     */
    void add(E e);
}

```