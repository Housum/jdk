###能够定时类型变量的功能接口 （目前只有Class Method Constructor） 
 
 ```
 package java.lang.reflect;
 
 /**
  * A common interface for all entities that declare type variables.
  *
  * @since 1.5
  */
 public interface GenericDeclaration extends AnnotatedElement {
 
 
      //获取定义的类型变量 比如：Map<K,V> 那么就是K 以及V  @see TypeVailable.md
     public TypeVariable<?>[] getTypeParameters();
 }

 ```