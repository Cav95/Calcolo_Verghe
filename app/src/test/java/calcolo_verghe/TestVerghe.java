package calcolo_verghe;

import java.util.Objects;
import org.junit.jupiter.api.Test;

import CalcoloTubolare.model.MultiQueueImpl;
import CalcoloTubolare.model.api.MultiQueue;


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

    @Test
    public void testOpenNewQueue() {
        MultiQueue<Integer, String> mq = new MultiQueueImpl<>();
        mq.openNewQueue("Q1");
        mq.openNewQueue("Q2");
        assertEquals(mq.availableQueues().contains("Q1"), true);
        assertEquals(mq.availableQueues().contains("Q2"), true);
        assertEquals(mq.availableQueues().contains("Q3"), false);

    }

}
