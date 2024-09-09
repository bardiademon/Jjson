import com.bardiademon.Jjson.JjsonObject.JjsonObject;
import com.bardiademon.Jjson.exception.JjsonException;

public class TestOfString {
    public static void main(String[] args) throws JjsonException {

        final String jsonString2 = """
                {
                        "test" :  { "Test" : " { } { { {{ {{ { { { {{ {{ {{ {  {\\" [ [ [ [ [ [ [ [ [ [[ [\\" " }
                }
                """;
        final String jsonString3 = """
                {
                  "section1": {
                    "key1": "text with nested { { { } } }",
                    "key2": "text with { { { } { { } ] }",
                    "key3": "string with escaped \\\\{ { } and [ ] \\\\} and { }",
                    "key4": {
                      "subsection1": "text with { { } [ ] }",
                      "subsection2": "string with \\"nested { { } }\\"",
                      "subsection3": "{ { [ { \\"complex { { } } [ ] } ] } }"
                    }
                  },
                  "section2": [
                    "array item with { { [ ] } }",
                    "{ \\"json\\": { \\"value\\": [ { } ] } }",
                    "string with escaped \\\\{ { } }"
                  ],
                  "section3": "final text with { { { { } } } } { }",
                  "section4": "{ { \\"nested\\": { } } }"
                }
                """;

        final String str = "{\"Test\": \"'56'}{\"}";

        final JjsonObject json = JjsonObject.ofString(jsonString3);

        System.out.println("json.getString(\"section3\") = " + json.getString("section3"));
        System.out.println("json.getString(\"section4\") = " + json.getString("section4"));

        System.out.println("json = " + json);

    }
}
