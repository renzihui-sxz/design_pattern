package com.sxz.designpattern.context;

import com.sxz.designpattern.util.ReflectUtil;

import java.lang.reflect.Method;

/**
 * @author suxz
 */
public class MethodObj {

    private Method method;

    private Object obj;

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public static MethodObj getInstance(Method method, Class<?> clazz) {
        MethodObj methodObj = new MethodObj();
        methodObj.setMethod(method);
        methodObj.setObj(ReflectUtil.newInstance(clazz));
        return methodObj;
    }
}
