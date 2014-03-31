package com.example.app;

import java.util.HashMap;

/**
 * Created by richard on 3/5/14.
 */
public class ParseSingleton {
    private static class Holder {
        private static final ParseSingleton s = new ParseSingleton();
    }

    private HashMap<String, Object> stuff;

    public void put(String s, Object o) {
        stuff.put(s, o);
    }

    public Object get(String s) {
        return stuff.get(s);
    }

    public static ParseSingleton getInstance() {
        return Holder.s;
    }

    private ParseSingleton() {
        stuff = new HashMap<String, Object>();
    }
}
