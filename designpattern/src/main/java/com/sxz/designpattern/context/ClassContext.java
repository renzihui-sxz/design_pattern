package com.sxz.designpattern.context;

import com.sxz.designpattern.enums.PatternEnum;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author suxz
 */
public class ClassContext {

    private static final Map<String, Map<Object, Class<?>>> SINGLE_CONTEXT = new ConcurrentHashMap<>();

    private static final Map<String, Map<Object, Collection<Class<?>>>> BATCH_CONTEXT = new ConcurrentHashMap<>();

    public static void put(PatternEnum patternEnum, Object id, Class<?> classFile) {
        Map<Object, Class<?>> classMap = SINGLE_CONTEXT.get(patternEnum.getModel());
        if (Objects.isNull(classMap)) {
            classMap = new ConcurrentHashMap<>();
        }
        classMap.put(id, classFile);
        SINGLE_CONTEXT.put(patternEnum.getModel(), classMap);
    }

    public static void batchPut(PatternEnum patternEnum, Object id, List<Class<?>> classes) {
        Map<Object, Collection<Class<?>>> classMap = BATCH_CONTEXT.get(patternEnum.getModel());
        if (Objects.isNull(classMap)) {
            classMap = new ConcurrentHashMap<>();
        }
        classMap.put(id, classes);
        BATCH_CONTEXT.put(patternEnum.getModel(), classMap);
    }

    public static Class<?> get(PatternEnum patternEnum, Object id) {
        Map<Object, Class<?>> classMap = SINGLE_CONTEXT.get(patternEnum.getModel());
        return Objects.isNull(classMap) ? null : classMap.get(id);
    }

    public static Collection<Class<?>> list(PatternEnum patternEnum, Object id) {
        Map<Object, Collection<Class<?>>> classMap = BATCH_CONTEXT.get(patternEnum.getModel());
        return Objects.isNull(classMap) ? null : classMap.get(id);
    }

    public static Set<Object> getIds(PatternEnum patternEnum) {
        Map<Object, Class<?>> classMap = SINGLE_CONTEXT.get(patternEnum.getModel());
        return Objects.isNull(classMap) ? null : classMap.keySet();
    }
}
