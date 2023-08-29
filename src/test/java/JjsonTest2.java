import com.bardiademon.Jjson.JjsonObject.JjsonObject;
import com.bardiademon.Jjson.data.exception.JjsonException;

public class JjsonTest2 {
    public static void main(String[] args) throws JjsonException {

        final JjsonObject jjsonObject = JjsonObject.fromString("""
                {
                    "statusCode": 400,
                    "message": [
                        {
                            "field": "price.0",
                            "code": "VALIDATION_IS_NUMBER"
                        }
                    ],
                "test": [{"slug":"asd"}],
                "test1": [[{"slug":"slug"}]]
                }
                """);

        System.out.println("jjsonObject.encode() = " + jjsonObject.encode());

        final JjsonObject slug = jjsonObject.getJjsonArray("test1").getJjsonArray(0).getJjsonObject(0);
        System.out.println("slug.encode() = " + slug.encode());

        System.out.println(jjsonObject.encodeFormatter());
        System.out.println(slug.encodeFormatter());


    }
}
