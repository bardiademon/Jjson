import com.bardiademon.Jjson.object.JjsonObject;
import com.bardiademon.Jjson.exception.JjsonException;
import com.bardiademon.Jjson.util.Logger;

public class TestOfString2 {
    public static void main(String[] args) throws JjsonException {

        Logger.enableLog(true, true);

        final String message = """
                response.body() = {
                                     "error": {
                                         "message": "Cannot specify both model and engine",
                                         "type": "invalid_request_error",
                                         "param": null,
                                         "code": null
                                     }
                                 }
                """;

        final String json = """
                {
                                     "error": {
                                         "message": "Cannot specify both model and engine",
                                         "type": "invalid_request_error",
                                         "param": null,
                                         "code": null
                                     }
                                 }
                """;

        System.out.println("JjsonObject.ofString(json) = " + JjsonObject.ofString(json));

        final JjsonObject jjsonObject = new JjsonObject().put("message", message);

        System.out.println("jjsonObject.getString(\"message\") = " + jjsonObject.getString("message"));

        System.out.println("jjsonObject = " + jjsonObject);

        final String joMessage = """
                {"message":"response.body() = {
                                     "error": {
                                         "message": "Cannot specify both model and engine",
                                         "type": "invalid_request_error",
                                         "param": null,
                                         "code": null
                                     }
                                 }
                "}
                """;

        Logger.enableLog(false, false);

    }
}
