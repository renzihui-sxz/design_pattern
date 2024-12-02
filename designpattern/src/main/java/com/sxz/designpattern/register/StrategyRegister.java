package com.sxz.designpattern.register;

import com.sxz.designpattern.annotation.Strategy;
import com.sxz.designpattern.context.ClassContext;
import com.sxz.designpattern.context.MethodContext;
import com.sxz.designpattern.context.MethodObj;
import com.sxz.designpattern.enums.PatternEnum;
import com.sxz.designpattern.util.ReflectUtil;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @author suxz
 */
public class StrategyRegister {

    public static void initStrategyHandler(String packageName) throws IOException, ClassNotFoundException {
        List<Class<?>> classes = ReflectUtil.getClassByAnnotation(Strategy.class, packageName);
        for (Class<?> aClass : classes) {
            String id = getStrategyId(aClass);
            ClassContext.put(PatternEnum.STRATEGY, id, aClass);
        }
        List<MethodObj> methods = ReflectUtil.getMethodByAnnotation(Strategy.class, packageName);
        for (MethodObj method : methods) {
            String id = getStrategyId(method.getMethod());
            MethodContext.put(PatternEnum.STRATEGY, id, method);
        }
    }

    private static String getStrategyId(Method method) {
        Strategy strategy = method.getAnnotation(Strategy.class);
        String id = strategy.id();
        if (Objects.isNull(id)) {
            throw new RuntimeException("The policy actuator id cannot be empty.");
        }
        Set<Object> ids = getAllIds();
        if (ids.contains(id)) {
            throw new RuntimeException("The policy actuator id must be unique.");
        }
        return id;
    }

    private static Set<Object> getAllIds() {
        Set<Object> ids = new HashSet<>();
        Set<Object> classIds = ClassContext.getIds(PatternEnum.STRATEGY);
        Set<Object> methodIds = MethodContext.getIds(PatternEnum.STRATEGY);
        if (Objects.nonNull(classIds)) {
            ids.addAll(classIds);
        }
        if (Objects.nonNull(methodIds)) {
            ids.addAll(methodIds);
        }
        return ids;
    }

    private static String getStrategyId(Class<?> aClass) {
        Strategy strategy = aClass.getAnnotation(Strategy.class);
        String id = strategy.id();
        if (Objects.isNull(id)) {
            throw new RuntimeException("The policy actuator id cannot be empty.");
        }
        Set<Object> ids = getAllIds();
        if (ids.contains(id)) {
            throw new RuntimeException("The policy actuator id must be unique.");
        }
        return id;
    }
}
