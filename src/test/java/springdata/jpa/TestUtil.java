package springdata.jpa;

/**
 * @author Petri Kainulainen
 */
public class TestUtil {

    public static String createUpdatedString(String value) {
        StringBuilder builder = new StringBuilder();

        builder.append(value);
        builder.append("Updated");

        return builder.toString();
    }
}
