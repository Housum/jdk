###只有实现了该类，才能够定义类型参数的功能

 目前只有Class Method Constructor
  
 ```
 package java.lang.reflect;
 
 /**
  * A common interface for all entities that declare type variables.
  *
  * @since 1.5
  */
 public interface GenericDeclaration extends AnnotatedElement {
 
 
      //获取定义的类型参数 比如：Map<K,V> 那么就是K 以及V  @see TypeVailable.md
     public TypeVariable<?>[] getTypeParameters();
 }

 ```