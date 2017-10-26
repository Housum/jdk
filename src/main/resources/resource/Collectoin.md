###Collection �ķ���
```
/*
 * @since 1.2
 */

public interface Collection<E> extends Iterable<E> {
    // Query Operations ��ѯ����
 
    /**
     * ���ؼ����е�Ԫ�ظ����������������Integer.MAX_VALUE �Ļ�����ô����Integer.MAX_VALUE
     */
    int size();

    /**
     * ���ؼ����Ƿ�Ϊ�� 
     */
    boolean isEmpty();

    /**
     * ���ؼ����Ƿ����o���Ԫ�أ�
     * �����������Ͳ���ƥ�䣬��ô�׳�ClassCastException����ѡ��
     * ��������������Ϊ��,��ô�׳�NullPointerException����ѡ��
     */
    boolean contains(Object o);

    /**
     * ����һ�������ӣ�����������ӵĶ��岢û��ʮ��ǿ�ҵĹ淶
     */
    Iterator<E> iterator();

    /**
     * ����һ���������Ԫ�ص����飬���������е�Ԫ�ز����Ǽ����е����á�
     */
    Object[] toArray();

    /**
     * ��toArray�������ƣ����Ƿ����˾�������ͣ����Ҵ���һ�������������������������Ҫע��һ�£�
     * 1.�������Ϊnull�Ļ�����ô���׳�NPE
     * 2.����������������Ͳ���ƥ���ϼ����е����͵Ļ�����ô�����׳�ArrayStoreException
     * 3.����һ����������ϵ�-�����������鳤�Ȳ��ܴ�ż����е�Ԫ�صĻ�����ô���ᴴ��һ���µ����飬����
     * ������ȵĴ��ڻ��ߵ��ڼ��ϵ�Ԫ�صĻ�����ô��ʹ�ô�������飬������ǲ��ֽ��ᱻnull���
     */
    <T> T[] toArray(T[] a);

    // Modification Operations �޸ķ���

    /**
     *  ���Ԫ�ص�������ȥ�������ӳɹ��Ļ�����ô����true�����򷵻�false
     *  ����ʵ�ֵĲ�ͬ��ѡ���Ե�
     *  1. �����֧��add�������׳�UnsupportedOperationException
     *  2.�����������Ͳ���ƥ��,��ô�׳�ClassCastException
     *  3.������ϲ��������յ�Ԫ�أ����Ҳ�����nullֵ����ô�׳�NullPointerException
     *  4.�����Ϊ����Ԫ�ص�ĳЩ���Ե��²��ܱ����룬��ô�׳�IllegalArgumentException
     *  5.�����Ϊ����ʱԼ�����ܱ����룬��ô�׳�IllegalStateException
     */
    boolean add(E e);

    /**
     *
     * �Ӽ�����ɾ��һ��Ԫ��,������equal����
     *��addһ�������׳�ClassCastException��NullPointerException��UnsupportedOperationException �쳣
     */
    boolean remove(Object o);


    // Bulk Operations  ����󲿷ֵ�Ԫ��

    /**
     */
    boolean containsAll(Collection<?> c);

    /**
     */
    boolean addAll(Collection<? extends E> c);

    /**
     */
    boolean removeAll(Collection<?> c);

    /**
     * @since 1.8
     */
    default boolean removeIf(Predicate<? super E> filter) {
        Objects.requireNonNull(filter);
        boolean removed = false;
        final Iterator<E> each = iterator();
        while (each.hasNext()) {
            if (filter.test(each.next())) {
                each.remove();
                removed = true;
            }
        }
        return removed;
    }

    /**
     * �󽻼�
     */
    boolean retainAll(Collection<?> c);

    /**
     * ������е�Ԫ��
     */
    void clear();


    // Comparison and hashing

    /**
     * �������������Ƕ����
     */
    boolean equals(Object o);

    /**
     * ���ؼ��ϵ�HashCode
     */
    int hashCode();

    /**
     *
     * @return a {@code Spliterator} over the elements in this collection
     * 1.����һ��Spliterator,һ������£�ʵ���඼��Ҫ�Լ�ȥʵ�֡�
     * �����Ҫ���������Spliterator��characteristicֻ��SIZED�Ļ������Ҽ�������û��Ԫ�صĻ�
     * ��ô�ǲ���Ҫ�ṩSpliterator
     * 2.Ϊ����ʹstream()�Լ�parallelStream()�ܹ�����һ���ӳټ��ص�Stream,Spliterator��characteristic
     * ��Ҫʹ��IMMUTABLE����CONCURENT,����Spliterator��һ���ӳٰ󶨵�Ҳ�ǿ��Եģ����ǰ���������������Ļ���
     * ��ôʵ����������ĵ���˵��������İ󶨲����Լ��ṹ��Ӱ�죬����ͬʱ����stream()�Լ�parallelStream������
     * 3.ͬʱĬ�ϴ�������һ������ʧ�ܣ�����characteristicΪSIZED��SUBSIZED��Spliterator
     * @since 1.8
     */
    @Override
    default Spliterator<E> spliterator() {
        return Spliterators.spliterator(this, 0);
    }

    /**
     * ����һ��˳������� 
     */
    default Stream<E> stream() {
        return StreamSupport.stream(spliterator(), false);
    }

    /**
     * ���ز��е�Stream
     * @since 1.8
     */
    default Stream<E> parallelStream() {
        return StreamSupport.stream(spliterator(), true);
    }
}

```