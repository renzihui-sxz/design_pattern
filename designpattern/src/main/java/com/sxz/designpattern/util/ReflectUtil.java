package com.sxz.designpattern.util;

import com.sxz.designpattern.context.MethodObj;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;

/**
 * @author suxz
 */
public class ReflectUtil {

    private static final String PACKAGE_INDEX = ".";

    private static final String FOLD_INDEX = "/";

    private static final String CLASS_INDEX = ".class";

    private static final String FILE_INDEX = "file";

    public static List<Class<?>> getClassByAnnotation(Class<? extends Annotation> annotationClass, String packageName) throws IOException, ClassNotFoundException {
        if (Objects.nonNull(packageName) && !packageName.isEmpty()) {
            return getClassFromPackage(annotationClass, packageName);
        }
        List<Class<?>> classesResp = new ArrayList<>();
        Package[] packages = Package.getPackages();
        for (Package pkg : packages) {
            Class<?>[] classes = getClassesInPackage(pkg.getName());
            for (Class<?> clazz : classes) {
                if (clazz.isAnnotationPresent(annotationClass)) {
                    classesResp.add(clazz);
                }
            }
        }
        return classesResp;
    }

    private static List<Class<?>> getClassFromPackage(Class<? extends Annotation> annotationClass, String packageName) throws IOException, ClassNotFoundException {
        List<Class<?>> classesResp = new ArrayList<>();
        Class<?>[] classes = getClassesInPackage(packageName);
        for (Class<?> clazz : classes) {
            if (clazz.isAnnotationPresent(annotationClass)) {
                classesResp.add(clazz);
            }
        }
        return classesResp;
    }

    private static List<MethodObj> getMethodObjFromPackage(Class<? extends Annotation> annotationClass, String packageName) throws IOException, ClassNotFoundException {
        List<MethodObj> methodResp = new ArrayList<>();
        Class<?>[] classes = getClassesInPackage(packageName);
        for (Class<?> clazz : classes) {
            for (Method method : clazz.getDeclaredMethods()) {
                if (method.isAnnotationPresent(annotationClass)) {
                    methodResp.add(MethodObj.getInstance(method, clazz));
                }
            }
        }
        return methodResp;
    }

    public static List<MethodObj> getMethodByAnnotation(Class<? extends Annotation> annotationClass, String packageName) throws IOException, ClassNotFoundException {
        if (Objects.nonNull(packageName) && !packageName.isEmpty()) {
            return getMethodObjFromPackage(annotationClass, packageName);
        }
        List<MethodObj> methodResp = new ArrayList<>();
        Package[] packages = Package.getPackages();
        for (Package pkg : packages) {
            Class<?>[] classes = getClassesInPackage(pkg.getName());
            for (Class<?> clazz : classes) {
                for (Method method : clazz.getDeclaredMethods()) {
                    if (method.isAnnotationPresent(annotationClass)) {
                        methodResp.add(MethodObj.getInstance(method, clazz));
                    }
                }
            }
        }
        return methodResp;
    }

    public static Class<?>[] getClassesInPackage(String packageName) throws IOException, ClassNotFoundException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace(PACKAGE_INDEX, FOLD_INDEX);
        Enumeration<URL> resources = classLoader.getResources(path);
        List<Class<?>> classes = new ArrayList<>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            classes.addAll(findClasses(resource, packageName));
        }
        return classes.toArray(new Class[0]);
    }

    private static List<Class<?>> findClasses(URL url, String packageName) throws ClassNotFoundException, IOException {
        List<Class<?>> classes = new ArrayList<>();
        if (!url.getProtocol().equals(FILE_INDEX)) {
            return classes;
        }
        File directory = new File(URLDecoder.decode(url.getFile(), StandardCharsets.UTF_8.toString()));
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        if (Objects.isNull(files)) {
            return classes;
        }
        for (File file : files) {
            addClasses(packageName, file, classes);
        }
        return classes;
    }

    private static void addClasses(String packageName, File file, List<Class<?>> classes) throws ClassNotFoundException, IOException {
        if (file.isDirectory()) {
            classes.addAll(findClasses(file.toURI().toURL(), packageName + PACKAGE_INDEX + file.getName()));
        } else if (file.getName().endsWith(CLASS_INDEX)) {
            String className = packageName + PACKAGE_INDEX + file.getName().substring(0, file.getName().length() - 6);
            Class<?> clazz = Class.forName(className);
            classes.add(clazz);
        }
    }

    public static Object newInstance(Class<?> aClass) {
        try {
            Constructor<?> constructor = aClass.getDeclaredConstructor();
            constructor.setAccessible(true);
            return constructor.newInstance();
        } catch (NoSuchMethodException | InstantiationException
                | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
