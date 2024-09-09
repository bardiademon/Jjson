import com.bardiademon.Jjson.JjsonArray.JjsonArray;
import com.bardiademon.Jjson.JjsonObject.JjsonObject;
import com.bardiademon.Jjson.converter.clazz.JjsonInclude;
import com.bardiademon.Jjson.converter.clazz.JjsonProperty;
import com.bardiademon.Jjson.exception.JjsonException;

public class TestString5 {
    public static void main(String[] args) throws JjsonException {

        String text = """
                \\\\" " "  " " "
                """;

        Message message = new Message(text);

        JjsonObject text1 = JjsonObject.create().put("text", text);

        System.out.println("text1 = " + text1.encodeFormatter());

        System.out.println("text1.getJjsonString(\"text\").toPlainText() = " + text1.getJjsonString("text").toPlainText());

        System.out.println("JjsonObject.ofClass(message) = " + JjsonObject.ofClass(message));

    }

    @JjsonInclude(mode = JjsonInclude.SerializationMode.METHODS_ONLY)
    public record Message(String text) {

        @JjsonProperty
        @Override
        public String text() {
            return text;
        }
    }
}
