###Iterable 接口
```

public interface Iterable<T> {
    /**
     * 返回一个迭代子
     * @return an Iterator.
     */
    Iterator<T> iterator();

    /** default 方法，遍历每一个元素,
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
     * 创建一个Spliterator，大部分的实现类都需要被覆盖，默认的时候是一个早期绑定，而不是后期绑定的
     * ,而且继承了 Iterator的快速失败的特性，默认实现的Spliterator具有很少的功能，没有任何的characteristics
     *
     * @since 1.8
     */
    default Spliterator<T> spliterator() {
        return Spliterators.spliteratorUnknownSize(iterator(), 0);
    }
}

```