import com.bardiademon.Jjson.JjsonArray.JjsonArray;
import com.bardiademon.Jjson.JjsonObject.JjsonObject;
import com.bardiademon.Jjson.converter.clazz.JjsonClass;
import com.bardiademon.Jjson.data.exception.JjsonException;
import com.bardiademon.Jjson.util.Logger;

public class Test12 {

    public static void main(String[] args) throws JjsonException {

        final String msg = """
                {
                       "name": null,
                       "email": null
                }
                """;
        Logger.disableLog(false);

        JjsonObject jjsonObject = JjsonObject.create().put("name", 645);

        String name = jjsonObject.asString("name");

        System.out.println("name = " + name);

        jjsonObject.put("name", null);

        System.out.println("jjsonObject = " + jjsonObject);


        JjsonArray put = JjsonArray.create().put(null);

        System.out.println("put.encode() = " + put.encode());

        put.put(0, 4545);

        System.out.println("put = " + put);

        System.out.println("JjsonObject.ofString(msg).encode() = " + JjsonObject.ofString(msg).encode());


        jjsonObject.put("info", new Info("bardiademon", "bardiademon@gmail.com"));
        jjsonObject.put("aChar", 'd');

        System.out.println("jjsonObject.encode() = " + jjsonObject.encode());

    }


    private record Info(String name, String email) implements JjsonClass {

        @Override
        public Object jsonValue() {
            return JjsonObject.create().put("name", name).put("email", email);
        }
    }
}
