package p12.exercise;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

public class MultiQueueImpl<T, Q> implements MultiQueue<T, Q> {

    HashMap<Q, Set<Tubolar<T>>> multiQueue = new HashMap<>();

    @Override
    public Set<Q> availableQueues() {
        Set<Q> queueAvaiable = new HashSet<>();
        for (Entry<Q, Set<Tubolar<T>>> elem : multiQueue.entrySet()) {
            queueAvaiable.add(elem.getKey());
        }
        return queueAvaiable;
    }

    @Override
    public void openNewQueue(Q queue) {

        if (!availableQueues().contains(queue)) {
            Set<Tubolar<T>> newQueue = new TreeSet(new Comparator<T>() {

                @Override
                public int compare(T o1, T o2) {
                    return (int)o1-(int)o2;
                }
                
            });

            multiQueue.put(queue, newQueue);
        } else {
            throw new IllegalArgumentException();

        }

    }

    @Override
    public boolean isQueueEmpty(Q queue) {
        if (availableQueues().contains(queue)) {
            return multiQueue.get(queue).isEmpty();
        } else {
            throw new IllegalArgumentException();

        }
    }

    @Override
    public void addTubolar(T elem, Q queue) {

        if (availableQueues().contains(queue)) {
            Tubolar<T> newTubolar = new Tubolar(elem);

            multiQueue.get(queue).add(newTubolar);
        } else {
            throw new IllegalArgumentException();

        }

    }

    @Override
    public boolean removeTubolar(Q queue , T lenght) {
        if (availableQueues().contains(queue)) {
            if (!multiQueue.get(queue).isEmpty()) {

                return multiQueue.get(queue).remove(lenght);

        }
            throw new IllegalArgumentException();
    }
        
                return false;
    }

   
    @Override
    public Set<Tubolar<T>> allEnqueuedElements() {
        Set<Tubolar<T>> allEnqueuedElementsSet = new TreeSet<>();

        for (Entry<Q, Set<Tubolar<T>>> elemMap : multiQueue.entrySet()) {
            for (Tubolar<T> elemT : elemMap.getValue()) {
                allEnqueuedElementsSet.add(elemT);
            }

        }
        return allEnqueuedElementsSet;

    }

    @Override
    public List<Tubolar<T>> dequeueAllFromQueue(Q queue) {
        if (availableQueues().contains(queue)) {
            List<Tubolar<T>> listDequeue = new ArrayList<>();

            while (!multiQueue.get(queue).isEmpty()) {
                listDequeue.addLast(multiQueue.get(queue).removeAll(listDequeue));
            }
            return listDequeue;

        } else {
            throw new IllegalArgumentException();

        }
    }

    
    @Override
    public void printAllQueue() {
        if (!availableQueues().isEmpty()) {
            for(Entry<Q, Set<Tubolar<T>>> elemEntry : multiQueue.entrySet()){
                System.out.print(elemEntry.getKey() + " ");
                for(Tubolar<T> elem : elemEntry.getValue()){
                    System.out.print(elem.toString() + " ");
                    
                }
                System.out.println();
            }
            
        } else {
            throw new IllegalArgumentException();

        }
    }

    
}
