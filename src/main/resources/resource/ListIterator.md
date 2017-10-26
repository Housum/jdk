###ListIterator�ӿ� 
```

package java.util;

/**
 * ע�⣺ LIstIterator��remove��set(..)��������������next(..)����previous(..)���ص���һ��Ԫ����
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
     * ����ǰһ��Ԫ�� 
     * @throws NoSuchElementException if the iteration has no previous
     *         element
     */
    E previous();

    /**
     * ��һ��Ԫ�ص�����
     */
    int nextIndex();
     /**
      * ǰһ��Ԫ�ص�����
      */
    int previousIndex();


    // Modification Operations

    /**
     * ɾ��next����previous���Ǹ�Ԫ�أ��������¼�����������׳�IllegalStateException��
     * next����previous����û�б����ù���������next��������previous����������֮��add����remove������������
     */
    void remove();

    /**
     * �滻�Ǹ���next����previous�滻��Ԫ�أ�IllegalStateException�쳣�������¼����������:
     * next����previous��û�б����ã�������next����previous������֮��remove����add������������
     */
    void set(E e);

    /**
     * ����ӵ�next()���ص�Ԫ�ص�ǰ��,��������֮�󣬵���previous���ᱻ���ʵ�
     */
    void add(E e);
}

```