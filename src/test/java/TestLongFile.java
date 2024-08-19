import com.bardiademon.Jjson.JjsonArray.JjsonArray;
import com.bardiademon.Jjson.JjsonObject.JjsonObject;
import com.bardiademon.Jjson.data.exception.JjsonException;
import com.bardiademon.Jjson.util.Logger;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class TestLongFile {

    public static void main(String[] args) throws JjsonException {

        System.out.println("Integer.MAX_VALUE = " + Integer.MAX_VALUE);

        final String joInfo = JjsonObject.create()
                .put("name", "Bardia")
                .put("family", "Namjoo")
                .put("datebrith", 1375)
                .put("programmer", true).encode();

        final ByteBuffer byteBuffer = ByteBuffer.allocate(710000000);

        for (int i = 0, len = 1000; i < len; i++) {
            byteBuffer.put(joInfo.getBytes(StandardCharsets.UTF_8));
            if (i + 1 < len) byteBuffer.put(",".getBytes(StandardCharsets.UTF_8));
        }

        final int len = byteBuffer.position();

        byteBuffer.flip();

        byte[] buffer = new byte[len];
        byteBuffer.get(buffer);

        final String str = "[" + new String(buffer, StandardCharsets.UTF_8) + "]";

        Logger.enableLog(false, false);

        System.out.println("10 = " + 10);

        JjsonArray json = JjsonArray.ofString(str);

    }

}
