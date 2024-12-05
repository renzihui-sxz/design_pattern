package com.sxz.designpattern.register;

import com.sxz.designpattern.annotation.ChainOrder;
import com.sxz.designpattern.context.ClassContext;
import com.sxz.designpattern.context.MethodContext;
import com.sxz.designpattern.context.MethodObj;
import com.sxz.designpattern.enums.PatternEnum;
import com.sxz.designpattern.util.ReflectUtil;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author suxz
 */
public class ChainOfResponsibilityRegister {

    public static void initChainOfResponsibilityHandler() throws IOException, ClassNotFoundException {
        List<Class<?>> classes = ReflectUtil.getClassByAnnotation(ChainOrder.class);
        if (!classes.isEmpty()) {
            Map<String, List<Class<?>>> groupClass = groupingByIdOfClass(classes);
            groupClass.forEach((key, value) -> ClassContext.batchPut(PatternEnum.CHAIN_OF_RESPONSIBILITY, key, value));
        }
        List<MethodObj> methods = ReflectUtil.getMethodByAnnotation(ChainOrder.class);
        if (!methods.isEmpty()) {
            Map<String, List<MethodObj>> groupMethod = groupingByIdOfMethod(methods);
            groupMethod.forEach((key, value) -> MethodContext.batchPut(PatternEnum.CHAIN_OF_RESPONSIBILITY, key, value));
        }
    }

    private static Map<String, List<Class<?>>> groupingByIdOfClass(List<Class<?>> classes) {
        return classes.stream().collect(Collectors.groupingBy(x -> x.getAnnotation(ChainOrder.class).id()));
    }

    private static Map<String, List<MethodObj>> groupingByIdOfMethod(List<MethodObj> methodObjs) {
        return methodObjs.stream().collect(Collectors.groupingBy(x -> x.getMethod().getAnnotation(ChainOrder.class).id()));
    }
}
