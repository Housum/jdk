### 参数化类型 比如 Map<K,V> 就是参数化类型
从这个类中可以获取参数化类型所有的信息 比如：类型参数对应的真实参数，原始类型，所属类的类型
```
/**
 *
 * @since 1.5
 */
public interface ParameterizedType extends Type {
    /**
     * 返回类型参数中真实的类型 比如： List<String> 返回 String  注意和TypeValiable的区别  它返回的是类型参数
     */
    Type[] getActualTypeArguments();

    /**
     * 返回去掉类型参数的类型 比如O<T> 返回O @see JDKTest
     */
    Type getRawType();

    /**
     * 返回该类型所属的哪个类型 如果类型是O<T>.I<S> 那么返回O<T> @see JDKTest
     */
    Type getOwnerType();
}

```


