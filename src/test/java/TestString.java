import com.bardiademon.Jjson.JjsonArray.JjsonArray;
import com.bardiademon.Jjson.JjsonObject.JjsonObject;
import com.bardiademon.Jjson.converter.JjsonObjectConverter;

public class TestString {
    public static void main(String[] args) {

        final String text = """
                   خط اول با کاراکتر \\ درون آن
                   خط دوم با کاراکتر \\ درون آن
                   خط سوم بدون کاراکتر خاص
                """.trim();

        final String stringFormatter = JjsonObjectConverter.converter().stringFormatter(text);
        System.out.println("stringFormatter = " + stringFormatter);

        final JjsonObject jjsonObject = JjsonObject.create().put("text", text);

        System.out.println("jjsonObject.getString(\"text\") = " + jjsonObject.getString("text"));

        System.out.println("jjsonObject.encode() = " + jjsonObject.encode());

        jjsonObject.put("text2", "text2");

        System.out.println("jjsonObject.getRealString(\"text\") = " + jjsonObject.getRealString("text2", "text2\n"));


        final JjsonArray array = JjsonArray.create().put(text);
        System.out.println("array.getString(0) = " + array.getString(0));
        System.out.println("array.encode() = " + array.encode());
        System.out.println("array.encodeFormatter() = " + array.encodeFormatter());
        System.out.println("array.getRealString(0) = " + array.getRealString(0));

    }
}
