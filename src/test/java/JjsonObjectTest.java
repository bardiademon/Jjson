import com.bardiademon.Jjson.JjsonObject.JjsonObject;
import com.bardiademon.Jjson.data.exception.JjsonException;

public class JjsonObjectTest {
    public static void main(String[] args) {

        final JjsonObject jjsonObject = new JjsonObject();
        jjsonObject.put("id", "bardiademon");
        jjsonObject.put("name", "Bardia Namjoo");
        jjsonObject.put("programmer", true);
        jjsonObject.put("age", 27);

        final var encode = jjsonObject.encode();
        System.out.println("Encode: " + encode);

        final var encodeFormatter = jjsonObject.encodeFormatter();
        System.out.println("Encode formatter: " + encodeFormatter);

        try {
            final JjsonObject fromString = JjsonObject.fromString("{\n" +
                    "  \"id\": \"bardiademon\",\n" +
                    "  \"name\": \"Bardia Namjoo\",\n" +
                    "  \"programmer\": true,\n" +
                    "  \"age\": 27\n" +
                    "}");
        } catch (JjsonException e) {
            throw new RuntimeException(e);
        }

    }
}
