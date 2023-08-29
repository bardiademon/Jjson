package com.bardiademon.Jjson.converter;

import com.bardiademon.Jjson.util.Logger;
import com.bardiademon.Jjson.data.enums.JsonValueType;
import com.bardiademon.Jjson.data.exception.JjsonException;

import java.text.NumberFormat;
import java.text.ParseException;

sealed class JjsonConverter permits JjsonArrayConverter, JjsonObjectConverter {
    private static final Logger logger = new Logger(JjsonConverter.class);

    protected int getCloseJsonValueString(final char[] jsonChars, final char open, final char close, final int start) {
        int number = 1;
        int numberFound = 0;
        for (int i = start; i < jsonChars.length; i++) {
            if (jsonChars[i] == open) {
                number++;
            } else if (jsonChars[i] == close) {
                numberFound++;
            }
            if (numberFound == number) {
                return i + 1;
            }
        }
        return -1;
    }

    protected Object[] findCharWithoutSpace(final char[] jsonChars, final int start) {
        for (int i = start; i < jsonChars.length; i++) {
            if ((i - 1 >= 0 && jsonChars[i - 1] != '\\') && jsonChars[i] != ' ' && jsonChars[i] != '\t' && jsonChars[i] != '\n') {
                return new Object[]{i, jsonChars[i]};
            }
        }
        return null;
    }

    protected String getCharFromResultObject(final Object[] objects) {
        return objects != null && objects.length == 2 && objects[1] instanceof Character ? objects[1].toString() : null;
    }

    protected Object[] goTo(final char[] jsonChars, final int start, final char aChar, final boolean checkBackSlash) {
        for (int i = start; i < jsonChars.length; i++) {
            if (jsonChars[i] == aChar) {
                if (checkBackSlash) {
                    if ((i - 1 >= 0 && jsonChars[i - 1] == '\\') && (i - 2 >= 0 && jsonChars[i - 2] != '\\')) {
                        continue;
                    }
                }
                return new Object[]{i, jsonChars[i]};
            }
        }
        return null;
    }

    protected Object[] getString(final String json, final char[] jsonChars, int index) throws JjsonException {
        final Object[] findQuotation = findCharWithoutSpace(jsonChars, index);
        if (findQuotation == null || ((char) findQuotation[1]) != '"') {
            throw new JjsonException("Not found \"", getCharFromResultObject(findQuotation), index);
        }
        index = (int) findQuotation[0] + 1;

        final Object[] findSecondQuotation = goTo(jsonChars, index, '"', true);
        if (findSecondQuotation == null || ((char) findSecondQuotation[1]) != '"') {
            throw new JjsonException("Not found \"", getCharFromResultObject(findSecondQuotation), index);
        }

        final String string = json.substring(index, (int) findSecondQuotation[0]);

        index = (int) findSecondQuotation[0] + 1;

        return new Object[]{index, string};
    }

    protected Object[] getValue(final Object[] findFirstChar, final String json, final char[] jsonChars, int index) throws JjsonException {

        final char aChar = (char) findFirstChar[1];

        // Null
        if (aChar == 'n') {
            logger.trace("Null value, Char -> {} , Index: {}", aChar, index);

            index = ((int) findFirstChar[0]) + 4; // 4 = "null".length()

            final String nullValue = json.substring((int) findFirstChar[0], index);
            if (!nullValue.trim().equals("null")) {
                throw new JjsonException("Invalid value", nullValue, index);
            }
            return new Object[]{index, null, JsonValueType.NULL};

            // String
        } else if (aChar == '"') {
            logger.trace("String value, Char -> {} , Index: {}", aChar, index);
            final Object[] stringValue = getString(json, jsonChars, (Integer) findFirstChar[0]);
            return new Object[]{stringValue[0], stringValue[1], JsonValueType.STRING};
            // Boolean
        } else if (aChar == 't' || aChar == 'f') {
            logger.trace("Boolean value, Char -> {} , Index: {}", aChar, index);

            index = ((int) findFirstChar[0]) + (aChar == 't' ? 4 : 5); // "true","false" -> .length();

            final String booleanValue = json.substring((int) findFirstChar[0], index);
            if (!booleanValue.equals("true") && !booleanValue.equals("false")) {
                throw new JjsonException("Invalid value", booleanValue, index);
            }
            return new Object[]{index, Boolean.valueOf(booleanValue), JsonValueType.BOOLEAN};

            // Number
        } else if (Character.isDigit(aChar) || aChar == '-') {
            logger.trace("Number value, Char -> {} , Index: {}", aChar, index);

            final int i = gotoFind((int) findFirstChar[0] + 1, jsonChars, c -> c != '.' && !Character.isDigit(c));

            final String numberValue = json.substring((int) findFirstChar[0], i);

            final Number number;
            try {
                number = NumberFormat.getInstance().parse(numberValue);
            } catch (ParseException e) {
                logger.error("Fail to convert to number: {}", numberValue, e);
                throw new JjsonException("Invalid number value", numberValue, (int) findFirstChar[0]);
            }
            return new Object[]{i, number, JsonValueType.NUMBER};

            //  JsonObject
        } else if (aChar == '{') {
            logger.trace("JsonObject value, Char -> {} , Index: {}", aChar, index);
            final int closeJsonValueString = getCloseJsonValueString(jsonChars, '{', '}', (int) findFirstChar[0] + 1);
            if (closeJsonValueString == -1) {
                throw new JjsonException("Invalid json object", (int) findFirstChar[0]);
            }
            final String jsonString = json.substring((int) findFirstChar[0], closeJsonValueString);
            return new Object[]{closeJsonValueString, jsonString, JsonValueType.JSON_OBJECT};

            //  JsonArray
        } else if (aChar == '[') {
            logger.trace("JsonArray value, Char -> {} , Index: {}", aChar, index);
            final int closeJsonValueString = getCloseJsonValueString(jsonChars, '[', ']', (int) findFirstChar[0] + 1);
            if (closeJsonValueString == -1) {
                throw new JjsonException("Invalid json array", (int) findFirstChar[0]);
            }
            final String jsonString = json.substring((int) findFirstChar[0], closeJsonValueString);
            return new Object[]{closeJsonValueString, jsonString, JsonValueType.JSON_ARRAY};

            // InvalidValue
        } else {
            throw new JjsonException("Invalid value", String.valueOf(aChar), index);
        }
    }

