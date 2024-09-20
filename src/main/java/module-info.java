open module Jjson {
    requires com.fasterxml.jackson.databind;
    exports com.bardiademon.Jjson;
    exports com.bardiademon.Jjson.config;
    exports com.bardiademon.Jjson.converter.clazz;
    exports com.bardiademon.Jjson.converter.string;
    exports com.bardiademon.Jjson.data.collection;
    exports com.bardiademon.Jjson.data.model;
    exports com.bardiademon.Jjson.encoder;
    exports com.bardiademon.Jjson.exception;
    exports com.bardiademon.Jjson.io;
    exports com.bardiademon.Jjson.array;
    exports com.bardiademon.Jjson.object;
    exports com.bardiademon.Jjson.validation;
}