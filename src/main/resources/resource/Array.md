### Array 类  
这是一个工具类，从构造函数private看出来
这个类主要的作用就是提供了一些静态方法来创建
数组以及对数组的进行一些操作。因为这些方法大部分
都是native 相对于比较的高效

 ```
 public final class Array {
 
     /**
      * Constructor.  Class Array is not instantiable.
      */
     private Array() {}
 
     /**
      * 创建一个数组，但是返回的一个Object,需要调用方强转 
      * componentType 数组元素的类型 
      * length: 数组的长度
      */
     public static Object newInstance(Class<?> componentType, int length)
         throws NegativeArraySizeException {
         return newArray(componentType, length);
     }
 
     /**
      * componentType 如果是一个数组类型的话，那么dimensions的总和就是几维数组(
      * 例如：compentType 为String[][] dimensions 总和为1 那么创建的就是String[][][]
      * 但是dimensions不能超过255
      ；如果componentType不是数组类型的话,那么创建的就是一维数组,dimensions 为元素的个数
      * 同理不能超过255)
      * 
      *
      */
     public static Object newInstance(Class<?> componentType, int... dimensions)
         throws IllegalArgumentException, NegativeArraySizeException {
         return multiNewArray(componentType, dimensions);
     }
     
     /**
      * 获取数组的长度 
      */
     public static native int getLength(Object array)
         throws IllegalArgumentException;
         
      /**
       * 获取数组的index元素
       */
     public static native Object get(Object array, int index)
         throws IllegalArgumentException, ArrayIndexOutOfBoundsException;
 
     /**
      * 对于Java的基本类型的包装类提供了方便的获取方法
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
         
      //设置值
     public static native void set(Object array, int index, Object value)
         throws IllegalArgumentException, ArrayIndexOutOfBoundsException;
 
    //对于JAVA的基础类提供的包装类
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
 