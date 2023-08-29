import com.bardiademon.Jjson.JjsonObject.JjsonObject;
import com.bardiademon.Jjson.data.exception.JjsonException;

public class JjsonTest4 {
    public static void main(String[] args) throws JjsonException {


        JjsonObject jjsonObject = JjsonObject.fromString("{\n" +
                "  \"name\": \"John Doe\",\n" +
                "  \"age\": 30,\n" +
                "  \"email\": \"johndoe@example.com\",\n" +
                "  \"address\": {\n" +
                "    \"street\": \"123 Main St\",\n" +
                "    \"city\": \"New York\",\n" +
                "    \"country\": \"USA\",\n" +
                "    \"coordinates\": {\n" +
                "      \"latitude\": 40.7128,\n" +
                "      \"longitude\": -74.0060\n" +
                "    }\n" +
                "  },\n" +
                "  \"interests\": [\n" +
                "    {\n" +
                "      \"category\": \"Sports\",\n" +
                "      \"activities\": [\n" +
                "        \"Football\",\n" +
                "        \"Basketball\",\n" +
                "        \"Tennis\"\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"category\": \"Music\",\n" +
                "      \"activities\": [\n" +
                "        {\n" +
                "          \"instrument\": \"Guitar\",\n" +
                "          \"genres\": [\"Rock\", \"Blues\"]\n" +
                "        },\n" +
                "        {\n" +
                "          \"instrument\": \"Piano\",\n" +
                "          \"genres\": [\"Classical\", \"Jazz\"]\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  ],\n" +
                "  \"education\": [\n" +
                "    {\n" +
                "      \"degree\": \"Bachelor of Science\",\n" +
                "      \"major\": \"Computer Science\",\n" +
                "      \"university\": {\n" +
                "        \"name\": \"XYZ University\",\n" +
                "        \"location\": {\n" +
                "          \"city\": \"New York\",\n" +
                "          \"country\": \"USA\"\n" +
                "        }\n" +
                "      },\n" +
                "      \"year\": 2015\n" +
                "    },\n" +
                "    {\n" +
                "      \"degree\": \"Master of Business Administration\",\n" +
                "      \"major\": \"Finance\",\n" +
                "      \"university\": {\n" +
                "        \"name\": \"ABC University\",\n" +
                "        \"location\": {\n" +
                "          \"city\": \"London\",\n" +
                "          \"country\": \"UK\"\n" +
                "        }\n" +
                "      },\n" +
                "      \"year\": 2018\n" +
                "    }\n" +
                "  ],\n" +
                "  \"projects\": [\n" +
                "    {\n" +
                "      \"title\": \"Project A\",\n" +
                "      \"description\": \"Lorem ipsum dolor sit amet, consectetur adipiscing elit.\",\n" +
                "      \"team\": [\n" +
                "        {\n" +
                "          \"name\": \"Adam\",\n" +
                "          \"role\": \"Developer\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"name\": \"Emily\",\n" +
                "          \"role\": \"Designer\"\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"title\": \"Project B\",\n" +
                "      \"description\": \"Praesent ac aliquet mauris. Fusce nec sem ac velit congue vulputate.\",\n" +
                "      \"team\": [\n" +
                "        {\n" +
                "          \"name\": \"Sarah\",\n" +
                "          \"role\": \"Developer\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"name\": \"Michael\",\n" +
                "          \"role\": \"QA\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"name\": \"Olivia\",\n" +
                "          \"role\": \"Project Manager\"\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "}");

        System.out.println(jjsonObject.encode());

    }
}
