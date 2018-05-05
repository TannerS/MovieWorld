package com.dev.tanners.movieworld.api.util;

import java.util.TreeMap;

/**
    Although for this project, this is not needed, but i try to design
    my objects as if they can be used in other projects, so its bets to allow
    customization and rules for the objects, so this will be used as a default value
    if user developer trys to use something that does not exist

 * @param <K>
 * @param <E>
 */
public class DefaultValueTreeMap<K, E> extends TreeMap<K, E>
{
    private E defaultValue;

    public DefaultValueTreeMap(E defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public E get(Object key) {
        E temp = super.get(key);

        if(temp == null)
            return this.defaultValue;
        else
            return temp;
    }
}
