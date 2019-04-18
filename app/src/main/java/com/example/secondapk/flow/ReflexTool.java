package com.example.secondapk.flow;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by rover12421 on 1/5/14.
 * 鍙嶅皠闈欐�佸簱
 */
public class ReflexTool {

    @SuppressWarnings("rawtypes")
    private static Class[] getObjesType(Object[] objects) {
        Class[] clazzes = new Class[objects.length];
        for (int i = 0; i < clazzes.length; i++) {
            clazzes[i] = objects[i].getClass();
        }
        return clazzes;
    }

    public static Object newInstance(Class<?> clazz, Object... objects) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        @SuppressWarnings("rawtypes")
        Constructor constructor = clazz.getDeclaredConstructor( getObjesType( objects ) );
        constructor.setAccessible( true );
        return constructor.newInstance( objects );
    }

    public static Field getField(Class<?> clazz, String name) throws NoSuchFieldException {

        Class<?> cl = clazz;
        NoSuchFieldException exception = null;
        while (cl != null && !cl.getName().equals( Object.class.getName() )) {
            try {
                Field field = cl.getDeclaredField( name );
                field.setAccessible( true );
                return field;
            } catch (NoSuchFieldException e) {
                if (exception == null) {
                    exception = e;
                }
                cl = cl.getSuperclass();
            }
        }

        throw exception;
    }

    public static Field getField(Object obj, String name) throws NoSuchFieldException {
        return getField( obj.getClass(), name );
    }

    public static void setFieldValue(Object obj, String name, Object value) throws NoSuchFieldException, IllegalAccessException {
        Field field = getField( obj, name );
        field.set( obj, value );
    }

    /**
     * 璁剧疆绫讳腑闈欐�佸瓧娈靛��
     *
     * @param clazz
     * @param name
     * @param value
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public static void setFieldValue(Class<?> clazz, String name, Object value) throws NoSuchFieldException, IllegalAccessException {
        Field field = getField( clazz, name );
        field.set( clazz, value );
    }

    public static Object getFieldValue(Object obj, String name) throws NoSuchFieldException, IllegalAccessException {
        Field field = getField( obj, name );
        return field.get( obj );
    }

    /**
     * 鑾峰彇绫讳腑闈欐�佸瓧娈电殑鍊�
     *
     * @param clazz
     * @param name
     * @return
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public static Object getFieldValue(Class<?> clazz, String name) throws NoSuchFieldException, IllegalAccessException {
        Field field = getField( clazz, name );
        return field.get( clazz );
    }

    public static Method getMethod(Class<?> clazz, String name, @SuppressWarnings("rawtypes") Class... classes) throws NoSuchMethodException {

        Class<?> cl = clazz;
        NoSuchMethodException exception = null;
        while (cl != null && !cl.getName().equals( Object.class.getName() )) {
            try {
                Method method = cl.getDeclaredMethod( name, classes );
                method.setAccessible( true );
                return method;
            } catch (NoSuchMethodException e) {
                if (exception == null) {
                    exception = e;
                }
                cl = cl.getSuperclass();
            }
        }

        throw exception;
    }

    public static Method getMethod(Class<?> clazz, String name, Object... objects) throws NoSuchMethodException {
        return getMethod( clazz, name, getObjesType( objects ) );
    }

    public static Method getMethod(Object obj, String name, @SuppressWarnings("rawtypes") Class... classes) throws NoSuchMethodException {
        return getMethod( obj.getClass(), name, classes );
    }

    public static Method getMethod(Object obj, String name, Object... objects) throws NoSuchMethodException {
        return getMethod( obj.getClass(), name, objects );
    }

    public static Object invokeMethod(Object obj, String name, Object... objects) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = getMethod( obj, name, objects );
        return method.invoke( obj, objects );
    }

    public static Object invokeMethod(Class<?> clazz, String name, Object... objects) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = getMethod( clazz, name, objects );
        return method.invoke( null, objects );
    }
}
