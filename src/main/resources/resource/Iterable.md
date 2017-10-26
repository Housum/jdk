###Iterable �ӿ�
```

public interface Iterable<T> {
    /**
     * ����һ��������
     * @return an Iterator.
     */
    Iterator<T> iterator();

    /** default ����������ÿһ��Ԫ��,
     * @since 1.8
     */
    default void forEach(Consumer<? super T> action) {
        Objects.requireNonNull(action);
        for (T t : this) {
            action.accept(t);
        }
    }

    /**
     * @return a {@code Spliterator} over the elements described by this
     * {@code Iterable}.
     * ����һ��Spliterator���󲿷ֵ�ʵ���඼��Ҫ�����ǣ�Ĭ�ϵ�ʱ����һ�����ڰ󶨣������Ǻ��ڰ󶨵�
     * ,���Ҽ̳��� Iterator�Ŀ���ʧ�ܵ����ԣ�Ĭ��ʵ�ֵ�Spliterator���к��ٵĹ��ܣ�û���κε�characteristics
     *
     * @since 1.8
     */
    default Spliterator<T> spliterator() {
        return Spliterators.spliteratorUnknownSize(iterator(), 0);
    }
}

```