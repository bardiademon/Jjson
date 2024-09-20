package com.bardiademon.Jjson.converter.string;

import com.bardiademon.Jjson.converter.JjsonObjectConverter;
import com.bardiademon.Jjson.data.model.JjsonString;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class JjsonStringConverter {

    private final static char[] CONTROL_CHARACTERS = {'\n', '\t', '\r', '\b', '\f', '"'};
    private final static char[] CONTROL_CHARACTERS_WITHOUT_BACKSLASH = {'n', 't', 'r', 'b', 'f'};

    private JjsonStringConverter() {
    }

    @Deprecated
    public static JjsonString escaped(final String input) {
        if (input == null || input.trim().isEmpty()) {
            return new JjsonString(input, null);
        }
        final StringBuilder result = new StringBuilder();
        for (int i = 0, escapeCounter = 0, controlCharSearch; i < input.length(); i++) {

            if (input.charAt(i) == '\\') {
                escapeCounter++;
                continue;
            }

            controlCharSearch = Arrays.binarySearch(CONTROL_CHARACTERS, input.charAt(i));
            if (escapeCounter > 0) {
                if (controlCharSearch < 0 && escapeCounter % 2 == 1) {
                    escapeCounter++;
                }
                result.append("\\".repeat(escapeCounter));
            }

            if (controlCharSearch >= 0) {
                result.append(switch (input.charAt(i)) {
                    case '\n' -> "\\n";
                    case '\r' -> "\\r";
                    case '\b' -> "\\b";
                    case '\f' -> "\\f";
                    case '"' -> "\\\"";
                    default -> input.charAt(i);
                });
                escapeCounter = 0;
                continue;
            }

            escapeCounter = 0;
            result.append(input.charAt(i));
        }

        if (input.contentEquals(result.toString())) {
            return new JjsonString(input, null);
        } else {
            return new JjsonString(input, result.toString());
        }
    }

    public static String unescapeUnicode(final String input) {
        final Pattern pattern = Pattern.compile("\\\\u([0-9A-Fa-f]{4})");
        final Matcher matcher = pattern.matcher(input);
        final StringBuilder result = new StringBuilder();
        while (matcher.find()) {
            final String hex = matcher.group(1);
            final char unicodeChar = (char) Integer.parseInt(hex, 16);
            matcher.appendReplacement(result, Character.toString(unicodeChar));
        }
        matcher.appendTail(result);
        return result.toString();
    }

    public static String toPlainText(final String input) {
        if (input == null || input.trim().isEmpty()) {
            return input;
        }
        final String replace = unescapeUnicode(input)
                .replace("\\n", "\n")
                .replace("\\r", "\r")
                .replace("\\b", "\b")
                .replace("\\f", "\f");

        final StringBuilder result = new StringBuilder();
        for (int i = 0, escapeCounter = 0, controlCharSearch; i < replace.length(); i++) {
            if (replace.charAt(i) == '\\') {
                escapeCounter++;
                continue;
            }
            if (escapeCounter > 0) {
                escapeCounter = 0;
                if ((controlCharSearch = Arrays.binarySearch(CONTROL_CHARACTERS_WITHOUT_BACKSLASH, replace.charAt(i))) >= 0) {
                    result.append(CONTROL_CHARACTERS[controlCharSearch]);
                    continue;
                }
            }
            result.append(replace.charAt(i));
        }

        return result.toString();
    }

    public static JjsonString escapedByJackson(final String input) {
        if (input == null || input.isEmpty()) {
            return new JjsonString(input, null);
        }
        try {
            String escaped = JjsonObjectConverter.converter().getObjectMapper().writeValueAsString(input);
            escaped = escaped.substring(1, escaped.length() - 1);
            if (input.contentEquals(escaped)) {
                return new JjsonString(input, null);
            } else {
                return new JjsonString(input, escaped);
            }
        } catch (JsonProcessingException e) {
            return new JjsonString(input, null);
        }
    }

    public static String unescaped(final String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        try {
            return JjsonObjectConverter.converter().getObjectMapper().readTree(input).asText();
        } catch (JsonProcessingException e) {
            return input;
        }
    }

}
