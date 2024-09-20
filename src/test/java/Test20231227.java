import com.bardiademon.Jjson.object.JjsonObject;
import com.bardiademon.Jjson.converter.string.JjsonStringConverter;
import com.bardiademon.Jjson.exception.JjsonException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class Test20231227 {
    public static void main(String[] args) throws JjsonException, IOException {

        final String test = """
                {"name": "bardiademon"}
                """;

        System.out.println("new ObjectMapper().readTree(test) = " + new ObjectMapper().readTree(test));

        System.out.println("JjsonStringConverter.escapedByJackson(test) = " + JjsonStringConverter.escapedByJackson(test));

        final JjsonObject json = JjsonObject.ofString(test);

        final String text = """
                این یک متن چند خطی است.
                "این نقل قول نیازی به escape شدن ندارد."
                در این متن از کاراکترهای خاص مانند بک‌اسلش \\ هم استفاده نشده است.
                
                """;

        json.put("text", text);

        System.out.println(json.getOriginalString("text"));
        System.out.println(JjsonStringConverter.unescaped(json.getString("text")));

    }
}
