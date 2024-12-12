package io.github.designpattern.register;

import io.github.designpattern.annotation.State;
import io.github.designpattern.context.MethodContext;
import io.github.designpattern.context.MethodObj;
import io.github.designpattern.enums.PatternEnum;
import io.github.designpattern.util.ReflectUtil;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author suxz
 */
public class StateRegister {

    public static void initStateHandler() throws IOException, ClassNotFoundException {
        List<MethodObj> methods = ReflectUtil.getMethodByAnnotation(State.class);
        if (methods.isEmpty()) {
            return;
        }
        validUnique(methods);
        Map<String, List<MethodObj>> stateGroup = groupingOfMethod(methods);
        for (Map.Entry<String, List<MethodObj>> entry : stateGroup.entrySet()) {
            MethodContext.batchPut(PatternEnum.STATE, entry.getKey(), entry.getValue());
        }
    }

    private static void validUnique(List<MethodObj> methods) {
        for (int current = 0; current < methods.size() - 1; current++) {
            String currentId = methods.get(current).getMethod().getAnnotation(State.class).id();
            for (int next = current + 1; next < methods.size(); next++) {
                String nextId = methods.get(next).getMethod().getAnnotation(State.class).id();
                if (currentId.equals(nextId)) {
                    throw new RuntimeException("The status actuator id must be globally unique.");
                }
            }
        }
    }

    private static Map<String, List<MethodObj>> groupingOfMethod(List<MethodObj> methodObjs) {
        return methodObjs.stream().collect(Collectors.groupingBy(x -> x.getMethod().getAnnotation(State.class).groupId()));
    }
}
