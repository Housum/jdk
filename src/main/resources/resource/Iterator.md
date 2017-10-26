###Iterator �ӿ�
```
/*
 *
 * ��JAVA��Collection��������Enumeration��,
 * ��Enumeration��Ҫ��һ�㲻ͬ��������Iterator�ṩ��remove��ɾ������
 * @see Collection
 * @see ListIterator
 * @see Iterable
 * @since 1.2
  
 *
 */
public interface Iterator<E> {
    /**
     *
     * �����һ��Ԫ�ش��ڣ���ô����true ,���򷵻�false
     * @return {@code true} if the iteration has more elements
     */
    boolean hasNext();

    /**
     * ������һ��Ԫ�أ��������û��Ԫ�صĻ�,��ô�׳�NoSuchElementException
     */
    E next();

    /**
     * ɾ��next()���Ǹ�Ԫ��.
     * 1.����Iterator�������������Ϊ��û����ϸ�Ĺ淶���ڵ��������е������������)
     *
     * 2.�����֧����������Ļ�,��ô���׳�UnsupportedOperationException 
     * �������ͬһ��Ԫ�ص��ö��remove�����Ļ�����ô���׳�IllegalStateException
     *
     */
    default void remove() {
        throw new UnsupportedOperationException("remove");
    }

    /**
     * ��ÿһ��������ִ�ж���Ĳ���
     * @since 1.8
     */
    default void forEachRemaining(Consumer<? super E> action) {
        Objects.requireNonNull(action);
        while (hasNext())
            action.accept(next());
    }
}

```