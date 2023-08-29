import com.bardiademon.Jjson.JjsonObject.JjsonObject;
import com.bardiademon.Jjson.data.exception.JjsonException;

public final class JjsonTest {
    public static void main(final String[] args) throws JjsonException {

        final JjsonObject jjsonObject = JjsonObject.fromString("{\n" +
                "  \"person\": {\n" +
                "    \"name\": \"John Doe\",\n" +
                "    \"age\": 30,\n" +
                "    \"email\": \"johndoe@example.com\",\n" +
                "    \"address\": {\n" +
                "      \"street\": \"123 Main St\",\n" +
                "      \"city\": \"New York\",\n" +
                "      \"state\": \"NY\",\n" +
                "      \"country\": \"USA\"\n" +
                "    },\n" +
                "    \"phoneNumbers\": [\n" +
                "      {\n" +
                "        \"type\": \"home\",\n" +
                "        \"number\": \"+1-123-456-7890\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"type\": \"work\",\n" +
                "        \"number\": \"+1-987-654-3210\"\n" +
                "      }\n" +
                "    ],\n" +
                "    \"friends\": [\n" +
                "      {\n" +
                "        \"name\": \"Jane Smith\",\n" +
                "        \"age\": 28,\n" +
                "        \"email\": \"janesmith@example.com\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"name\": \"Bob Johnson\",\n" +
                "        \"age\": 32,\n" +
                "        \"email\": \"bobjohnson@example.com\"\n" +
                "      }\n" +
                "    ]\n" +
                "  },\n" +
                "  \"pets\": [\n" +
                "    {\n" +
                "      \"name\": \"Max\",\n" +
                "      \"species\": \"Dog\",\n" +
                "      \"age\": 5\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"Whiskers\",\n" +
                "      \"species\": \"Cat\",\n" +
                "      \"age\": 3\n" +
                "    }\n" +
                "  ],\n" +
                "  \"hobbies\": [\n" +
                "    \"Reading\",\n" +
                "    \"Traveling\",\n" +
                "    \"Photography\",\n" +
                "    \"Playing Guitar\"\n" +
                "  ],\n" +
                "  \"isMarried\": true,\n" +
                "  \"spouse\": null\n" +
                "}");

        System.out.println("jjsonObject = " + jjsonObject);

        final String encode = jjsonObject.encodeFormatter();

        System.out.println("encode = " + encode);

    }
}
