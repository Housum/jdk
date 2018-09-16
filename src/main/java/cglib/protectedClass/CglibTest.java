package cglib.protectedClass;

import net.sf.cglib.proxy.*;

import java.lang.reflect.Method;

/**
 * @author qibao
 * @since 2018/9/15
 **/
public class CglibTest {

    public static void main(String[] args) {

        MethodInterceptorImpl methodInterceptor = new MethodInterceptorImpl(new DemoClass());
        DemoClass demoClass = (DemoClass) methodInterceptor.getProxy();
        //accept
        demoClass.m1();
        demoClass.m2();
        /*
        *MethodInterceptorImpl1
        *m1
        *MethodInterceptorImpl1
        *m2
        * */


    }

    static class MethodInterceptorImpl implements MethodInterceptor {

        private DemoClass demoClass;

        public MethodInterceptorImpl(DemoClass demoClass) {
            this.demoClass = demoClass;
        }

        @Override
        public Object intercept(Object o, Method method, Object[] objects,
                                MethodProxy methodProxy) throws Throwable {
            System.out.println("MethodInterceptorImpl");
            return methodProxy.invoke(demoClass, objects);
//            return methodProxy.invokeSuper(o,objects);
        }

        public Object getProxy() {
            Enhancer enhancer = new Enhancer();
            Callback[] callbacks = new Callback[]{this, new DispatcherImpl(), new MethodInterceptorImpl1(demoClass)};
            enhancer.setCallbacks(callbacks);
            enhancer.setSuperclass(DemoClass.class);
            enhancer.setCallbackFilter(new CallbackFilterImpl());
            Class[] callbackTypes = new Class[]{MethodInterceptorImpl.class, DispatcherImpl.class, MethodInterceptorImpl1.class};
            enhancer.setCallbackTypes(callbackTypes);
            return enhancer.create();
        }
    }

    static class MethodInterceptorImpl1 implements MethodInterceptor {

        private DemoClass demoClass;

        public MethodInterceptorImpl1(DemoClass demoClass) {
            this.demoClass = demoClass;
        }

        @Override
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
            System.out.println("MethodInterceptorImpl1");
            return proxy.invoke(demoClass, args);
        }
    }

    static class DispatcherImpl implements Dispatcher {

        @Override
        public Object loadObject() throws Exception {
            System.out.println("loadObject ");
            return new DemoClass();
        }
    }

    //CallbackFilter 采用的是预先选择callbacks集合中的MethodInterceptor
    static class CallbackFilterImpl implements CallbackFilter {

        @Override
        public int accept(Method method) {
            System.out.println("accept ");
            //返回集合callbacks[2]
            return 2;
        }
    }

}
