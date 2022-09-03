package com.game.common.utils.bean;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Bean utility class
 *
 * @author Yu Yue
 */
public class BeanUtils extends org.springframework.beans.BeanUtils
{

    private static final int BEAN_METHOD_PROP_INDEX = 3;


    private static final Pattern GET_PATTERN = Pattern.compile("get(\\p{javaUpperCase}\\w*)");


    private static final Pattern SET_PATTERN = Pattern.compile("set(\\p{javaUpperCase}\\w*)");

    /**
     * Bean property copy tool method.
     *
     * @param dest target object
     * @param src source object
     */
    public static void copyBeanProp(Object dest, Object src)
    {
        try
        {
            copyProperties(src, dest);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Get the setter method of the object.
     *
     * @param obj object
     * @return list of setter methods for the object
     */
    public static List<Method> getSetterMethods(Object obj)
    {
        // List of setter methods
        List<Method> setterMethods = new ArrayList<Method>();


        Method[] methods = obj.getClass().getMethods();



        for (Method method : methods)
        {
            Matcher m = SET_PATTERN.matcher(method.getName());
            if (m.matches() && (method.getParameterTypes().length == 1))
            {
                setterMethods.add(method);
            }
        }

        return setterMethods;
    }

    /**
     * Get the getter method of the object.
     *
     * @param obj object
     * @return a list of getter methods for the object
     */

    public static List<Method> getGetterMethods(Object obj)
    {
        // list of getter methods
        List<Method> getterMethods = new ArrayList<Method>();

        Method[] methods = obj.getClass().getMethods();

        for (Method method : methods)
        {
            Matcher m = GET_PATTERN.matcher(method.getName());
            if (m.matches() && (method.getParameterTypes().length == 0))
            {
                getterMethods.add(method);
            }
        }

        return getterMethods;
    }

    /**
     * Check whether the property names in the Bean method name are equal. <br>
     * If the property names of getName() and setName() are the same, the property names of getName() and setAge() are different.
     *
     * @param m1 method name 1
     * @param m2 method name 2
     * @return returns true as the property name, otherwise returns false
     */

    public static boolean isMethodPropEquals(String m1, String m2)
    {
        return m1.substring(BEAN_METHOD_PROP_INDEX).equals(m2.substring(BEAN_METHOD_PROP_INDEX));
    }
}
