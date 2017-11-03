### Queue 接口

HEAD-E-E-E-E-E-E-E-TAIL <br/>
添加元素从tail 添加 取出元素从 head取出<br/>
##### 定义
1. 这是一个队列的数据结构,重新的定义了一些方法：比如add操作可能会因为没有空间而抛出异常
比如：remove操作会因为没有元素而抛出异常
2. Queue主要有两组操作：一组是悲观的（不满足抛出异常）一组是乐观的（不满足只是返回false)
3. 添加元素: add = 悲观  offer = 乐观
4. 删除元素: remove = 悲观 poll = 乐观
5. 查看元素: element = 悲观 peek = 乐观

```
 *  是一个队列，遵守FIFO（先进先出）
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
     * 如果有空间的话，那么添加元素到队列中，并且返回true.
     * 1.没有空间的话那么抛出异常IllegalStateException, 
     * 2.如果参数不和规矩，那么抛出IllegalArgumentException
     * 3.如果为null的话，那么抛出NPE
     * 4.如果参数类型不匹配，那么抛出ClassCastException
     */
    boolean add(E e);

    /**
     * 1.储存传入的参数，如果储存成功，那么返回true，否则返回false，如果是一个有限空间的话，那么
     * 也可以抛出异常。
     * 2.抛出的异常可以参考add
     * 
     */
    boolean offer(E e);

    /**
     * 删除最前面的元素，如果元素不存在，那么抛出NoSuchElementException
     * 
     */
    E remove();

    /**
     * 弹出最前面的元素，如果不存在的话，那么返回null
     */
    E poll();

    /**
     * 查看最前面的元素，但是并不移除，如果不存在那么抛出NoSuchElementException
     */
    E element();

    /**
     * 查看最前面的元素，但是不移除，如果不存在那么返回null
     */
    E peek();
}

```
