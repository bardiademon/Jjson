import com.bardiademon.Jjson.JjsonArray.JjsonArray;
import com.bardiademon.Jjson.JjsonObject.JjsonObject;
import com.bardiademon.Jjson.data.exception.JjsonException;

public class TestOfString3 {
    public static void main(String[] args) throws JjsonException {

        final String joString = """
                {\"message_data\":[{\"type\":\"TE\\"XT\",\"content\":\"سلام خوبی؟\"}]}
                """;

        final JjsonObject jjsonObject = JjsonObject.ofString(joString);

        System.out.println("jjsonObject.encode() = " + jjsonObject.encode());

        final String joArrString = """
                [\"test\"]
                """;
        final JjsonArray array = JjsonArray.ofString(joArrString);

        System.out.println("array = " + array);
    }
}
