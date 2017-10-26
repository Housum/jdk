###Spliterator�÷�
```
package java.util;

import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;

/**
 *  1.��Iterator�������汾  �ܹ��Լ��ϣ����飬IO channel �����з����Լ��ָ
    2.ͨ��tryAdvance(..)�Ե�����Ԫ�ؽ��з��ʣ�forEachRemaining(..)��ȫ����Ԫ�ؽ���
    ����
    3.ͨ������ͨ��trySplit()�����ӷָ�����ڲ��еĻ����²��������Spliterator,�����ڵ�����Spliterator�²�
    ���������ܵ�����
    4.characteristics()���᷵�صĸ�Spliterator��������ʲô,���ж������Щ�����Լ�����
    5.������һ��ǰ�ڰ󶨺ͺ��ڰ󶨵ĸ��ǰ�ڰ�ָ����һ��������Spliterator�Ļ�����ô��ֱ�ӹ�������
    ���õ�Ԫ�أ����Ǻ��ڰ����ǣ����ڰ󶨽����ǵ���һ����Ԫ��,���߹����������ʱ��Ż������Ԫ��
    6.��Iteratorһ����֧�ֿ���ʧ��
 */
public interface Spliterator<T> {
    
    /**
     * �������������Ԫ�صĻ�  ��ô�������и����Ĳ����������Ĳ��������ҷ���true��
     * ���򷵻�false����������action�����ڵĻ� ��ô���׳�NPE
     */
    boolean tryAdvance(Consumer<? super T> action);
    
    default void forEachRemaining(Consumer<? super T> action) {
        do { } while (tryAdvance(action));
    }
    
    /**
     * ����һ����Spliterator(���磺 ArrayList��Ԫ�ش���û��һ���ʱ�򣬽��ὫԪ�طֳ�����)
     */
    Spliterator<T> trySplit();
    
    /**
     * �ڵ��õ�forEachRemaining��ʱ�� �������һ��SIZED��Spliterator�������ж��ٸ�Ԫ�ػᱻ������
     * ��������⼸������£�Ԫ�������޵���ô����Long.MAX_VALUE,������ģ����߼������ֵ̫�ķ�CPU��Դ
     * �п������ֵ����һ������ȫ׼ȷ��
     */
    long estimateSize();
    
    
    /**
     * ���Spliterator �����Բ�����SIZED
     */
    default long getExactSizeIfKnown() {
        return (characteristics() & SIZED) == 0 ? -1L : estimateSize();
    }
    
    /**
     * ����Spliterator������ 
     */
    int characteristics();
    
    default boolean hasCharacteristics(int characteristics) {
        return (characteristics() & characteristics) == characteristics;
    }
    
    /**
     * ����̳���Comparator��ô����  ���򷵻�null
     */
    default Comparator<? super T> getComparator() {
        throw new IllegalStateException();
    }

   
    /**
    * ��ʾ��Spliterator���ᰴ��һ������ż�¼ִ�е�Ԫ�صģ�
    * ��tryAdvence��..����forEachRemaining(..)���ᰴ��������
    * ���з��ʣ����磺Collectoin.iterator ���ص��������,List �ȡ�
    * ����ͨ��Hash��ʵ�ֵ� ��ô����������� ���磺HashSet
    * 
    */
    public static final int ORDERED    = 0x00000010;
    /**
     * ��ʾ�ǲ����ظ���  ���磺Set
     */
    public static final int DISTINCT   = 0x00000001;
    
    /**
    * ��ʾ�������  ����SortSet
    */
    public static final int SORTED     = 0x00000004;

    /**
     *
     * ͨ��estimateSize()���᷵��һ�����޵�����
     */
    public static final int SIZED      = 0x00000040;

    /**
     * ���ʵ�Ԫ�ز���Ϊ�� �����磺PriorityQuque
     *  
     */
    public static final int NONNULL    = 0x00000100;

    /**
     * ���ɱ䣬�����׳�ConcurrentModificationException
     */
    public static final int IMMUTABLE  = 0x00000400;

    /**
     * ������Spliterator
     */
    public static final int CONCURRENT = 0x00001000;

    /**
     * ���޸�Ԫ��
     */
    public static final int SUBSIZED = 0x00004000;

    /**
    * ���ڰ�װ����˵���ṩ�˽ӿڽ��в���
    * @param T ��װ����
    * @param T_CONS �����Ĳ���
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
     * int �İ�װ��
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
     * long �İ�װ��
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
     * double �İ�װ��
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
java 8Ϊ�˲��еĲ��������Ƴ�����һ��Iterator,���Ҳ������ܹ��������ϣ������ܹ��������ļ��Ͻ���
�ָ�ָ�֮���Spliterator���Խ��в��еĲ���

����ʵ����Iterable�Ľӿڶ��ṩ�˻�ȡSpliterator�Ĳ���