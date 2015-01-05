package main.java.com.filter;

import main.java.com.wildcard.WildCardMatcher;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by Mihan.Liyanage on 12/27/2014.
 */
public class JavaFilter<E,T> implements Filter<E,T>{

    @Override
    public Collection<E> find(List<E> itemList, Map<Method, T> methodList, boolean sort, final Method sortingAttribute) {

        final String error = "Method invocation failure";

        Method method;
        Object mappingObject, iteratingObject = null;
        ArrayList<E> iteratorList;

        final WildCardMatcher wildCardMatcher = new WildCardMatcher();

        for(final Map.Entry<Method, T> entry : methodList.entrySet()){

            iteratorList = new ArrayList<E>(itemList);

            method = entry.getKey();
            mappingObject = entry.getValue();

            for(E objectIterator : iteratorList){
                try {
                    iteratingObject = method.invoke(objectIterator);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

                if(iteratingObject!=null) {

                    if (mappingObject.toString().contains("*")) {
                        if (!Pattern.matches(wildCardMatcher.wildcardToRegex(mappingObject.toString().toLowerCase()), iteratingObject.toString().toLowerCase())) {
                            itemList.remove(objectIterator);
                        }
                    } else {
                        if (!iteratingObject.toString().toLowerCase().equals(mappingObject.toString().toLowerCase())) {
                            itemList.remove(objectIterator);
                        }
                    }
                }
                else {
                    itemList.remove(objectIterator);
                }
            }
        }

        /*-------------------- sorting --------------------*/
        if(sort){

            Collections.sort(itemList, new Comparator<Object>() {
                Object objectFirst,objectSecond;
                @Override
                public int compare(Object objectOne, Object objectTwo) {

                    try {
                        objectFirst = sortingAttribute.invoke(objectOne);
                        objectSecond = sortingAttribute.invoke(objectTwo);

                        if (objectFirst == null && objectSecond == null)
                            return 0;
                        if (objectFirst == null)
                            return 1;
                        else if (objectSecond == null)
                            return -1;
                        else {
                            if (sortingAttribute.invoke(objectOne) instanceof Double) {
                                return Double.valueOf(sortingAttribute.invoke(objectOne) + "").compareTo(Double.valueOf(sortingAttribute.invoke(objectTwo) + ""));
                            } else {
                                return sortingAttribute.invoke(objectOne).toString().toLowerCase().compareTo(sortingAttribute.invoke(objectTwo).toString().toLowerCase());
                            }
                        }
                    } catch (IllegalAccessException e) {
                        System.out.println("Error = " + error + e.getMessage());
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        System.out.println("Error = " + error + e.getMessage());
                        e.printStackTrace();
                    }
                    return 0;
                }
            });
        }
        return itemList;
    }
}

