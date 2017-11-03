### Set的骨架实现
```

/**
 * 1.这个类是Set的骨架实现类,大部分的子类只需要实现它就可以了,可以很简单的就实现
 * set的功能,但是需要注意的是必须要满足set的定义: 不能包含重复的元素
 * 2.该基类继承了AbstractCollection,但是没有实现任何的方法(只是实现了HashCode以及equal方法)
 */

public abstract class AbstractSet<E> extends AbstractCollection<E> implements Set<E> {
    /**
     */
    protected AbstractSet() {
    }

    // Comparison and hashing

    /**
     * 1.对比两个集合,如果两个集合类型都为Set,并且对应的每一个索引上的元素都是相等的
     * (a.equal(b) =true ) 那么就认为两个集合相等。这里注意：在没有覆盖equal方法的情况下
     * 那么只要两个都是Set的子类，并且每一个对应的元素都相等，那么就返回true
     */
    public boolean equals(Object o) {
        if (o == this)
            return true;

        if (!(o instanceof Set))
            return false;
        Collection<?> c = (Collection<?>) o;
        if (c.size() != size())
            return false;
        try {
            return containsAll(c);
        } catch (ClassCastException unused)   {
            return false;
        } catch (NullPointerException unused) {
            return false;
        }
    }

    /**
     * 1.返回集合的hashCode,集合的hashCode等于每一个元素的hashCode相加，如果是null的话,
     * 那么就为0
     * 2.方法实现的方式是获得ListIterator,然后遍历每一个元素,将每一个元素的hashCode都
     * 进行相加
     */
    public int hashCode() {
        int h = 0;
        Iterator<E> i = iterator();
        while (i.hasNext()) {
            E obj = i.next();
            if (obj != null)
                h += obj.hashCode();
        }
        return h;
    }

    /**
     * 1.将集合中的元素都删除,主要删除成功一个或者一个以上的元素那么返回true
     * 2.如果实现的集合不支持remove方法,那么将会抛出UOE
     */
    public boolean removeAll(Collection<?> c) {
        Objects.requireNonNull(c);
        boolean modified = false;

        if (size() > c.size()) {
            for (Iterator<?> i = c.iterator(); i.hasNext(); )
                modified |= remove(i.next());
        } else {
            for (Iterator<?> i = iterator(); i.hasNext(); ) {
                if (c.contains(i.next())) {
                    i.remove();
                    modified = true;
                }
            }
        }
        return modified;
    }

}

```