### Array ��  
����һ�������࣬�ӹ��캯��private������
�������Ҫ�����þ����ṩ��һЩ��̬����������
�����Լ�������Ľ���һЩ��������Ϊ��Щ�����󲿷�
����native ����ڱȽϵĸ�Ч

 ```
 public final class Array {
 
     /**
      * Constructor.  Class Array is not instantiable.
      */
     private Array() {}
 
     /**
      * ����һ�����飬���Ƿ��ص�һ��Object,��Ҫ���÷�ǿת 
      * componentType ����Ԫ�ص����� 
      * length: ����ĳ���
      */
     public static Object newInstance(Class<?> componentType, int length)
         throws NegativeArraySizeException {
         return newArray(componentType, length);
     }
 
     /**
      * componentType �����һ���������͵Ļ�����ôdimensions���ܺ;��Ǽ�ά����(
      * ���磺compentType ΪString[][] dimensions �ܺ�Ϊ1 ��ô�����ľ���String[][][]
      * ����dimensions���ܳ���255
      �����componentType�����������͵Ļ�,��ô�����ľ���һά����,dimensions ΪԪ�صĸ���
      * ͬ���ܳ���255)
      * 
      *
      */
     public static Object newInstance(Class<?> componentType, int... dimensions)
         throws IllegalArgumentException, NegativeArraySizeException {
         return multiNewArray(componentType, dimensions);
     }
     
     /**
      * ��ȡ����ĳ��� 
      */
     public static native int getLength(Object array)
         throws IllegalArgumentException;
         
      /**
       * ��ȡ�����indexԪ��
       */
     public static native Object get(Object array, int index)
         throws IllegalArgumentException, ArrayIndexOutOfBoundsException;
 
     /**
      * ����Java�Ļ������͵İ�װ���ṩ�˷���Ļ�ȡ����
      */
     public static native boolean getBoolean(Object array, int index)
         throws IllegalArgumentException, ArrayIndexOutOfBoundsException;
 
     public static native byte getByte(Object array, int index)
         throws IllegalArgumentException, ArrayIndexOutOfBoundsException;
 
     public static native char getChar(Object array, int index)
         throws IllegalArgumentException, ArrayIndexOutOfBoundsException;
 
     public static native short getShort(Object array, int index)
         throws IllegalArgumentException, ArrayIndexOutOfBoundsException;
 
     public static native int getInt(Object array, int index)
         throws IllegalArgumentException, ArrayIndexOutOfBoundsException;
 
     public static native long getLong(Object array, int index)
         throws IllegalArgumentException, ArrayIndexOutOfBoundsException;
 
     public static native float getFloat(Object array, int index)
         throws IllegalArgumentException, ArrayIndexOutOfBoundsException;
 
     public static native double getDouble(Object array, int index)
         throws IllegalArgumentException, ArrayIndexOutOfBoundsException;
         
      //����ֵ
     public static native void set(Object array, int index, Object value)
         throws IllegalArgumentException, ArrayIndexOutOfBoundsException;
 
    //����JAVA�Ļ������ṩ�İ�װ��
     public static native void setBoolean(Object array, int index, boolean z)
         throws IllegalArgumentException, ArrayIndexOutOfBoundsException;
 
     public static native void setByte(Object array, int index, byte b)
         throws IllegalArgumentException, ArrayIndexOutOfBoundsException;
 
     public static native void setChar(Object array, int index, char c)
         throws IllegalArgumentException, ArrayIndexOutOfBoundsException;
 
     public static native void setShort(Object array, int index, short s)
         throws IllegalArgumentException, ArrayIndexOutOfBoundsException;
 
     public static native void setInt(Object array, int index, int i)
         throws IllegalArgumentException, ArrayIndexOutOfBoundsException;
         
     public static native void setLong(Object array, int index, long l)
         throws IllegalArgumentException, ArrayIndexOutOfBoundsException;
 
     /d
     public static native void setFloat(Object array, int index, float f)
         throws IllegalArgumentException, ArrayIndexOutOfBoundsException;
 
     public static native void setDouble(Object array, int index, double d)
         throws IllegalArgumentException, ArrayIndexOutOfBoundsException;
 
     /*
      * Private
      */
 
     private static native Object newArray(Class<?> componentType, int length)
         throws NegativeArraySizeException;
 
     private static native Object multiNewArray(Class<?> componentType,
         int[] dimensions)
         throws IllegalArgumentException, NegativeArraySizeException;
 
 
 }

 ```
 