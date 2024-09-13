import com.bardiademon.Jjson.JjsonArray.JjsonArray;
import com.bardiademon.Jjson.converter.clazz.JjsonClass;
import com.bardiademon.Jjson.converter.clazz.JjsonInclude;
import com.bardiademon.Jjson.converter.clazz.JjsonProperty;
import com.bardiademon.Jjson.exception.JjsonException;
import com.bardiademon.Jjson.util.Logger;

import java.util.List;
import java.util.Locale;

public class TestToClass2 {
    public static void main(String[] args) throws JjsonException {

        Logger.enableLog(true, true);

        final List<AiMessageModel> messages = List.of(
                new AiMessageModel(AIMessageRole.SYSTEM, "SYSTEM ROLE"),
                new AiMessageModel(AIMessageRole.USER, "USER ROLE"),
                new AiMessageModel(AIMessageRole.ASSISTANT, "ASSISTANT ROLE")
        );

        final JjsonArray joMessage = JjsonArray.ofClass(messages);


        System.out.println(joMessage.encodeFormatter());

    }

    @JjsonInclude(mode = JjsonInclude.SerializationMode.METHODS_ONLY)
    public record AiMessageModel(AIMessageRole role, String content) {

        @Override
        @JjsonProperty
        public AIMessageRole role() {
            return role;
        }

        @Override
        @JjsonProperty
        public String content() {
            return content;
        }


    }

    public enum AIMessageRole implements JjsonClass<Integer> {
        SYSTEM, USER, ASSISTANT
        //
        ;


        public String getRole() {
            return name().toLowerCase(Locale.ROOT);
        }

        @Override
        public String toString() {
            return getRole();
        }

        @Override
        public Integer jsonValue() {
            return 0;
        }
    }
}
