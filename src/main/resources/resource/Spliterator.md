###Spliterator用法
```
package java.util;

import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;

/**
 *  1.是Iterator的升级版本  能够对集合，数组，IO channel ，进行访问以及分割，
    2.通过tryAdvance(..)对单个的元素进行访问，forEachRemaining(..)对全部的元素进行
    访问
    3.通过可以通过trySplit()进行子分割，可以在并行的环境下操作多个的Spliterator,但是在单个的Spliterator下并
    不会有性能的提升
    4.characteristics()将会返回的该Spliterator的属性是什么,类中定义的这些常量以及含义
    5.这里有一个前期绑定和后期绑定的概念，前期绑定指的是一旦创建了Spliterator的话，那么就直接关联上了
    引用的元素，但是后期绑定则不是，后期绑定讲的是当第一访问元素,或者估算其个数的时候才会关联上元素
    6.和Iterator一样，支持快速失败
 */
public interface Spliterator<T> {
    
    /**
     * 如果接下来存在元素的话  那么对他进行给定的操作（单个的操作）并且返回true，
     * 否则返回false。如果定义的action不存在的话 那么将抛出NPE
     */
    boolean tryAdvance(Consumer<? super T> action);
    
    default void forEachRemaining(Consumer<? super T> action) {
        do { } while (tryAdvance(action));
    }
    
    /**
     * 返回一个子Spliterator(例如： ArrayList在元素处理还没有一般的时候，将会将元素分成两份)
     */
    Spliterator<T> trySplit();
    
    /**
     * 在调用的forEachRemaining的时候， 如果这是一个SIZED的Spliterator话将会有多少个元素会被遍历，
     * 如果出现这几种情况下：元素是无限的那么返回Long.MAX_VALUE,不清楚的，或者计算这个值太耗费CPU资源
     * 有可能这个值并不一定是完全准确的
     */
    long estimateSize();
    
    
    /**
     * 如果Spliterator 的属性不包含SIZED
     */
    default long getExactSizeIfKnown() {
        return (characteristics() & SIZED) == 0 ? -1L : estimateSize();
    }
    
    /**
     * 返回Spliterator的属性 
     */
    int characteristics();
    
    default boolean hasCharacteristics(int characteristics) {
        return (characteristics() & characteristics) == characteristics;
    }
    
    /**
     * 如果继承了Comparator那么返回  否则返回null
     */
    default Comparator<? super T> getComparator() {
        throw new IllegalStateException();
    }

   
    /**
    * 表示该Spliterator将会按照一定的序号记录执行的元素的，
    * 在tryAdvence（..）和forEachRemaining(..)都会按照这个序号
    * 进行访问，比如：Collectoin.iterator 返回的是有序的,List 等。
    * 但是通过Hash表实现的 那么将不会有序号 比如：HashSet
    * 
    */
    public static final int ORDERED    = 0x00000010;
    /**
     * 表示是不能重复的  比如：Set
     */
    public static final int DISTINCT   = 0x00000001;
    
    /**
    * 表示是有序的  比如SortSet
    */
    public static final int SORTED     = 0x00000004;

    /**
     *
     * 通过estimateSize()将会返回一个有限的容量
     */
    public static final int SIZED      = 0x00000040;

    /**
     * 访问的元素不会为空 ，比如：PriorityQuque
     *  
     */
    public static final int NONNULL    = 0x00000100;

    /**
     * 不可变，将会抛出ConcurrentModificationException
     */
    public static final int IMMUTABLE  = 0x00000400;

    /**
     * 并发的Spliterator
     */
    public static final int CONCURRENT = 0x00001000;

    /**
     * 有限个元素
     */
    public static final int SUBSIZED = 0x00004000;

    /**
    * 对于包装类来说，提供了接口进行操作
    * @param T 包装类型
    * @param T_CONS 给定的操作
    * @param T_SPLITR Spliterator 
    */
    public interface OfPrimitive<T, T_CONS, T_SPLITR extends Spliterator.OfPrimitive<T, T_CONS, T_SPLITR>>
            extends Spliterator<T> {
        @Override
        T_SPLITR trySplit();
        
        @SuppressWarnings("overloads")
        boolean tryAdvance(T_CONS action);

        @SuppressWarnings("overloads")
        default void forEachRemaining(T_CONS action) {
            do { } while (tryAdvance(action));
        }
    }

    /**
     * int 的包装类
     * @since 1.8
     */
    public interface OfInt extends OfPrimitive<Integer, IntConsumer, OfInt> {

        @Override
        OfInt trySplit();

        @Override
        boolean tryAdvance(IntConsumer action);

        @Override
        default void forEachRemaining(IntConsumer action) {
            do { } while (tryAdvance(action));
        }

        @Override
        default boolean tryAdvance(Consumer<? super Integer> action) {
            if (action instanceof IntConsumer) {
                return tryAdvance((IntConsumer) action);
            }
            else {
                if (Tripwire.ENABLED)
                    Tripwire.trip(getClass(),
                                  "{0} calling Spliterator.OfInt.tryAdvance((IntConsumer) action::accept)");
                return tryAdvance((IntConsumer) action::accept);
            }
        }

        @Override
        default void forEachRemaining(Consumer<? super Integer> action) {
            if (action instanceof IntConsumer) {
                forEachRemaining((IntConsumer) action);
            }
            else {
                if (Tripwire.ENABLED)
                    Tripwire.trip(getClass(),
                                  "{0} calling Spliterator.OfInt.forEachRemaining((IntConsumer) action::accept)");
                forEachRemaining((IntConsumer) action::accept);
            }
        }
    }

    /**
     * long 的包装类
     * @since 1.8
     */
    public interface OfLong extends OfPrimitive<Long, LongConsumer, OfLong> {

        @Override
        OfLong trySplit();

        @Override
        boolean tryAdvance(LongConsumer action);

        @Override
        default void forEachRemaining(LongConsumer action) {
            do { } while (tryAdvance(action));
        }

        @Override
        default boolean tryAdvance(Consumer<? super Long> action) {
            if (action instanceof LongConsumer) {
                return tryAdvance((LongConsumer) action);
            }
            else {
                if (Tripwire.ENABLED)
                    Tripwire.trip(getClass(),
                                  "{0} calling Spliterator.OfLong.tryAdvance((LongConsumer) action::accept)");
                return tryAdvance((LongConsumer) action::accept);
            }
        }

        @Override
        default void forEachRemaining(Consumer<? super Long> action) {
            if (action instanceof LongConsumer) {
                forEachRemaining((LongConsumer) action);
            }
            else {
                if (Tripwire.ENABLED)
                    Tripwire.trip(getClass(),
                                  "{0} calling Spliterator.OfLong.forEachRemaining((LongConsumer) action::accept)");
                forEachRemaining((LongConsumer) action::accept);
            }
        }
    }

    /**
     * double 的包装类
     * @since 1.8
     */
    public interface OfDouble extends OfPrimitive<Double, DoubleConsumer, OfDouble> {

        @Override
        OfDouble trySplit();

        @Override
        boolean tryAdvance(DoubleConsumer action);

        @Override
        default void forEachRemaining(DoubleConsumer action) {
            do { } while (tryAdvance(action));
        }
        @Override
        default boolean tryAdvance(Consumer<? super Double> action) {
            if (action instanceof DoubleConsumer) {
                return tryAdvance((DoubleConsumer) action);
            }
            else {
                if (Tripwire.ENABLED)
                    Tripwire.trip(getClass(),
                                  "{0} calling Spliterator.OfDouble.tryAdvance((DoubleConsumer) action::accept)");
                return tryAdvance((DoubleConsumer) action::accept);
            }
        }

        @Override
        default void forEachRemaining(Consumer<? super Double> action) {
            if (action instanceof DoubleConsumer) {
                forEachRemaining((DoubleConsumer) action);
            }
            else {
                if (Tripwire.ENABLED)
                    Tripwire.trip(getClass(),
                                  "{0} calling Spliterator.OfDouble.forEachRemaining((DoubleConsumer) action::accept)");
                forEachRemaining((DoubleConsumer) action::accept);
            }
        }
    }
}

```
java 8为了并行的操作集合推出来的一个Iterator,而且不仅仅能够迭代集合，而且能够将整个的集合进行
分割，分割之后的Spliterator可以进行并行的操作

对于实现了Iterable的接口都提供了获取Spliterator的操作