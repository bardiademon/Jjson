import com.bardiademon.Jjson.JjsonArray.JjsonArray;
import com.bardiademon.Jjson.JjsonObject.JjsonObject;

import java.io.IOException;

public class JjsonTest9 {
    public static void main(String[] args) throws IOException {
        JjsonObject jjsonObject = JjsonObject.create();
        System.out.println("jjsonObject = " + jjsonObject);

        System.out.println("JjsonArray.create() = " + JjsonArray.create());

        final JjsonArray array = JjsonArray.create().put(4).put(5).put(8).put(12);

        System.out.println("array.encode() = " + array.encode());

//        array.write("example/array.json");
        array.write("example/array2.json", true, true);


        System.out.println("array.encodeFormatter() = " + array.encodeFormatter());

    }
}
