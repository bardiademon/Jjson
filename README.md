# JavaJson(Jjson)

It is a library for managing, generating and working with Json, which is built without using any additional libraries

Java version
-----

This library was built for Java 17, the latest LTS. 
[Click to download](https://dev.java)

Maven
-----

* Step 1. Add the bardiademon-repository to your build file

```xml
<repositories>
    <repository>
        <id>bardiademon-repository</id>
        <url>https://repository.bardiademon.com/</url>
    </repository>
</repositories>
```

* Step 2. Add the dependency

```xml
<dependency>
    <groupId>com.bardiademon</groupId>
    <artifactId>Jjson</artifactId>
    <version>2.1.0</version>
</dependency>
```

Usage
-----

Creating an object from the JjsonObject class and putting values into it

```java
public class JjsonObjectTest {
    public static void main(String[] args) {

        final JjsonObject jjsonObject = new JjsonObject();
        jjsonObject.put("id", "bardiademon");
        jjsonObject.put("name", "Bardia Namjoo");
        jjsonObject.put("programmer", true);
        jjsonObject.put("age", 27);

    }
}
```

Format

```java

public class JjsonObjectTest {
    public static void main(String[] args) {

        final JjsonObject jjsonObject = new JjsonObject();
        jjsonObject.put("id", "bardiademon");
        jjsonObject.put("name", "Bardia Namjoo");
        jjsonObject.put("programmer", true);
        jjsonObject.put("age", 27);

        final String encode = jjsonObject.encode();
        System.out.println(encode);

        final String encodeFormatter = jjsonObject.encodeFormatter();
        System.out.println(encodeFormatter);
    }
}
```

output

```json
{"id": "bardiademon","name": "Bardia Namjoo","programmer": true,"age": 27}
```

```json
{
  "id": "bardiademon",
  "name": "Bardia Namjoo",
  "programmer": true,
  "age": 27
}
```

Creating an object of the JjsonObject class with a String of Json
-----

```java
public class JjsonObjectTest {
    public static void main(String[] args) {

        try {
            final JjsonObject ofString = JjsonObject.ofString("{\"id\":\"bardiademon\",\"name\":\"Bardia Namjoo\",\"programmer\":true,\"age\":27}");
        } catch (JjsonException e) {
            throw new RuntimeException(e);
        }

    }
}
```

```java
public class JjsonObjectTest {
    public static void main(String[] args) {

        try {
            final JjsonObject ofString = JjsonObject.ofString("{\n" +
                    "  \"id\": \"bardiademon\",\n" +
                    "  \"name\": \"Bardia Namjoo\",\n" +
                    "  \"programmer\": true,\n" +
                    "  \"age\": 27\n" +
                    "}");
        } catch (JjsonException e) {
            throw new RuntimeException(e);
        }

    }
}
```

Working with JjsonArray is the same way
-----

```java
public class JjsonArrayTest {
    public static void main(String[] args) throws JjsonException {

        Logger.disableLog(false);

        final var jjsonArray = JjsonArray.ofString("""
                [1,5,4.56,"Hi, I'm bardiademon\tJava Programmer",true,null,{},[],{"name":"bardiademon"},["Bardia Namjoo"],[{}],[[{}]],{"test":[{}]}]
                """);

        final var getter = (JjsonArrayGetter) jjsonArray;
        final var builder = (JjsonArrayBuilder) jjsonArray;
        final var encoder = (JjsonEncoder) jjsonArray;

        System.out.println(jjsonArray.encode());

        final var number = jjsonArray.getInteger(-1, 5);
        System.out.println("number.intValue() = " + number);

        builder.put(5);
        builder.put("New String");
        builder.put(JjsonObject.ofJjsonObject(getter.getJjsonObject(8)));
        builder.put(0, getter.getInteger(0) + getter.getInteger(0));
        builder.put(8, getter.getJjsonObject(8).getString("name"));

        System.out.println(encoder.encode());

        final String string = getter.getString(5, "DEFAULT VALUE");
        System.out.println("string = " + string);

        final Boolean aBoolean = getter.getBoolean(4);
        System.out.println("aBoolean = " + aBoolean);


    }
}
```

output

```json
[1,5,4.56,"Hi, I'm bardiademon	Java Programmer",true,null,{},[],{"name":"bardiademon"},["Bardia Namjoo"],[{}],[[{}]],{"test":[{}]}]
```
```text
number.intValue() = 5
```
```json
[2,5,4.56,"Hi, I'm bardiademon	Java Programmer",true,null,{},[],"bardiademon",["Bardia Namjoo"],[{}],[[{}]],{"test":[{}]},5,"New String",{"name":"bardiademon"}]
```
```text
string = DEFAULT VALUE
aBoolean = true
```

Json of File/Stream
-----

```java
public class JjsonTest {
    public static void main(String[] args) throws JjsonException, IOException {
        JjsonArray.ofFile("path");
        JjsonObject.ofFile("path");

        JjsonArray.ofStream(new FileInputStream("path"));
        JjsonObject.ofStream(new FileInputStream("path"));
    }
}
```

Json Write to file
-----

```java
public class JjsonTest {
    public static void main(String[] args) throws JjsonException, IOException {
        final JjsonObject jjsonObject = new JjsonObject();
        jjsonObject.put("id", "bardiademon");
        jjsonObject.put("name", "Bardia Namjoo");
        jjsonObject.put("programmer", true);
        jjsonObject.put("age", 27);

        jjsonObject.write("path");

        JjsonArray.create()
                .put(5)
                .put(3)
                .put(8)
                .write("path");
    }
}
```

Json Of Array/Collection/Map
-----

```java
import com.bardiademon.Jjson.JjsonArray.JjsonArray;
import com.bardiademon.Jjson.JjsonObject.JjsonObject;

import java.util.List;

public class JjsonTest {
    public static void main(String[] args) throws JjsonException, IOException {

        JjsonArray.ofCollection(Set.of(5L, 87L, 2L, 3L, 1L, 6L, 465L, 4L, 89L));
        JjsonArray.ofCollection(List.of(5L, 87L, 2L, 3L, 1L, 6L, 465L, 4L, 89L));
        JjsonArray.ofArray(new int[]{1, 23, 63, 54, 6, 755});
        JjsonArray.ofArray(new short[]{1, 23, 63, 54, 6, 755});
        JjsonArray.ofArray(new float[]{1.5F, 5, 4});

        final HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("id", "bardiademon");
        hashMap.put("firstname", "Bardia");
        hashMap.put("lastname", "Namjoo");
        
        JjsonObject.ofMap(hashMap);
    }
}
```

<h1 align="center">
    💻 Technologies
</h1>

<p align="center">
  <a href="https://dev.java">
    <img src="https://skillicons.dev/icons?i=java"  alt="Java"/>
  </a>
</p>

<h1 align="center">
    🌟 Spread the word!
</h1>

If you want to say thank you:

- Add a GitHub Star to the project!
- Follow my GitHub [bardiademon](https://github.com/bardiademon)

<h1 align="center">
    ⚠️ License & 📝 Credits
</h1>

by bardiademon [https://bardiademon.com](https://www.bardiademon.com)
