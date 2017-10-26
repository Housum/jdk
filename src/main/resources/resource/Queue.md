### Queue �ӿ�

```
 *  ��һ�����У�����FIFO���Ƚ��ȳ���
 * @see java.util.Collection
 * @see LinkedList
 * @see PriorityQueue
 * @see java.util.concurrent.LinkedBlockingQueue
 * @see java.util.concurrent.BlockingQueue
 * @see java.util.concurrent.ArrayBlockingQueue
 * @see java.util.concurrent.LinkedBlockingQueue
 * @see java.util.concurrent.PriorityBlockingQueue
 */
public interface Queue<E> extends Collection<E> {
    /**
     * ����пռ�Ļ�����ô���Ԫ�ص������У����ҷ���true.
     * 1.û�пռ�Ļ���ô�׳��쳣IllegalStateException, 
     * 2.����������͹�أ���ô�׳�IllegalArgumentException
     * 3.���Ϊnull�Ļ�����ô�׳�NPE
     * 4.����������Ͳ�ƥ�䣬��ô�׳�ClassCastException
     */
    boolean add(E e);

    /**
     * 1.���洫��Ĳ������������ɹ�����ô����true�����򷵻�false�������һ�����޿ռ�Ļ�����ô
     * Ҳ�����׳��쳣��
     * 2.�׳����쳣���Բο�add
     * 
     */
    boolean offer(E e);

    /**
     * ɾ����ǰ���Ԫ�أ����Ԫ�ز����ڣ���ô�׳�NoSuchElementException
     * 
     */
    E remove();

    /**
     * ������ǰ���Ԫ�أ���������ڵĻ�����ô����null
     */
    E poll();

    /**
     * �鿴��ǰ���Ԫ�أ����ǲ����Ƴ��������������ô�׳�NoSuchElementException
     */
    E element();

    /**
     * �鿴��ǰ���Ԫ�أ����ǲ��Ƴ��������������ô����null
     */
    E peek();
}

```
