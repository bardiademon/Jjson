import com.bardiademon.Jjson.array.JjsonArray;
import com.bardiademon.Jjson.object.JjsonObject;
import com.bardiademon.Jjson.converter.clazz.JjsonClass;

public class TestEnum {
    public static void main(String[] args) {

        final JjsonArray jjsonArray = JjsonArray.ofArray(new MessageType[]{MessageType.TEXT, MessageType.VIDEO});
        final JjsonArray jjsonArray2 = JjsonArray.create().put(MessageType.IMAGE).put(MessageType.VOICE);
        final JjsonObject jjsonObject = JjsonObject.create().put("enums", new MessageType[]{MessageType.TEXT, MessageType.VIDEO});

        System.out.println("jjsonArray.encode() = " + jjsonArray.encode());
        System.out.println("jjsonArray2.encode() = " + jjsonArray2.encode());
        System.out.println("jjsonObject.encode() = " + jjsonObject.encode());

        System.out.println("--------------------------------------------------------------------------------------------------------------------");

        final JjsonArray carsArray = JjsonArray.ofArray(new Cars[]{Cars.PARS, Cars.GLX_405});
        final JjsonArray carsArray2 = JjsonArray.create().put(Cars.PARS_TU5);
        final JjsonObject cars = JjsonObject.create().put("cars", new Cars[]{Cars.GLX_405, Cars.PARS});

        System.out.println("carsArray.encode() = " + carsArray.encode());
        System.out.println("carsArray2.encode() = " + carsArray2.encode());
        System.out.println("cars.encode() = " + cars.encode());

        System.out.println("--------------------------------------------------------------------------------------------------------------------");


        final JjsonArray kingArray = JjsonArray.ofArray(new IranianKings[]{IranianKings.DARIUSH_CREATE, IranianKings.KOROUSH_GREAT});
        final JjsonArray kingArray2 = JjsonArray.create().put(IranianKings.NADER_KING);
        final JjsonObject king = JjsonObject.create().put("kings", new IranianKings[]{IranianKings.KOROUSH_GREAT, IranianKings.DARIUSH_CREATE});

        System.out.println("kingArray.encode() = " + kingArray.encode());
        System.out.println("kingArray2.encode() = " + kingArray2.encode());
        System.out.println("king.encode() = " + king.encode());

    }

    public enum MessageType {
        TEXT,
        IMAGE,
        VOICE,
        VIDEO
        //
        ;
    }

    public enum Cars {
        PARS("pars"),
        GLX_405("405 glx"),
        PARS_TU5("pars tu5")
        //
        ;

        private final String carName;

        Cars(final String carName) {
            this.carName = carName;
        }

        @Override
        public String toString() {
            return carName;
        }
    }

    public enum IranianKings implements JjsonClass<String> {
        KOROUSH_GREAT("من برای آزادی و احترام به حقوق بشر قیام کردم."),
        DARIUSH_CREATE("هرکس که در این جهان کار نیکو انجام دهد، در نهایت برنده خواهد شد."),
        NADER_KING("نادر پسر شمشیر")
        //
        ;

        private final String word;

        IranianKings(final String word) {
            this.word = word;
        }

        @Override
        public String jsonValue() {
            return word;
        }
    }
}
