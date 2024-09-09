import com.bardiademon.Jjson.JjsonArray.JjsonArray;
import com.bardiademon.Jjson.JjsonObject.JjsonObject;
import com.bardiademon.Jjson.exception.JjsonException;
import com.bardiademon.Jjson.util.Logger;

import java.io.IOException;

public class TestJsonL {
    public static void main(String[] args) throws IOException, JjsonException {
        Logger.enableLog(true, true);

        final JjsonArray jjsonArray = JjsonArray.createJsonL();
        jjsonArray.put(new JjsonObject().put("name", "bardiademon"));
        jjsonArray.put(new JjsonObject().put("email", "bardiademon@gmail.com"));
        jjsonArray.put("bardiademon@gmail.com");

        System.out.println("jjsonArray.encode() = " + jjsonArray.encode());
        System.out.println("jjsonArray.encodeJsonL() = " + jjsonArray.encodeJsonL());

        System.out.println("----------------------------------------------------------");
        Logger.enableLog(false, false);

        final JjsonArray jjsonArray2 = JjsonArray.create();
        jjsonArray2.put(new JjsonObject().put("name", "bardiademon"));
        jjsonArray2.put(new JjsonObject().put("email", "bardiademon@gmail.com"));
        jjsonArray2.put("bardiademon@gmail.com");

        System.out.println("jjsonArray2.isJsonL() = " + jjsonArray2.setJsonL(true));
        System.out.println("jjsonArray2.isJsonL() = " + jjsonArray2.isJsonL());
        System.out.println("jjsonArray.encode() = " + jjsonArray2.encode());
        try {
            System.out.println("jjsonArray.encodeJsonL() = " + jjsonArray2.encodeJsonL());
        } catch (JjsonException e) {
            e.printStackTrace(System.out);
        }

        System.out.println("----------------------------------------------------------");
        Logger.enableLog(true, true);

        final JjsonArray jjsonArray3 = JjsonArray.create();
        jjsonArray3.put(new JjsonObject().put("name", "bardiademon"));
        jjsonArray3.put(new JjsonObject().put("email", "bardiademon@gmail.com"));

        System.out.println("jjsonArray3.isJsonL() = " + jjsonArray3.setJsonL(true));
        System.out.println("jjsonArray3.isJsonL() = " + jjsonArray3.isJsonL());
        System.out.println("jjsonArray3.encode() = " + jjsonArray3.encode());
        System.out.println("jjsonArray3.encodeJsonL() = " + jjsonArray3.encodeJsonL());

        System.out.println("----------------------------------------------------------");
        Logger.enableLog(false, false);

        final JjsonArray jjsonArray4 = JjsonArray.create();
        jjsonArray4.put(new JjsonObject().put("name", "bardiademon"));
        jjsonArray4.put(new JjsonObject().put("email", "bardiademon@gmail.com"));

        jjsonArray4.write("test.json", true);
        jjsonArray4.writeJjsonL("test.jsonl", true);
        System.out.println("----------------------------------------------------------");
        Logger.enableLog(false, false);

        final JjsonArray jjsonArray5 = JjsonArray.ofJsonLFile("test.jsonl");
        System.out.println("jjsonArray5.isJsonL() = " + jjsonArray5.isJsonL());
        System.out.println("jjsonArray5.isJsonL() = " + jjsonArray5.setJsonL(true));

        jjsonArray5.put(new JjsonObject().put("test", JjsonArray.ofArray(new int[]{485, 4514, 44, 44451441, 54, 84, 515})));
        jjsonArray5.write("test2.json", true);
        jjsonArray5.writeJjsonL("test2.jsonl", true);
        System.out.println("jjsonArray5 = " + jjsonArray5);
        System.out.println("----------------------------------------------------------");
        Logger.enableLog(false, false);

        final JjsonArray jjsonArray6 = JjsonArray.ofJsonLFile("test2.jsonl");

        jjsonArray6.put(new JjsonObject().put("test", JjsonArray.ofArray(new int[]{485, 4514, 44, 44451441, 54, 84, 515})));
        jjsonArray6.write("test3.json", true);
        jjsonArray6.writeJjsonL("test3.jsonl", true);
        System.out.println("jjsonArray5 = " + jjsonArray6);

        Logger.enableLog(false, false);
    }
}
