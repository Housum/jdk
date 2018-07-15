### 有通配符存在的范型 比如： <? extends Clazz> 

```
public interface WildcardType extends Type {

    /**
     * 获取通配符范型的上限 <? extends Clazz> 的上限就是Clazz
     */
    Type[] getUpperBounds();

    /**
     * 获取通配符范型的下限 <? super Demo> 的下限就是Demo
     */
    Type[] getLowerBounds();
}
```

