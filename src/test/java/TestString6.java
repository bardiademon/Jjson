import com.bardiademon.Jjson.JjsonObject.JjsonObject;
import com.bardiademon.Jjson.exception.JjsonException;

public class TestString6 {
    public static void main(String[] args) throws JjsonException {

        final String joString = "{\"message_data\":[{\"type\":\"TEXT\",\"content\":\"سلام\\n\\n\"}]}";

        System.out.println("JjsonObject.ofString(joString) = " + JjsonObject.ofString(joString));

    }
}
