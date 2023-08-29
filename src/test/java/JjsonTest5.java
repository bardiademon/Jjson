import com.bardiademon.Jjson.JjsonObject.JjsonObject;
import com.bardiademon.Jjson.data.exception.JjsonException;
import com.bardiademon.Jjson.util.Logger;

public class JjsonTest5 {
    public static void main(String[] args) throws JjsonException {

        Logger.disableLog(true);

        final JjsonObject jjsonObject = JjsonObject.fromString("""
                {
                  "nam\re": "bardiademon\n\n",
                  "score": -45.6,
                  "test": "Hi, I'm bardiademon,\t My name is \\"Bardia Namjoo\\" "
                }
                """);


        String string = jjsonObject.getString("nam\re");
//
        System.out.println(jjsonObject.encodeFormatter());
        System.out.println("string = " + string);

        System.out.println("jjsonObject.getString(\"test\") = " + jjsonObject.getString("test"));

        System.out.println("jjsonObject.keys() = " + jjsonObject.keys());
    }
}
