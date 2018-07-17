###标示为类型参数(TypeParameter) 在范型中，定义的参数 比如： Map<K,V> 那么 TypeVariable 为K,V两个

```
/**
 * 只有在首次使用的使用才会被创建（lazy init ）
 */
public interface TypeVariable<D extends GenericDeclaration> extends Type, AnnotatedElement {

    //返回类型参数的限制,比如在Map<K extends DemoClass,V> 那么 K的 bounds为DemoClass，V 的限制为Object
    Type[] getBounds();

    /**
     * 因为只有在继承了@see GenericDeclaration接口的才能够定义类型变量,所以这里将会返回
     */
    D getGenericDeclaration();

    /**
     * 返回名称
     */
    String getName();
    
     /*
      * 返回该类型参数中使用到注解
      */
     AnnotatedType[] getAnnotatedBounds();
}

```