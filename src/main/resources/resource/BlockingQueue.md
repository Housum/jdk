### BockingQueue �ӿ�
����ӿ����������������ģ����ʹ��
������Դ��:
```
/*

/**
 * ���һ����չ��Queue,����һ�������Ķ��У�
 * 1.��һЩ������һֱ�������ܹ�ִ��Ϊֹ���磺 put(..),take()��
 * 2.�Լ�һЩ�ܹ�����һ��ʱ��ķ���,����:offer(..),poll(..)
 * 3.BockingQueue ���������һ��nullֵ��ȥ����Ϊnullֵ����Ϊpollû�в������ص�һ�����
 * 4.�����һ�����������Ƶ�BlockingQueue��ʱ��,��ô����ͨ��remainingCapacity �鿴���ж���������put��������Ҫ������ֱ���ܹ����룩
 * 5.BlockingQueueһ������¶���ʹ�����������������У�����ͬʱ��Ϊ���̳���Collection�ӿڣ����Ի��ǿ���ͨ��remove(..)�Ƴ�һЩԪ�ص�
 *�����ǲ�����������������ֻ����ĳЩ����������ʹ�ã��������������ɵ���Ϣ��ȡ���ˣ����ʱ��Ϳ���ʹ��remove(..)��
 * 6.BlockingQueue���̰߳�ȫ�ģ�������������ԭ�ӵģ���������Щ����������addAll(..)��removeAll(..),retainAll(..),����ʵ��Ϊ
 * ��Щ�������ɹ��Ļ���ֱ���׳��쳣��
 */
public interface BlockingQueue<E> extends Queue<E> {
 
 
    /**
     * summary �� ��ÿһ�������������׳�һ�µļ����쳣��
     * 1.ClassCastException ����������Ͳ���ƥ��
     * 2.NullPointerException �������Ϊ��
     * 3.IllegalArgumentException ���������������BlockingQueue�Ĺ淶
     */
     
     
    /**
     * ���Ԫ�ص�������,������ܹ���ӽ�ȥ����ô�׳�IllegalStateException�쳣
     */
    boolean add(E e);

    /**
     * ���Ԫ�ص������У����ʧ����ô����false�������ӳɹ�����ô����true
     */
    boolean offer(E e);

    /**
     * ���Ԫ�ص������У������Ӳ��ˣ���һֱ�������ܹ���ӣ����ǿ��ܱ��ж�
     * �׳�InterruptedException
     */
    void put(E e) throws InterruptedException;

    /**
     * ���Ԫ�ص�������ȥ�����������ӣ������һ��ʱ�䣬����ɹ�����true�����򷵻�false
     * �п��ܱ��жϣ��׳�InterruptedException
     */
    boolean offer(E e, long timeout, TimeUnit unit)
        throws InterruptedException;

    /**
     * ��ö��׵�Ԫ�أ�������ܻ�ã���ô�ͻ�һֱ���������ж�
     */
    E take() throws InterruptedException;

    /**
     * ��ö��׵�Ԫ�أ�������ܻ�ã���ô����������ʱ��
     */
    E poll(long timeout, TimeUnit unit)
        throws InterruptedException;

    /**
     * ���ػ��ܱ������Ԫ��
     */
    int remainingCapacity();

    /**
     * ɾ��ָ����Ԫ�أ����ɾ���ɹ��Ļ�����ô����true�����򷵻�false
     */
    boolean remove(Object o);

    /**
     * �����Ƿ����������Ԫ��
     */
    public boolean contains(Object o);

    /**
     * ��ʣ�µ�Ԫ�ض����뵽c������
     */
    int drainTo(Collection<? super E> c);

    /**
     * ��һ����Ԫ�ط��뵽c�����У�����ܴﵽmaxElement 
     */
    int drainTo(Collection<? super E> c, int maxElements);
}

```