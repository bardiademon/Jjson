package com.bardiademon.Jjson.JjsonArray;

import com.bardiademon.Jjson.JjsonCollection;

public interface JjsonArrayCollection extends JjsonCollection {
    boolean remove(final int index);

    int has(final Object obj);
}
