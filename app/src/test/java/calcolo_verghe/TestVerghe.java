package calcolo_verghe;

import java.util.LinkedList;
import java.util.Objects;
import org.junit.jupiter.api.Test;

import p12.exercise.CalcolatorTubolar;
import p12.exercise.CalcolatorTubolarInterface;
import p12.exercise.MultiQueue;
import p12.exercise.MultiQueueImpl;
import org.javatuples.Pair;

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
    public void testOpenNewQueue() {
        MultiQueue<Integer,String> mq = new MultiQueueImpl<>();
        mq.openNewQueue("Q1");
        mq.openNewQueue("Q2");
        assertEquals(mq.availableQueues().contains("Q1"),true);
        assertEquals(mq.availableQueues().contains("Q2"),true);
        assertEquals(mq.availableQueues().contains("Q3"),false);

    }

    
    @Test
    public void testEnqueue() {
        // Creo le code Q1 e Q2, e ci metto dentro vari elementi 
        MultiQueue<Integer,String> mq = new MultiQueueImpl<>();
        assertEquals(mq.allEnqueuedElements().size(),0);
        
        mq.openNewQueue("Q1");
        mq.openNewQueue("Q2");
        mq.addTubolar(1000, "Q2",1);
        mq.addTubolar(1001, "Q2",1);
        mq.addTubolar(1002, "Q2",1);
        mq.addTubolar(1003, "Q1",1);
        mq.addTubolar(1004, "Q1",1);
        // Verifico quali elementi sono complessivamente in coda
        assertEquals(mq.getTubolarList("Q1").size() , 2);
        assertEquals(mq.getTubolarList("Q2").size() , 3);
    }

    @Test
    public void testDequeue() {
        // Creo le code Q1 e Q2, e ci metto dentro vari elementi 
        MultiQueue<Integer,String> mq = new MultiQueueImpl<>();
        assertEquals(mq.allEnqueuedElements().size(),0);
        mq.openNewQueue("Q1");
        mq.openNewQueue("Q2");
        mq.addTubolar(1000, "Q2",1);
        mq.addTubolar(1001, "Q2",1);
        mq.addTubolar(1002, "Q2",1);
        mq.addTubolar(1003, "Q1",1);
        mq.addTubolar(1004, "Q1",1);
        // Verifico l'ordine di rimozione degli elementi
        mq.removeTubolar("Q1",1003);
        assertEquals(mq.getTubolarList("Q1").size(),1);

        //assertEquals(mq.removeTubolar("Q2",1000),true);
    }
    
@Test
    public void testFullDequeue() {
        // Creo le code Q1 e Q2, e ci metto dentro vari elementi 
        MultiQueue<Integer,String> mq = new MultiQueueImpl<>();
        assertEquals(mq.allEnqueuedElements().size(),0);
        mq.openNewQueue("Q1");
        mq.openNewQueue("Q2");
        mq.addTubolar(1000, "Q2",1);
        mq.addTubolar(1001, "Q2",1);
        mq.addTubolar(1002, "Q2",1);
        mq.addTubolar(1003, "Q1",1);
        mq.addTubolar(1004, "Q1",1);
        // Rimuovo tutti gli elementi da una coda
        assertEquals(mq.dequeueAllFromQueue("Q2"), true);
        assertEquals(mq.dequeueAllFromQueue("Q2"),false);
    }

    @Test
    public void testVerghe() {
        // Creo le code Q1 e Q2, e ci metto dentro vari elementi 
        CalcolatorTubolarInterface<Integer, String> mq = new CalcolatorTubolar<>();
        assertEquals(mq.allEnqueuedElements().size(),0);

        
        mq.openNewQueue("Q1");
        mq.openNewQueue("Q2");
        mq.addTubolar(1000, "Q2",3);
        mq.addTubolar(2000, "Q2",4);
        mq.addTubolar(150, "Q1",30);
        mq.addTubolar(6000, "Q1",2);

        LinkedList<Pair<Integer,LinkedList<Integer>>> test = new LinkedList<>();
        test =  mq.calcoloVerga("Q2", 6000);
        assertEquals(test.size(),2);
    }
   

}
