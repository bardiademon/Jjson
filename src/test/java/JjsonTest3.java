import com.bardiademon.Jjson.JjsonObject.JjsonObject;
import com.bardiademon.Jjson.data.exception.JjsonException;

public class JjsonTest3 {
    public static void main(String[] args) throws JjsonException {

        JjsonObject jjsonObject = JjsonObject.fromString("{\n" +
                "  \"person\": {\n" +
                "    \"name\": \"John Doe\",\n" +
                "    \"age\": 30,\n" +
                "    \"email\": \"johndoe@example.com\",\n" +
                "    \"address\": {\n" +
                "      \"street\": \"123 Main St\",\n" +
                "      \"city\": \"New York\",\n" +
                "      \"country\": \"USA\"\n" +
                "    },\n" +
                "    \"interests\": [\"programming\", \"reading\", \"traveling\"],\n" +
                "    \"education\": [\n" +
                "      {\n" +
                "        \"degree\": \"Bachelor of Science\",\n" +
                "        \"major\": \"Computer Science\",\n" +
                "        \"university\": \"XYZ University\",\n" +
                "        \"year\": 2015\n" +
                "      },\n" +
                "      {\n" +
                "        \"degree\": \"Master of Business Administration\",\n" +
                "        \"major\": \"Finance\",\n" +
                "        \"university\": \"ABC University\",\n" +
                "        \"year\": 2018\n" +
                "      }\n" +
                "    ],\n" +
                "    \"projects\": [\n" +
                "      {\n" +
                "        \"title\": \"Project A\",\n" +
                "        \"description\": \"Lorem ipsum dolor sit amet, consectetur adipiscing elit.\",\n" +
                "        \"year\": 2020\n" +
                "      },\n" +
                "      {\n" +
                "        \"title\": \"Project B\",\n" +
                "        \"description\": \"Praesent ac aliquet mauris. Fusce nec sem ac velit congue vulputate.\",\n" +
                "        \"year\": 2021\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}");

        System.out.println("jjsonObject.encode() = " + jjsonObject.encode());


    }
}
