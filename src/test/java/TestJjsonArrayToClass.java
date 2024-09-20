import com.bardiademon.Jjson.array.JjsonArray;
import com.bardiademon.Jjson.exception.JjsonException;

import java.util.ArrayList;
import java.util.List;

public class TestJjsonArrayToClass {
    public static void main(String[] args) throws JjsonException {

        final List<Object> test = new ArrayList<>();

        for (int i = 99; i >= 0; i--) {
            if (i % 2 == 0) {
                test.add(i);
            } else {
                test.add("an number " + i);
            }
        }


        final JjsonArray jjsonArray = JjsonArray.ofClass(test);


        System.out.println(jjsonArray.encodeFormatter());

    }
}
