import com.example.Joker;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 * Created by greent on 2/24/16.
 */
public class TestJoker  {
    @Test
    public void testMakeMeLaugh() {
        Joker joker = new Joker();
        assertThat(joker.makeMeLaugh(), notNullValue());
    }
}
