package main.java.com.filter;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by Mihan.Liyanage on 12/27/2014.
 */
public interface Filter<E,T>{

    /**
     * Attribute Filter
     * @param itemList Item List
     * @param methodList Attribute list wants to filter
     * @param sort Sorting
     * @param sortingAttribute Sorting attribute
     * @return Filtered list
     */

    public Collection<E> find(final List<E> itemList, final Map<Method,T> methodList, boolean sort, final Method sortingAttribute);
}
