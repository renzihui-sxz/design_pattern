package io.github.designpattern.register;

import io.github.designpattern.annotation.Strategy;
import io.github.designpattern.context.ClassContext;
import io.github.designpattern.context.MethodContext;
import io.github.designpattern.context.MethodObj;
import io.github.designpattern.enums.PatternEnum;
import io.github.designpattern.util.ReflectUtil;

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

    public static void initStrategyHandler() throws IOException, ClassNotFoundException {
        List<Class<?>> classes = ReflectUtil.getClassByAnnotation(Strategy.class);
        for (Class<?> aClass : classes) {
            String id = getStrategyId(aClass);
            ClassContext.put(PatternEnum.STRATEGY, id, aClass);
        }
        List<MethodObj> methods = ReflectUtil.getMethodByAnnotation(Strategy.class);
        for (MethodObj method : methods) {
            String id = getStrategyId(method.getMethod());
            MethodContext.put(PatternEnum.STRATEGY, id, method);
        }
    }

    private static String getStrategyId(Method method) {
        Strategy strategy = method.getAnnotation(Strategy.class);
        String id = strategy.id();
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
        Set<Object> ids = getAllIds();
        if (ids.contains(id)) {
            throw new RuntimeException("The policy actuator id must be unique.");
        }
        return id;
    }
}
