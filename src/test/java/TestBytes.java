import com.bardiademon.Jjson.JjsonArray.JjsonArray;
import com.bardiademon.Jjson.JjsonObject.JjsonObject;
import com.bardiademon.Jjson.converter.clazz.JjsonInclude;
import com.bardiademon.Jjson.converter.clazz.JjsonProperty;
import com.bardiademon.Jjson.data.exception.JjsonException;

import java.util.Arrays;

public class TestBytes {
    public static void main(String[] args) {

        JjsonArray jjsonArray = JjsonArray.create().put(new byte[]{44, 54, 8, 48, 5, 7, 4, 5, 8, 56, 4, 1, 4});
        System.out.println("jjsonArray = " + jjsonArray);
        final byte[] jjsonArrayBytes = jjsonArray.getBytes(0);
        System.out.println("bytes = " + Arrays.toString(jjsonArrayBytes));

        JjsonObject jjsonObject = JjsonObject.create().put("bytes", new byte[]{4, 5, 9, 85, 4, 69, 84, 63, 98, 6});
        System.out.println("put = " + jjsonObject);
        final byte[] jjsonObjectBytes = jjsonObject.getBytes("bytes");
        System.out.println("bytes = " + Arrays.toString(jjsonObjectBytes));

        try {
            JjsonObject jjsonObject1 = JjsonObject.ofClass(new TestBytesClass(jjsonArrayBytes, new Byte[]{4, 65, 96, 9, 6, 8, 96}));
            System.out.println("jjsonObject1 = " + jjsonObject1);

            JjsonObject jjsonObject2 = JjsonObject.ofString(jjsonObject1.encode());
            byte[] bytes = jjsonObject2.getBytes("methodBytes");
            System.out.println("bytes = " + Arrays.toString(bytes));

        } catch (JjsonException e) {
            throw new RuntimeException(e);
        }

    }

    @JjsonInclude(mode = JjsonInclude.SerializationMode.FIELDS_AND_METHODS)
    public static class TestBytesClass {

        public byte[] varBytes;

        @JjsonProperty(nullable = false, defaultValueMethodName = "defaultMethodBytes")
        public Byte[] nullVarBytes;

        public TestBytesClass(byte[] varBytes, Byte[] nullVarBytes) {
            this.varBytes = varBytes;
            this.nullVarBytes = nullVarBytes;
        }

        @JjsonProperty
        public byte[] methodBytes() {
            return new byte[]{54, 98, 9, 5, 4, 7, 5, 6, 9, 5, 3, 5, 8};
        }

        public Byte[] defaultMethodBytes() {
            return new Byte[]{4, 8, 9, 63, 8, 6, 9, 5, 4, 8,};
        }

    }

}
