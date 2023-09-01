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
        <url>https://bardiademon.com/repository</url>
    </repository>
</repositories>
```

* Step 2. Add the dependency

```xml
<dependency>
    <groupId>com.bardiademon</groupId>
    <artifactId>Jjson</artifactId>
    <version>1.0.0</version>
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
            final JjsonObject fromString = JjsonObject.fromString("{\"id\":\"bardiademon\",\"name\":\"Bardia Namjoo\",\"programmer\":true,\"age\":27}");
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
            final JjsonObject fromString = JjsonObject.fromString("{\n" +
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

        final var jjsonArray = JjsonArray.fromString("""
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
        builder.put(JjsonObject.fromJjsonObject(getter.getJjsonObject(8)));
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
