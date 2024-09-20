import com.bardiademon.Jjson.array.JjsonArray;
import com.bardiademon.Jjson.exception.JjsonException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class TestJson {
    public static void main(String[] args) throws FileNotFoundException, JjsonException {

        JjsonArray companyInfo = JjsonArray.ofStream(new FileInputStream("D:\\programming\\project\\My\\Jjson\\src\\test\\java\\example.json"));

        System.out.println("companyInfo.size() = " + companyInfo.size());


        System.out.println("companyInfo.encode() = " + companyInfo.encode());


    }
}
