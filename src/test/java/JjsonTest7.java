import com.bardiademon.Jjson.JjsonObject.JjsonObject;
import com.bardiademon.Jjson.data.exception.JjsonException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class JjsonTest7 {
    public static void main(String[] args) throws JjsonException, FileNotFoundException {

        final JjsonObject bardiademon = JjsonObject.ofStream(new FileInputStream("example/test-json-object.json"));
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
