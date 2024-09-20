import com.bardiademon.Jjson.array.JjsonArray;
import com.bardiademon.Jjson.object.JjsonObject;
import com.bardiademon.Jjson.converter.clazz.JjsonClass;
import com.bardiademon.Jjson.exception.JjsonException;

public class TestAsString {

    public static void main(String[] args) throws JjsonException {

        final JjsonObject info = JjsonObject.create()
                .put("name", "Bardia Demon")
                .put("contacts", new Contacts(JjsonArray.create().put("Telegram").put("Whatsapp").put("Gmail")));


        final String contacts = info.asString("contacts");

        System.out.println("contacts = " + contacts);

        System.out.println("info.encode() = " + info.encode());

        final JjsonArray jjsonArray = JjsonArray.ofString("[\"Telegram\",\"Whatsapp\",\"Gmail\"]");
        System.out.println("jjsonArray = " + jjsonArray);
    }

    public static final class Contacts implements JjsonClass<JjsonArray> {
        private final JjsonArray array;

        public Contacts(final JjsonArray array) {
            this.array = array;
        }

        public JjsonArray jsonValue() {
            return array;
        }

        @Override
        public String toString() {
            return array.encode();
        }
    }

}
