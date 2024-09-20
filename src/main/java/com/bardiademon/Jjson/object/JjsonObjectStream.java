package com.bardiademon.Jjson.object;

import java.util.Map;
import java.util.stream.Stream;

public interface JjsonObjectStream {
    Stream<Map.Entry<String, Object>> stream();
}
