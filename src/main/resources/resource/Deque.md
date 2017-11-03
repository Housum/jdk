### Deque �ӿ�

```
/**
 * ˫�˶��У�һ�����������һ��û�г������Ƶģ�����ʵ�������ѡ���Ե�
 * ʵ��Ϊ�г������Ƶġ�ͬʱ��Ҳ֧��ջ����ʽ(stack),ֻ��Ҫʹ��
 * addFirst(..),offerFirst(..),removeFirst(),pollFirst(),getFirst(),peekFirst()
 */
public interface Deque<E> extends Queue<E> {
    /**
     *  ����Ԫ�ص����ף�������ܲ��룬���׳�IllegalStateException�쳣
     */
    void addFirst(E e);

    /**
     * ����Ԫ�ص���β��������ܲ��룬���׳�IllegalStateException�쳣
     */
    void addLast(E e);

    /**
     * ����Ԫ�ص�����,������ܲ��룬�򷵻�false
     */
    boolean offerFirst(E e);

    /**
     *����Ԫ�ص���β��������ܲ��룬�򷵻�false
     */
    boolean offerLast(E e);

    /**
     *  ɾ�����׵�Ԫ��,���ҷ��أ� ��������ڵĻ�����ô�׳�NoSuchElementException
     */
    E removeFirst();

    /**
     * ɾ����β��Ԫ�أ����ҷ��� ��������ڵĻ�����ô�׳�NoSuchElementException
     */
    E removeLast();

    /**
     * ɾ�����׵�Ԫ�أ����ҷ��أ���������ڵĻ�,��ô����null
     */
    E pollFirst();

    /**
     * ɾ����β��Ԫ�أ����ҷ��أ���������ڵĻ�����ô����null 
     */
    E pollLast();

    /**
     * ������Queue��element �鿴���׵�Ԫ�أ������������ô�׳�NoSuchElementException
     */
    E getFirst();

    /**
     *������Queue��element �鿴��β��Ԫ�أ������������ô�׳�NoSuchElementException
     */
    E getLast();

    /**
     * �Ӷ��ײ鿴����
     */
    E peekFirst();

    /**
     * �Ӷ�β�鿴����
     */
    E peekLast();

    /**
     * ɾ������������Ԫ�أ���������ڣ���ô���в����κεĸı䣬
     * ɾ���ɹ��Ļ�������true
     */
    boolean removeFirstOccurrence(Object o);

    /**
     * ɾ�����β�����Ԫ��o����������ڣ���ô���в����κεĸı䣬
     *                  ɾ���ɹ��Ļ�������true
     */
    boolean removeLastOccurrence(Object o);

    // *** �̳� Queue �ķ��� ***

    /**
     * �൱��addLast
     *
     */
    boolean add(E e);

    /**
     * �൱��offerLast
     */
    boolean offer(E e);

    /**
     * �൱��removeFirst
     */
    E remove();

    /**
     * �൱��pollFirst
     */
    E poll();

    /**
     * �൱��getFirst
     */
    E element();

    /**
     * �൱��peekFirst
     */
    E peek();


    // *** ջ��ʵ��  ***

    /**
     * �൱��addFirst
     */
    void push(E e);

    /**
     * ��������൱��removeFirst
     * this method is equivalent to {@link #removeFirst} 
     */
    E pop();


    //���涼�Ǽ̳�Collection�ķ���

    /**
     */
    boolean remove(Object o);

    /**
     */
    boolean contains(Object o);

    /**
     * ���ض��еĸ���
     */
    public int size();

    /**
     * ����һ���Ӷ��׵���β��Iterator
     */
    Iterator<E> iterator();

    /**
     * ����һ���Ӷ�β�����׵�Iterator 
     */
    Iterator<E> descendingIterator();

}

```