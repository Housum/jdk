#### Objects ������

```

package java.util;

import java.util.function.Supplier;

/**
 *  Objects������ ���ṩ�ľ�̬�����Ƚ������������ɶ����hashCode,
 *  �ж϶���ǿջ��߱���Ϊ�� 
 * @since 1.7
 */
public final class Objects {
    private Objects() {
        throw new AssertionError("No java.util.Objects instances for you!");
    }

    /**
     * �Ƚ���������
     * 
     */
    public static boolean equals(Object a, Object b) {
        return (a == b) || (a != null && a.equals(b));
    }

   /**
    * �����: ���������Ļ�  ��ô����Arrays.deepEquals�ж�Ԫ���Ƿ�ȫ�����
    * �������������ô��Ϊnull��ʱ�� ��ȣ�������ö����equal�ж�
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
     * �ж϶����hashCode
     */
    public static int hashCode(Object o) {
        return o != null ? o.hashCode() : 0;
    }

   /**
    * �Զ��ֵ����hashcode�ļ���
    * 
    */
    public static int hash(Object... values) {
        return Arrays.hashCode(values);
    }

    /**
     *
     *���ɶ����String��ʾ �����null�Ļ� ��ô�����ַ�����null��
     */
    public static String toString(Object o) {
        return String.valueOf(o);
    }

    /**
     *
     * ���Ϊ�� ����Ĭ�ϵ�ֵ
     */
    public static String toString(Object o, String nullDefault) {
        return (o != null) ? o.toString() : nullDefault;
    }

    /**
     * ʹ��Comparator�Ƚ�������
     */
    public static <T> int compare(T a, T b, Comparator<? super T> c) {
        return (a == b) ? 0 :  c.compare(a, b);
    }

    /**
     * �ж϶�����Ϊ�գ������׳�NPE
     * 
     */
    public static <T> T requireNonNull(T obj) {
        if (obj == null)
            throw new NullPointerException();
        return obj;
    }

    /**
     * ������Ϊ�գ����򷵻�NPE�����Ҵ����Զ������Ϣ
     *
     */
    public static <T> T requireNonNull(T obj, String message) {
        if (obj == null)
            throw new NullPointerException(message);
        return obj;
    }

    /**
     *
     *  �����Ƿ�Ϊ��
     * @since 1.8
     */
    public static boolean isNull(Object obj) {
        return obj == null;
    }

    /**
     * ����Ƿ�Ϊ��
     * @see java.util.function.Predicate
     * @since 1.8
     */
    public static boolean nonNull(Object obj) {
        return obj != null;
    }

    /**
     * �������Щ��Ҫ�Զ������Ϣ�����Դ���Supplier��1.8ֱ��ʹ�ú���ʽ���ʽ����
     * @since 1.8
     */
    public static <T> T requireNonNull(T obj, Supplier<String> messageSupplier) {
        if (obj == null)
            throw new NullPointerException(messageSupplier.get());
        return obj;
    }
}

```