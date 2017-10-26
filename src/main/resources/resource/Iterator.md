###Iterator 接口
```
/*
 *
 * 在JAVA的Collection用来代替Enumeration的,
 * 和Enumeration主要的一点不同就是在于Iterator提供了remove即删除方法
 * @see Collection
 * @see ListIterator
 * @see Iterable
 * @since 1.2
  
 *
 */
public interface Iterator<E> {
    /**
     *
     * 如果下一个元素存在，那么返回true ,否则返回false
     * @return {@code true} if the iteration has more elements
     */
    boolean hasNext();

    /**
     * 返回下一个元素，但是如果没有元素的话,那么抛出NoSuchElementException
     */
    E next();

    /**
     * 删除next()的那个元素.
     * 1.关于Iterator的这个方法的行为并没有详细的规范（在迭代过程中调用了这个方法)
     *
     * 2.如果不支持这个操作的话,那么将抛出UnsupportedOperationException 
     * 如果对于同一个元素调用多次remove方法的话，那么将抛出IllegalStateException
     *
     */
    default void remove() {
        throw new UnsupportedOperationException("remove");
    }

    /**
     * 对每一个操作都执行定义的操作
     * @since 1.8
     */
    default void forEachRemaining(Consumer<? super E> action) {
        Objects.requireNonNull(action);
        while (hasNext())
            action.accept(next());
    }
}

```