package calcolo_verghe;

import java.util.Objects;
import java.util.Set;

import org.junit.jupiter.api.Test;

import p12.exercise.MultiQueue;
import p12.exercise.MultiQueueImpl;
import p12.exercise.Tubolar; ;



public class TestVerghe {


   private static void assertEquals(Object result, Object expected){
        if (!Objects.equals(result, expected)){
            System.out.println(result+" is different from "+expected);
            fail("generic error");
        }
    }

    private static void fail(String s){
        throw new RuntimeException(s);
    }

    @Test
    public void testEnqueue() {
        // Creo le code Q1 e Q2, e ci metto dentro vari elementi 
        MultiQueue<Integer,String> mq = new MultiQueueImpl<>();
        assertEquals(mq.allEnqueuedElements().size(),0);
        mq.openNewQueue("Q1");
        mq.openNewQueue("Q2");
        mq.addTubolar(1000, "Q2");
        mq.addTubolar(1001, "Q2");
        mq.addTubolar(1002, "Q2");
        mq.addTubolar(1003, "Q1");
        mq.addTubolar(1004, "Q1");
        // Verifico quali elementi sono complessivamente in coda
        assertEquals(mq.allEnqueuedElements(),Set.of( 1000,1001,1002,1003,1004));
    }

    @Test
    public void testDequeue() {
        // Creo le code Q1 e Q2, e ci metto dentro vari elementi 
        MultiQueue<Integer,String> mq = new MultiQueueImpl<>();
        assertEquals(mq.allEnqueuedElements().size(),0);
        mq.openNewQueue("Q1");
        mq.openNewQueue("Q2");
        mq.addTubolar(1000, "Q2");
        mq.addTubolar(1001, "Q2");
        mq.addTubolar(1002, "Q2");
        mq.addTubolar(1003, "Q1");
        mq.addTubolar(1004, "Q1");
        // Verifico l'ordine di rimozione degli elementi
        assertEquals(mq.removeTubolar("Q1",1003),true);
        assertEquals(mq.removeTubolar("Q2",1000),true);
        assertEquals(mq.removeTubolar("Q2",1001),true);
        assertEquals(mq.removeTubolar("Q2",1002),true);
        assertEquals(mq.removeTubolar("Q2",1000),false);
        assertEquals(mq.removeTubolar("Q2",1000),false);
        // Altre aggiunte e rimozioni..
        mq.addTubolar(1005, "Q1");
        mq.addTubolar(1006, "Q2");
        assertEquals(mq.removeTubolar("Q2",1066),true);
        assertEquals(mq.allEnqueuedElements(),Set.of(1004,1005));        
    }
    
@Test
    public void testFullDequeue() {
        // Creo le code Q1 e Q2, e ci metto dentro vari elementi 
        MultiQueue<Integer,String> mq = new MultiQueueImpl<>();
        assertEquals(mq.allEnqueuedElements().size(),0);
        mq.openNewQueue("Q1");
        mq.openNewQueue("Q2");
        mq.addTubolar(1000, "Q2");
        mq.addTubolar(1001, "Q2");
        mq.addTubolar(1002, "Q2");
        mq.addTubolar(1003, "Q1");
        mq.addTubolar(1004, "Q1");
        // Rimuovo tutti gli elementi da una coda
        assertEquals(mq.dequeueAllFromQueue("Q2"), true);
        assertEquals(mq.dequeueAllFromQueue("Q2"),false);
    }
    
    

}
