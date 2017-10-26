#### Objects 工具类

```

package java.util;

import java.util.function.Supplier;

/**
 *  Objects工具类 ，提供的静态方法比较两个对象，生成对象的hashCode,
 *  判断对象非空或者必须为空 
 * @since 1.7
 */
public final class Objects {
    private Objects() {
        throw new AssertionError("No java.util.Objects instances for you!");
    }

    /**
     * 比较两个对象
     * 
     */
    public static boolean equals(Object a, Object b) {
        return (a == b) || (a != null && a.equals(b));
    }

   /**
    * 深相等: 如果是数组的话  那么调用Arrays.deepEquals判断元素是否全部相等
    * 如果不是数组那么都为null的时候 相等，否则调用对象的equal判断
    *
    */
    public static boolean deepEquals(Object a, Object b) {
        if (a == b)
            return true;
        else if (a == null || b == null)
            return false;
        else
            return Arrays.deepEquals0(a, b);
    }

    /**
     * 判断对象的hashCode
     */
    public static int hashCode(Object o) {
        return o != null ? o.hashCode() : 0;
    }

   /**
    * 对多个值进行hashcode的计算
    * 
    */
    public static int hash(Object... values) {
        return Arrays.hashCode(values);
    }

    /**
     *
     *生成对象的String表示 如果是null的话 那么返回字符串‘null’
     */
    public static String toString(Object o) {
        return String.valueOf(o);
    }

    /**
     *
     * 如果为空 返回默认的值
     */
    public static String toString(Object o, String nullDefault) {
        return (o != null) ? o.toString() : nullDefault;
    }

    /**
     * 使用Comparator比较两个数
     */
    public static <T> int compare(T a, T b, Comparator<? super T> c) {
        return (a == b) ? 0 :  c.compare(a, b);
    }

    /**
     * 判断对象不能为空，否则抛出NPE
     * 
     */
    public static <T> T requireNonNull(T obj) {
        if (obj == null)
            throw new NullPointerException();
        return obj;
    }

    /**
     * 对象不能为空，否则返回NPE，并且带上自定义的信息
     *
     */
    public static <T> T requireNonNull(T obj, String message) {
        if (obj == null)
            throw new NullPointerException(message);
        return obj;
    }

    /**
     *
     *  对象是否为空
     * @since 1.8
     */
    public static boolean isNull(Object obj) {
        return obj == null;
    }

    /**
     * 如果是否不为空
     * @see java.util.function.Predicate
     * @since 1.8
     */
    public static boolean nonNull(Object obj) {
        return obj != null;
    }

    /**
     * 针对于那些需要自定义的消息，可以传入Supplier，1.8直接使用函数式表达式即可
     * @since 1.8
     */
    public static <T> T requireNonNull(T obj, Supplier<String> messageSupplier) {
        if (obj == null)
            throw new NullPointerException(messageSupplier.get());
        return obj;
    }
}

```