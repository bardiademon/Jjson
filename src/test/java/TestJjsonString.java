import com.bardiademon.Jjson.converter.string.JjsonStringConverter;
import com.bardiademon.Jjson.data.model.JjsonString;

public class TestJjsonString {
    public static void main(String[] args) {

        final JjsonString testText = JjsonStringConverter.escapedByJackson("Test =\"\n\b Text");

        System.out.println("testText.escaped() = " + testText.escaped());
        System.out.println("testText.original() = " + testText.original());
        System.out.println("testText.toPlainText() = " + testText.toPlainText());

        System.out.println("testText = " + testText);

    }
}
