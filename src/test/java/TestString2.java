import com.bardiademon.Jjson.JjsonObject.JjsonObject;
import com.bardiademon.Jjson.converter.JjsonObjectConverter;
import com.bardiademon.Jjson.exception.JjsonException;

import java.io.IOException;

public class TestString2 {

    public static void main(String[] args) throws JjsonException, IOException {

        JjsonObject jsonStr = JjsonObject.ofString("""
                                {
                                    "name": "Bardia \\r\\n \\\n \s \f\n \\\\\\\\\\\\\\\\\\\n
                Namjoo
                
                "
                                }
                """);

        System.out.println("jsonStr = " + jsonStr);
        jsonStr.put("email", "bardia \\\\n \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\r namjoo\n@gmail.com");

        System.out.println("jsonStr.getOriginalString(\"name\") = " + jsonStr.getOriginalString("name"));
        System.out.println("jsonStr.getOriginalString(\"name\") = " + jsonStr.getJjsonString("name").toPlainText());
        System.out.println("jsonStr.getOriginalString(\"email\") = " + jsonStr.getOriginalString("email"));

        String json = jsonStr.encode();

        System.out.println("JjsonObject.ofString(json) = " + JjsonObject.ofString(json));

        for (int i = 1; i <= 100; i++) {
            System.out.printf("Json: %s , I: %s\n", jsonStr, i);
            final JjsonObject jjsonObject = JjsonObject.ofString(json);
            System.out.println("------------------------------------------------------");
            System.out.println("\n" + jjsonObject.getOriginalString("name"));
            System.out.println("\n" + jjsonObject.getString("email"));
            System.out.println("------------------------------------------------------");
            jjsonObject.write("test.json", true, true);
            json = jjsonObject.encode();
        }
        System.out.println("jsonStr = " + jsonStr);

    }

}
