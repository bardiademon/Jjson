import com.bardiademon.Jjson.JjsonObject.JjsonObject;
import com.bardiademon.Jjson.data.exception.JjsonException;

public class JjsonTest6 {
    public static void main(String[] args) throws JjsonException {

        final JjsonObject bardiademon = JjsonObject.ofFile("example/test-json-object.json");
        System.out.println("bardiademon.encode() = " + bardiademon.encode());
//        System.out.println("bardiademon.encodeFormatter() = " + bardiademon.encodeFormatter());

        System.out.printf("ID: %s\n", bardiademon.getString("ID"));

        System.out.printf("Phone: %d\n", bardiademon.getLong("phone"));
        System.out.printf("Phone: %s\n", bardiademon.asString("phone"));

        System.out.printf("Telegram ID: %s\n", bardiademon.getJjsonArray("contact").getJjsonObject(0).getString("id"));
        System.out.printf("Instagram ID: %s\n", bardiademon.getJjsonArray("contact").getJjsonObject(1).getString("id"));


        final JjsonObject bardia2 = JjsonObject.ofJjsonObject(bardiademon);
        System.out.println("bardia2.encode() = " + bardia2.encode());
    }
}
