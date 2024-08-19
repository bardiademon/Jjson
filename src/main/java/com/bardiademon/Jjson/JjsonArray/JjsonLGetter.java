package com.bardiademon.Jjson.JjsonArray;

import com.bardiademon.Jjson.JjsonObject.JjsonObject;

public interface JjsonLGetter {

    String asString(final int index);

    JjsonObject getJjsonObject(final int index);

    String asString(final int index, final String def);

    JjsonObject getJjsonObject(final int index, final JjsonObject def);
}
