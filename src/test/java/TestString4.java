import com.bardiademon.Jjson.object.JjsonObject;
import com.bardiademon.Jjson.exception.JjsonException;

public class TestString4 {
    public static void main(String[] args) throws JjsonException {
        JjsonObject jsonStr = JjsonObject.ofString("""
                                {
                                    "name": "Bardia \\r\\n \\\n \s \f\n \\\\\\\\\\\\\\\\\\\n
                Namjoo
                
                "
                                }
                """);

        System.out.println("jsonStr = " + jsonStr);
        jsonStr.put("email", "bardia \\\\n \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\r namjoo\n@gmail.com");
        System.out.println("jsonStr = " + jsonStr);
        System.out.println("jsonStr.getString(\"email\") = " + jsonStr.getString("email"));
        final String plainTextEmail = jsonStr.getJjsonString("email").toPlainText();
        System.out.println("plainTextEmail = " + plainTextEmail);

    }
}
