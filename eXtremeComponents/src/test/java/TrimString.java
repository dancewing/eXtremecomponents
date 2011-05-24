import org.extremecomponents.util.StringUtils;
import org.junit.Test;

/**
 * Created by jeff
 */
public class TrimString {
    @Test
    public void testTrim() {
         String s = "\t\n         男\r";
        System.out.println(StringUtils.trimAllWhitespace(s));
    }
}
