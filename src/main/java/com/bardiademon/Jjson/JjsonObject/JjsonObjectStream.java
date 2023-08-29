package com.bardiademon.Jjson.JjsonObject;

import java.util.Map;
import java.util.stream.Stream;

public interface JjsonObjectStream {
    Stream<Map.Entry<String, Object>> stream();
}
