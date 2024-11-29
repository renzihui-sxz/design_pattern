package com.sxz.designpattern.context;

import com.sxz.designpattern.enums.PatternEnum;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author suxz
 */
public class MethodContext {

    private static final Map<String, Map<Object, MethodObj>> SINGLE_CONTEXT = new ConcurrentHashMap<>();

    private static final Map<String, Map<Object, Collection<MethodObj>>> BATCH_CONTEXT = new ConcurrentHashMap<>();

    public static void put(PatternEnum patternEnum, Object id, MethodObj method) {
        Map<Object, MethodObj> methodMap = SINGLE_CONTEXT.get(patternEnum.getModel());
        if (Objects.isNull(methodMap)) {
            methodMap = new ConcurrentHashMap<>();
        }
        methodMap.put(id, method);
        SINGLE_CONTEXT.put(patternEnum.getModel(), methodMap);
    }

    public static void batchPut(PatternEnum patternEnum, Object id, List<MethodObj> classes) {
        Map<Object, Collection<MethodObj>> methodMap = BATCH_CONTEXT.get(patternEnum.getModel());
        if (Objects.isNull(methodMap)) {
            methodMap = new ConcurrentHashMap<>();
        }
        methodMap.put(id, classes);
        BATCH_CONTEXT.put(patternEnum.getModel(), methodMap);
    }

    public static MethodObj get(PatternEnum patternEnum, Object id) {
        Map<Object, MethodObj> methodMap = SINGLE_CONTEXT.get(patternEnum.getModel());
        return Objects.isNull(methodMap) ? null : methodMap.get(id);
    }

    public static Collection<MethodObj> list(PatternEnum patternEnum, Object id) {
        Map<Object, Collection<MethodObj>> classMap = BATCH_CONTEXT.get(patternEnum.getModel());
        return Objects.isNull(classMap) ? null : classMap.get(id);
    }

    public static Set<Object> getIds(PatternEnum patternEnum) {
        Map<Object, MethodObj> methodMap = SINGLE_CONTEXT.get(patternEnum.getModel());
        return Objects.isNull(methodMap) ? null : methodMap.keySet();
    }
}
