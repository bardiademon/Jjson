import com.bardiademon.Jjson.JjsonArray.JjsonArray;
import com.bardiademon.Jjson.JjsonObject.JjsonObject;
import com.bardiademon.Jjson.converter.clazz.JjsonInclude;
import com.bardiademon.Jjson.converter.clazz.JjsonProperty;
import com.bardiademon.Jjson.data.exception.JjsonException;

public class TestJsonClass {
    public static void main(String[] args) throws JjsonException {

        JjsonObject jjsonObject = JjsonObject.ofClass(new Info("bardia", "namjoo"));
        System.out.println("jjsonObject = " + jjsonObject);

        final JjsonArray jsonInfo = new JjsonArray()
                .put(new Info("Bardia", "Namjoo"));

        System.out.println("jsonInfo.getObject(0) = " + jsonInfo.getObject(0));

        System.out.println("jsonInfo = " + jsonInfo);

        JjsonArray jjsonArray = JjsonArray.ofString("[{\"id\": 3, \"access\": \"TEXT\", \"created_at\": \"2024/08/21 17:02:25\"}]");
        System.out.println("jjsonArray = " + jjsonArray);

    }

    @JjsonInclude(mode = JjsonInclude.SerializationMode.METHODS_ONLY)
    public static class Info {
        private String name;
        private String family;

        public Info(String name, String family) {
            this.name = name;
            this.family = family;
        }

        @JjsonProperty(name = "name")
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @JjsonProperty(name = "family")
        public String getFamily() {
            return family;
        }

        public void setFamily(String family) {
            this.family = family;
        }


        @Override
        public String toString() {
            return "Info{" +
                    "name='" + name + '\'' +
                    ", family='" + family + '\'' +
                    '}';
        }
    }
}
