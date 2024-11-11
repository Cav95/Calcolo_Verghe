package p12.exercise;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

public class MultiQueueImpl<T, Q> implements MultiQueue<T, Q> {

    HashMap<Q, Set<Tubolar<T>>> multiQueue = new HashMap<>();

    @Override
    public Set<Q> availableQueues() {
               
        return multiQueue.keySet();
    }

    @Override
    public void openNewQueue(Q queue) {

        if (!availableQueues().contains(queue)) {
            Set<Tubolar<T>> newQueue = new TreeSet<>(new Comparator<Tubolar<T>>() {

                @Override
                public int compare(Tubolar<T> o1, Tubolar<T> o2) {
                    return (int)o1.getLenght() - (int)o2.getLenght();
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
            Tubolar<T> newTubolar = new Tubolar<>(elem);

            multiQueue.get(queue).add(newTubolar);
        } else {
            throw new IllegalArgumentException();

        }

    }

    @Override
    public void removeTubolar(Q queue , T lenght) {
        if (availableQueues().contains(queue)) {

            for(var myIterator = getTubolarList(queue).iterator() ; myIterator.hasNext() ; ){
                if(myIterator.next().getLenght().equals(lenght)){
                     myIterator.remove() ;                    
                }
            }
        }
        else{
            throw new IllegalArgumentException();   
        }
            
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
    public boolean dequeueAllFromQueue(Q queue) {
        if (availableQueues().contains(queue)) {
                 
             return  multiQueue.get(queue).removeAll(multiQueue.get(queue));
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

    

    @Override
    public Set<Tubolar<T>> getTubolarList(Q queue) {
        return multiQueue.get(queue);
    }

    
}
