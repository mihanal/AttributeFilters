package main.java.com.filter;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Ordering;
import main.java.com.wildcard.WildCardMatcher;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by Mihan.Liyanage on 1/2/2015.
 */
public class GoogleFilter<E,T> implements Filter<E,T> {

    @Override
    public Collection<E> find(List<E> itemList, Map<Method, T> methodList, boolean sort, final Method sortingAttribute) {

        final String error = "Method invocation failure";
        Collection resultList = null;

        final WildCardMatcher wildCardMatcher = new WildCardMatcher();

        for (final Map.Entry<Method, T> entry : methodList.entrySet()){

            final Method method = entry.getKey();
            final Object mappingObject = entry.getValue();

            resultList = Collections2.filter(itemList, new Predicate<Object>() {
                @Override
                public boolean apply(Object object) {
                    if (mappingObject.toString().contains("*")) {
                        try {
                            try {
                                return Pattern.matches(wildCardMatcher.wildcardToRegex(mappingObject.toString().toLowerCase()), method.invoke(object).toString().toLowerCase());
                            } catch (NullPointerException ignored) {
                            }
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            try {
                                return method.invoke(object).toString().toLowerCase().equals(mappingObject.toString().toLowerCase());
                            } catch (NullPointerException ignored) {}
                        } catch (IllegalAccessException e) {
                            System.out.println("Error = " + error + e.getMessage());
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            System.out.println("Error = " + error + e.getMessage());
                            e.printStackTrace();
                        }
                    }
                    return false;
                }
            });
        }

        /*-------------------- sorting --------------------*/
        if(sort) {
            Ordering<Object> attributeOrder = new Ordering<Object>() {
                public int compare(Object objectOne, Object objectTwo) {
                    try {
                        try {
                            if(sortingAttribute.invoke(objectOne) instanceof Double){
                                return Double.valueOf(sortingAttribute.invoke(objectOne) + "").compareTo(Double.valueOf(sortingAttribute.invoke(objectTwo) + ""));
                            }
                            else {
                                return sortingAttribute.invoke(objectOne).toString().toLowerCase().compareTo(sortingAttribute.invoke(objectTwo).toString().toLowerCase());
                            }
                        }catch (NullPointerException e){
                            return -1;
                        }
                    } catch (IllegalAccessException e) {
                        System.out.println("Error = " + error + e.getMessage());
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        System.out.println("Error = " + error + e.getMessage());
                        e.printStackTrace();
                    }
                    return -1;
                }
            };
            if(resultList!=null)
                resultList = attributeOrder.sortedCopy(resultList);
        }
        return resultList;
    }
}
