import com.bardiademon.Jjson.JjsonArray.JjsonArray;
import com.bardiademon.Jjson.JjsonArray.JjsonArrayPut;
import com.bardiademon.Jjson.JjsonArray.JjsonArrayGetter;
import com.bardiademon.Jjson.JjsonObject.JjsonObject;
import com.bardiademon.Jjson.encoder.JjsonEncoder;
import com.bardiademon.Jjson.exception.JjsonException;
import com.bardiademon.Jjson.util.Logger;

public class JjsonArrayTest {
    public static void main(String[] args) throws JjsonException {

        Logger.enableLog(true, true);

        final var jjsonArray = JjsonArray.ofString("""
                [1,5,4.56,"Hi, I'm bardiademon\tJava Programmer",true,null,{},[],{"name":"bardiademon"},["Bardia Namjoo"],[{}],[[{}]],{"test":[{}]}]
                """);

        final var getter = (JjsonArrayGetter) jjsonArray;
        final var builder = (JjsonArrayPut) jjsonArray;
        final var encoder = (JjsonEncoder) jjsonArray;

        System.out.println(jjsonArray.encode());

        final var number = jjsonArray.getInteger(-1, 5);
        System.out.println("number.intValue() = " + number);

        builder.put(5);
        builder.put("New String");
        builder.put(JjsonObject.ofJjsonObject(getter.getJjsonObject(8)));
        builder.put(0, getter.getInteger(0) + getter.getInteger(0));
        builder.put(8, getter.getJjsonObject(8).getString("name"));

        System.out.println(encoder.encode());

        final String string = getter.getString(5, "DEFAULT VALUE");
        System.out.println("string = " + string);

        final Boolean aBoolean = getter.getBoolean(4);
        System.out.println("aBoolean = " + aBoolean);


    }
}
