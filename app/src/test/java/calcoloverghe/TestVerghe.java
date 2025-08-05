package calcoloverghe;

import java.util.Objects;
import org.junit.jupiter.api.Test;

import verghe.model.TubolarMultiListImpl;
import verghe.model.api.TubolarMultiList;

/**
 * Test class for verifying the functionality of TubolarMultiList.
 * This class contains tests for opening new queues in the TubolarMultiList.
 */
public class TestVerghe {

    private static void assertEquals(Object result, Object expected) {
        if (!Objects.equals(result, expected)) {
            System.out.println(result + " is different from " + expected);
            fail("generic error");
        }
    }

    private static void fail(String s) {
        throw new RuntimeException(s);
    }

    /**
     * Tests the opening of a new queue in the TubolarMultiList.
     * It checks if the queues "Q1" and "Q2" are available after opening them,
     * and "Q3" is not available.
     */
    @Test
    public void testOpenNewQueue() {
        TubolarMultiList mq = new TubolarMultiListImpl();
        mq.openNewQueue("Q1");
        mq.openNewQueue("Q2");
        assertEquals(mq.availableQueues().contains("Q1"), true);
        assertEquals(mq.availableQueues().contains("Q2"), true);
        assertEquals(mq.availableQueues().contains("Q3"), false);

    }

}
