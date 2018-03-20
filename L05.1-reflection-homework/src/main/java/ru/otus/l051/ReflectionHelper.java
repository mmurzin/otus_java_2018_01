package ru.otus.l051;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReflectionHelper {

    private ReflectionHelper() {
        throw new UnsupportedOperationException();
    }

    static <T> T instantiate(Class<T> type, Object... args) {
        try {
            if (args.length == 0) {
                return type.getDeclaredConstructor().newInstance();
            } else {
                Class<?>[] classes = toClasses(args);
                return type.getDeclaredConstructor(classes).newInstance(args);
            }
        } catch (InstantiationException | IllegalAccessException
                | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }

    static private Class<?>[] toClasses(Object[] args) {
        return Arrays.stream(args).map(Object::getClass).toArray(Class<?>[]::new);
    }

    public static String[] getNamesByPackage(String packageName)
            throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert classLoader != null;
        String path = packageName.replace('.', '/');
        Enumeration resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<>();
        while (resources.hasMoreElements()) {
            URL resource = (URL) resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        ArrayList<String> names = new ArrayList<>();
        for (File directory : dirs) {
            names.addAll(findClassNames(directory, packageName));
        }
        return names.toArray(new String[names.size()]);
    }

    private static List<String> findClassNames(File directory, String packageName) {
        String CLASS_ENDING = ".class";
        List<String> names = new ArrayList<>();
        if (!directory.exists() && isCurrentProjectDirectory(packageName)) {
            return names;
        }
        File[] files = directory.listFiles();
        assert files != null;
        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                names.addAll(findClassNames(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(CLASS_ENDING)) {
                names.add(packageName + '.' + file.getName().substring(0,
                        file.getName().length() - CLASS_ENDING.length()));
            }
        }
        return names;
    }

    private static boolean isCurrentProjectDirectory(String packageName) {
        Pattern p = Pattern.compile("^ru.otus.l051.+");
        Matcher m = p.matcher(packageName);
        boolean isValid = m.matches();
        if (!isValid) {
            throw new RuntimeException("Invalid package name " + packageName);
        }
        return true;
    }
}
