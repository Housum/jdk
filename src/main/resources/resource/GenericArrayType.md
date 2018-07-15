### 范型数组的类型表示  数组中出现范型的 比如： T[]

```
public interface GenericArrayType extends Type {

    /**
     * 返回元素的类型表示,比如：T[] 返回 T @see JDKTest
     */
    Type getGenericComponentType();
}


```