package com.core.qa.automation.common.utils.reflection;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * Utility class for reflection-based operations.
 * <p>
 * <b>Example usage:</b>
 * <pre>
 *     Method m = ReflectionUtils.getAssignableMethod(MyClass.class, "myMethod", String.class);
 * </pre>
 */
public class ReflectionUtils {

    private ReflectionUtils() {
        // Utility class
    }

    /**
     * Returns the first method from clazz's methods which has a type signature that is assignable from params.
     * This differs from class.getMethod(name, params) in that params do not have to exactly match.
     *
     * @param clazz      Class to search for method in
     * @param methodName Name of method to search for
     * @param params     Parameter types of method to search for
     * @return Method assignable from provided parameter types
     * @throws NoSuchMethodException If no such method exists
     */
    public static Method getAssignableMethod(Class<?> clazz, String methodName, Class<?>... params) throws NoSuchMethodException {
        return Arrays.stream(clazz.getMethods())
                .filter(method -> method.getName().equals(methodName))
                .filter(method -> method.getParameterCount() == params.length)
                .filter(method -> IntStream.range(0, params.length)
                        .allMatch(i -> method.getParameterTypes()[i].isAssignableFrom(params[i])))
                .findFirst()
                .orElseThrow(() -> new NoSuchMethodException(
                        "No such method: " + methodName + " assignable from parameter types"));
    }

    /**
     * Returns the first method from clazz's methods which has a type signature that is assignable from params.
     *
     * @param clazz      Class to search for method in
     * @param methodName Name of method to search for
     * @param params     Objects whose types determine the parameter types
     * @return Method assignable from provided parameter types
     * @throws NoSuchMethodException If no such method exists
     */
    public static Method getAssignableMethod(Class<?> clazz, String methodName, Object[] params) throws NoSuchMethodException {
        return getAssignableMethod(clazz, methodName, getTypes(params));
    }

    /**
     * Gets the types of an array of objects.
     *
     * @param params the objects
     * @return an array of Class types
     */
    public static Class<?>[] getTypes(Object... params) {
        return Arrays.stream(params)
                .map(Object::getClass)
                .toArray(Class<?>[]::new);
    }

    /**
     * Invokes a method by name on an object.
     *
     * @param target     the target object
     * @param methodName the method name
     * @param args       the method arguments
     * @return the result of the method invocation
     * @throws Exception if invocation fails
     */
    public static Object invokeMethod(Object target, String methodName, Object... args) throws Exception {
        Method method = getAssignableMethod(target.getClass(), methodName, args);
        return method.invoke(target, args);
    }
}

