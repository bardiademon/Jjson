import com.bardiademon.Jjson.JjsonArray.JjsonArray;
import com.bardiademon.Jjson.JjsonObject.JjsonObject;
import com.bardiademon.Jjson.converter.clazz.JjsonClass;
import com.bardiademon.Jjson.converter.clazz.JjsonInclude;
import com.bardiademon.Jjson.converter.clazz.JjsonProperty;
import com.bardiademon.Jjson.exception.JjsonException;
import com.bardiademon.Jjson.util.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassToJsonTest {
    public static void main(String[] args) throws JjsonException, IOException {

        Logger.enableLog(true, true);
        try {
            Logger.setPath("log.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        final Integer[] arr = new Integer[]{};

        System.out.println("arr.getClass().isArray() = " + arr.getClass().isArray());

        final Info info = new Info("bardia2", "email2", null, new Long[]{9177257149L});

        JjsonObject jjsonObject = JjsonObject.ofClass(new Info("bardiademon", "bardiademon@gmail.com", info, new Long[]{9177257149L}));
        JjsonArray jjsonArray = JjsonArray.ofClass(List.of(
                new Info("bardiademon", "bardiademon@gmail.com", info, new Long[]{9177257149L}),
                new Info("bardiademon", "bardiademon@gmail.com", info, new Long[]{9177257149L}),
                new Info("bardiademon", "bardiademon@gmail.com", info, new Long[]{9177257149L}),
                new Info("bardiademon", "bardiademon@gmail.com", info, new Long[]{9177257149L}),
                new Info("bardiademon", "bardiademon@gmail.com", info, new Long[]{9177257149L}))
        );

        System.out.println("jjsonObject = " + jjsonObject);
        System.out.println("jjsonArray = " + jjsonArray);

        final ComplexClass complexClass = new ComplexClass(
                "this is a title",
                null,
                new ComplexClass.Details(
                        "this is a description",
                        new ComplexClass.Details.Author("bardia", "Iran", new ComplexClass.Details.Author[]{new ComplexClass.Details.Author("bardia", "ir", null)}),
                        new ComplexClass.Details.Publisher("bardiademon", "IR")
                )
        );

        final JjsonObject jjsonObject1 = JjsonObject.ofClass(complexClass);

        System.out.println("jjsonObject1 = " + jjsonObject1);


        final Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("id", "bardiademon");
        hashMap.put("firstname", "Bardia");
        hashMap.put("lastname", "Namjoo");
        hashMap.put(null, "Namjoo");
        hashMap.put("info", info);
        hashMap.put("info2", new int[]{454, 484, 989, 8548, 489});

        JjsonObject jjsonObject2 = JjsonObject.ofClass(hashMap);
        System.out.println("jjsonObject2 = " + jjsonObject2);

        System.out.println("--------------------------------------------------------------------------------");

        final Test1 test1 = new Test1();

        final JjsonObject joTestClass1 = JjsonObject.ofClass(test1);
        System.out.println("joTestClass1 = " + joTestClass1);


        Logger.enableLog(false, false);
    }


    @JjsonInclude(mode = JjsonInclude.SerializationMode.FIELDS_AND_METHODS, includeNonPublicField = true, includeNonPublicMethod = true, includeMethod = true, useJjsonClass = true)
    public record Info(String name, @JjsonProperty(name = "bardiaEmail") String email,
                       @JjsonProperty(ignoreIfNull = true) Info info,
                       Long[] numbers) implements JjsonClass<JjsonArray> {

        @JjsonProperty(name = "info")
        @Override
        public JjsonArray jsonValue() {
            return JjsonArray.create().put(JjsonObject.create().put("name", name).put("email", email));
        }
    }

    @JjsonInclude(includeNonPublicField = true)
    public static class ComplexClass {

        // فیلدهای کلاس اصلی
        private String title;
        @JjsonProperty(nullable = false)
        private Integer year;
        private Details details;

        // سازنده کلاس اصلی
        public ComplexClass(String title, Integer year, Details details) {
            this.title = title;
            this.year = year;
            this.details = details;
        }

        @JjsonInclude(includeNonPublicField = true)
        // کلاس تو در تو برای جزییات
        public static class Details {
            private String description;
            private Author author;
            private Publisher publisher;

            public Details(String description, Author author, Publisher publisher) {
                this.description = description;
                this.author = author;
                this.publisher = publisher;
            }

            @JjsonInclude(includeNonPublicField = true)
            // کلاس تو در تو برای نویسنده
            public static class Author {
                private String name;
                private String nationality;

                @JjsonProperty(nullable = false)
                private Author[] arr;

                public Author() {
                }

                public Author(String name, String nationality, final Author[] arr) {
                    this.name = name;
                    this.nationality = nationality;
                    this.arr = arr;
                }

                // Getters and Setters برای فیلدهای کلاس Author
                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getNationality() {
                    return nationality;
                }

                public void setNationality(String nationality) {
                    this.nationality = nationality;
                }
            }

            @JjsonInclude(includeNonPublicField = true)
            // کلاس تو در تو برای ناشر
            public static class Publisher {
                private String publisherName;
                private String location;

                public Publisher(String publisherName, String location) {
                    this.publisherName = publisherName;
                    this.location = location;
                }

                // Getters and Setters برای فیلدهای کلاس Publisher
                public String getPublisherName() {
                    return publisherName;
                }

                public void setPublisherName(String publisherName) {
                    this.publisherName = publisherName;
                }

                public String getLocation() {
                    return location;
                }

                public void setLocation(String location) {
                    this.location = location;
                }
            }

            // Getters and Setters برای فیلدهای کلاس Details
            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public Author getAuthor() {
                return author;
            }

            public void setAuthor(Author author) {
                this.author = author;
            }

            public Publisher getPublisher() {
                return publisher;
            }

            public void setPublisher(Publisher publisher) {
                this.publisher = publisher;
            }
        }

        // Getters and Setters برای فیلدهای کلاس ComplexClass
        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public Details getDetails() {
            return details;
        }

        public void setDetails(Details details) {
            this.details = details;
        }


    }

    @JjsonInclude(mode = JjsonInclude.SerializationMode.FIELDS_AND_METHODS, includeStaticMethod = true, includeField = true, includeStaticField = true, includeNonPublicMethod = true)
    public static class Test1 {

        public String name = "test";
        private int age = 28;

        @JjsonProperty(nullable = false, defaultValueMethodName = "getDefaultStaticVarName")
        public static Boolean staticVar = null;

        public static Boolean getDefaultStaticVarName() {
            return true;
        }

        @JjsonProperty(nullable = true, defaultValueMethodName = "getDefaultInfoName")
        public static Info[] info = null;

        public static Info[] getDefaultInfoName() {
            return new Info[]{new Info("bardia", "bardiademn@gmail.com", null, null)};
        }

        @JjsonProperty(name = "field_name", nullable = true)
        public String getName() {
            return name;
        }

        @JjsonProperty(name = "age")
        public int getAge() {
            return age;
        }

        public void setName(String name) {
            this.name = name;
        }

        @JjsonProperty
        private void voidMethod() {
            System.out.println("Void method");
        }

        @JjsonProperty
        public int intMethod() {
            System.out.println("Int method");
            return 12;
        }

        @JjsonProperty
        public float floatMethod() {
            System.out.println("Float method");
            return (float) Math.PI;
        }

        @JjsonProperty(name = "my_name")
        private static String stringStaticMethod() {
            System.out.println("string static method");
            return "bardiademon";
        }

        @JjsonProperty
        public static void voidStaticMethod() {
            System.out.println("void static method");
        }

        @JjsonProperty
        public static Void voidStaticMethod2() {
            System.out.println("void static method 2");
            return null;
        }

    }

}
