package com.bardiademon.Jjson.array;

import com.bardiademon.Jjson.data.collection.JjsonCollection;

public interface JjsonArrayCollection extends JjsonCollection {
    boolean remove(final int index);

    int has(final Object obj);
}
