### 标记成员基类

该接口是最基本的一个接口，标记实现类是一个成员，目前实现类有Field，Method 以及Constructor
```
ublic
interface Member {

    /**
     * 共有的，包含父类的
     */
    public static final int PUBLIC = 0;

    /**
     * 本类所有的，不包含父类
     */
    public static final int DECLARED = 1;

    /**
     * 返回定义的类
     */
    public Class<?> getDeclaringClass();

    /**
     * 返回名称
     */
    public String getName();

    /**
     * 返回权限 
     * @see Modifier
     */
    public int getModifiers();

    //返回该member是否是编译器生成的
    public boolean isSynthetic();
```