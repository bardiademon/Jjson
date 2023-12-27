import com.bardiademon.Jjson.JjsonObject.JjsonObject;
import com.bardiademon.Jjson.data.exception.JjsonException;

import java.io.IOException;

public class Test20231227 {
    public static void main(String[] args) throws JjsonException, IOException {

        final String test = "{\"name\": \"\n\nbardia\n\n\ndemon\"}";

        final JjsonObject json = JjsonObject.ofString(test);

        System.out.println(json.getString("name"));

    }
}
