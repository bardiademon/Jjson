import com.bardiademon.Jjson.JjsonArray.JjsonArray;
import com.bardiademon.Jjson.JjsonObject.JjsonObject;
import com.bardiademon.Jjson.converter.JjsonArrayConverter;
import com.bardiademon.Jjson.converter.JjsonObjectConverter;
import com.bardiademon.Jjson.data.exception.JjsonException;

public final class JsonValidationTest {

    public static void main(final String[] args) throws JjsonException {
        JjsonObject jjsonObject = new JjsonObjectConverter().fromString("""
                {
                "number": 1,
                                 "glossary": {
                                     "title": "example glossary",
                             		"GlossDiv": {
                                         "title": "S",
                             			"GlossList": {
                                             "GlossEntry": {
                                                 "ID": "SGML",
                             					"SortAs": "SGML",
                             					"GlossTerm": "Standard Generalized Markup Language",
                             					"Acronym": "SGML",
                             					"Abbrev": "ISO 8879:1986",
                             					"GlossDef": {
                                                     "para": "A meta-markup language, used to create markup languages such as DocBook.",
                             						"GlossSeeAlso": ["GML", "XML"]
                                                 },
                             					"GlossSee": "markup"
                                             }
                                         }
                                     }
                                 }
                             }
                                             """);


        System.out.println("jjsonObject.toString() = " + jjsonObject);

        final JjsonObject glossary = jjsonObject.getJjsonObject("glossary");
        String title = glossary.getString("title");

        System.out.println("title = " + title);


        final JjsonObject jjsonArray = JjsonObject.fromString("""
               {"name": {"test":2},"array" : [1,4,7]}
                """);

        System.out.println("jjsonArray = " + jjsonArray);

    }
}
