import com.bardiademon.Jjson.JjsonObject.JjsonObject;
import com.bardiademon.Jjson.exception.JjsonException;

public class TestString3 {
    public static void main(String[] args) throws JjsonException {

        final String joStr = """
                {"name": " \\"Bardia\\" Namjoo"}
                """;

        final JjsonObject jjsonObject = JjsonObject.ofString(joStr);

        System.out.println("JjsonObject.ofString(joStr) = " + jjsonObject);
        System.out.println("jjsonObject.getOriginalString(\"name\") = " + jjsonObject.getOriginalString("name"));

    }
}
