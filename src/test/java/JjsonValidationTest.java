import com.bardiademon.Jjson.util.JjsonValidation;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class JjsonValidationTest {
    public static void main(String[] args) throws FileNotFoundException {

        final boolean ofStringJsonArray = JjsonValidation.ofString("[]");
        final boolean ofStringJsonObject = JjsonValidation.ofString("{}");
        final boolean ofFile = JjsonValidation.ofFile("example/bardia2.json");
        final boolean ofStream = JjsonValidation.ofStream(new FileInputStream("example/bardia2.json"));

        final boolean ofString = JjsonValidation.ofString("[}");

        System.out.println("ofStringJsonObject = " + ofStringJsonObject);
        System.out.println("ofStringJsonArray = " + ofStringJsonArray);
        System.out.println("ofFile = " + ofFile);
        System.out.println("ofStream = " + ofStream);

        System.out.println("ofString = " + ofString);


    }
}
