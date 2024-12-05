package com.sxz.designpattern.context;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author suxz
 **/
public class PackageContext {

    private static final Set<String> DEFAULT_PACKAGES = new HashSet<>();

    private static String settingPackage = null;

    static {
        Package[] packages = Package.getPackages();
        List<String> packageNames = Stream.of(packages).map(Package::getName).collect(Collectors.toList());
        for (String packageName : packageNames) {
            boolean flag = true;
            for (String defaultPackage : DEFAULT_PACKAGES) {
                if (packageName.equals(defaultPackage) || packageName.startsWith(defaultPackage)) {
                    flag = false;
                    break;
                }
                if (defaultPackage.startsWith(packageName)) {
                    DEFAULT_PACKAGES.remove(defaultPackage);
                    break;
                }
            }
            if (flag) {
                DEFAULT_PACKAGES.add(packageName);
            }
        }
    }

    public static void setPackage(String packageName) {
        settingPackage = packageName;
    }

    public static String getSettingPackage() {
        return settingPackage;
    }

    public static Set<String> getDefaultPackages() {
        return DEFAULT_PACKAGES;
    }
}