    protected boolean isEmpty(final String json, final char open, final char close) throws JjsonException {
        if (json == null || json.isEmpty()) {
            throw new JjsonException("Invalid json", 0);
        }

        final char[] charArray = json.trim().toCharArray();

        if (charArray[0] != open) {
            throw new JjsonException("Invalid json", String.valueOf(charArray[0]), 0);
        }

        final Object[] findClose = findCharWithoutSpace(charArray, 1);
        if (findClose == null) {
            throw new JjsonException("Invalid json", 0);
        }

        if (((char) findClose[1]) == close) {
            logger.trace("Json is empty: {}", json);
            return true;
        }

        return false;
    }

    // end of json
    protected int eoj(final char[] jsonChars, int index, final char closeChar) throws JjsonException {
        final Object[] findComma = findCharWithoutSpace(jsonChars, index);
        if (findComma == null) {
            throw new JjsonException("Invalid json " + (closeChar == '}' ? "object" : "array"), index);
        }
        if (((char) findComma[1]) == ',') {
            return ((int) findComma[0]) + 1;
        }
        if (((char) findComma[1]) != closeChar) {
            throw new JjsonException("Invalid json object", String.valueOf(findComma[1]), (int) findComma[0]);
        }
        final Object[] findNothing = findCharWithoutSpace(jsonChars, (int) findComma[0] + 1);
        if (findNothing != null) {
            throw new JjsonException("Invalid json object", String.valueOf(findNothing[1]), (int) findNothing[0]);
        }

        return -1;
    }

    protected int gotoFind(final int start, final char[] jsonChars, final If anIf) {
        int i = start;
        for (; i < jsonChars.length; i++) {
            if (anIf.condition(jsonChars[i])) {
                break;
            }
        }
        return i;
    }

    private static final String BN = "##BN##";
    private static final String BR = "##BR##";
    private static final String BT = "##BT##";
    private static final String BF = "##BF##";
    private static final String BB = "##BB##";

    public String stringFormatter(final String str) {
        if (str == null) {
            return null;
        }
        if (str.isEmpty()) {
            return "";
        }

//        '\"', '\'', '\n', '\r', '\t', '\b', '\f', '\\'
        return str.replaceAll("\n", BN)
                .replaceAll("\r", BR)
                .replaceAll("\t", BT)
                .replaceAll("\f", BF)
                .replaceAll("\b", BB);
    }

    public String stringFormatterReverse(final String str) {
        if (str == null) {
            return null;
        }
        if (str.isEmpty()) {
            return "";
        }

//        '\"', '\'', '\n', '\r', '\t', '\b', '\f', '\\'
        return str.replaceAll(BN, "\n")
                .replaceAll(BR, "\r")
                .replaceAll(BT, "\t")
                .replaceAll(BF, "\f")
                .replaceAll(BB, "\b");
    }

    protected interface If {
        boolean condition(final char aChar);
    }

    protected String space(final int numberOfSpace) {
        if (numberOfSpace <= 0) return "";
        return "  ".repeat(numberOfSpace);
    }
}
