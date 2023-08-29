package com.bardiademon.Jjson.JjsonObject;

import com.bardiademon.Jjson.JjsonCollection;

public interface JjsonObjectCollection extends JjsonCollection {
    boolean remove(final String key);

    boolean has(final String key);
}
