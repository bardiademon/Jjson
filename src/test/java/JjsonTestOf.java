import com.bardiademon.Jjson.JjsonArray.JjsonArray;
import com.bardiademon.Jjson.JjsonObject.JjsonObject;
import com.bardiademon.Jjson.data.exception.JjsonException;

import java.io.IOException;
import java.util.*;

public class JjsonTestOf {
    public static void main(String[] args) throws JjsonException, IOException {

        final Set<Long> listSet = Set.of(5L, 87L, 2L, 3L, 1L, 6L, 465L, 4L, 89L);
        final List<Long> list = List.of(5L, 87L, 2L, 3L, 1L, 6L, 465L, 4L, 89L);

        System.out.println("JjsonArray.ofCollection(listSet) = " + JjsonArray.ofCollection(listSet));
        System.out.println("JjsonArray.ofCollection(list) = " + JjsonArray.ofCollection(list));

        final int[] arr = {1, 2, 5, 699, 8, 25, 66, 54};
        System.out.println("JjsonArray.ofArray(arr) = " + JjsonArray.ofArray(arr));

        final long[] arrLong = {87, 56489, 1, 456, 561, 2345, 56, 15};
        System.out.println("JjsonArray.ofArray(arr) = " + JjsonArray.ofArray(arrLong));

        final String[] arrString = {"Bardia", "Namjoo", "bardiademon", "bardiademon@gmail.com"};
        System.out.println("JjsonArray.ofArray(arr) = " + JjsonArray.ofArray(arrString));

        final short[] arrShort = {1, 5, 6, 98, 6, 54, 6666, 5, 6, 4, 6};
        System.out.println("JjsonArray.ofArray(arr) = " + JjsonArray.ofArray(arrShort));

        final LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("id", "bardiademon");
        map.put("firstname", "Bardia");
        map.put("lastname", "Namjoo");
        System.out.println("JjsonObject.ofMap(map) = " + JjsonObject.ofMap(map));

        final HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("id", "bardiademon");
        hashMap.put("firstname", "Bardia");
        hashMap.put("lastname", "Namjoo");
        hashMap.put(null, "Namjoo");
        hashMap.put("null", null);
        System.out.println("JjsonObject.ofMap(map) = " + JjsonObject.ofMap(hashMap));

        final List<Object> listObj = new ArrayList<>();
        listObj.add(1);
        listObj.add("bardia");
        listObj.add(new int[]{1, 2, 6, 65, 98, 5, 4, 56});
        listObj.add(new float[]{1.6F, 12.6F, 3.14F});
        listObj.add(new Object[]{new float[]{1.5F}, 5, 4});
        listObj.add(JjsonArray.create().put("45").putValue(JjsonObject.create().putValue("name", "bardia")));
        listObj.add(Set.of(1, 5, 6, "Bardia"));
        listObj.add(List.of(1, 56, 64, 6548, 6354, 6485, 3.14F, 45645L, List.of(1, 2, 3, 6), JjsonObject.ofMap(map)));

        final JjsonArray jjsonArray = JjsonArray.ofCollection(listObj);

        jjsonArray.putValue(new int[]{1, 32, 3, 6, 4, 69, 6, 4, 6});
        final JjsonArray arrInt = (JjsonArray) jjsonArray.getObject(8);
        System.out.println("arrInt = " + arrInt);

        jjsonArray.putValue(Map.of("Name", "Bardia"));
        jjsonArray.putValue(JjsonObject.create().putValue("id", "bardiademon").putValue("arr", new int[]{1, 2, 3, 65, 8}));

        jjsonArray.put(JjsonObject.ofMap(map));

        System.out.println("jjsonArray.encode() = " + jjsonArray.encode());

        jjsonArray.write("example/test-obj.json", true);


        final JjsonArray clone = jjsonArray.clone();
        System.out.println("clone.encode() = " + clone.encode());

    }
}
