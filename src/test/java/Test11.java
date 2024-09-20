import com.bardiademon.Jjson.array.JjsonArray;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test11 {
    public static void main(String[] args) {

        final JjsonArray array = JjsonArray.create();


        final ExecutorService executorService = Executors.newCachedThreadPool();


        while (true) {
            executorService.execute(() -> array.put(44));
            executorService.execute(() -> array.encode());
        }


    }
}
