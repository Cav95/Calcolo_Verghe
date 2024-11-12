package p12.exercise;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
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
                    if((int) o1.getLenght() < (int) o2.getLenght()){
                        return 1;
                    }
                    else{
                        return -1;
                    }
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
    public void addTubolar(T elem, Q queue, int quantity) {

        if (availableQueues().contains(queue)) {
            Tubolar<T> newTubolar = new Tubolar<>(elem, quantity);

            multiQueue.get(queue).add(newTubolar);
        } else {
            throw new IllegalArgumentException();

        }

    }

    @Override
    public void removeTubolar(Q queue, T lenght) {
        if (availableQueues().contains(queue)) {

            for (var myIterator = getTubolarList(queue).iterator(); myIterator.hasNext();) {
                if (myIterator.next().getLenght().equals(lenght)) {
                    myIterator.remove();
                }
            }
        } else {
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

            return multiQueue.get(queue).removeAll(multiQueue.get(queue));
        } else {
            throw new IllegalArgumentException();

        }
    }

    @Override
    public void printAllQueue() {
        if (!availableQueues().isEmpty()) {
            for (Entry<Q, Set<Tubolar<T>>> elemEntry : multiQueue.entrySet()) {
                System.out.print(elemEntry.getKey() + " ");
                for (Tubolar<T> elem : elemEntry.getValue()) {
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

    @Override
    public HashMap<Q, LinkedList<LinkedList<T>>> calcoloTotal() {
        HashMap<Q, LinkedList<LinkedList<T>>> mapCut = new HashMap<>();

        for (Entry<Q, Set<Tubolar<T>>> elemEntry : multiQueue.entrySet()) {
            System.out.println(elemEntry.getKey());
            mapCut.put(elemEntry.getKey(), calcoloVerga(elemEntry.getKey(), 6000));

        }
        return mapCut;

    }

    @Override
    public LinkedList<LinkedList<T>> calcoloVerga(Q queue, int lenght) {
        LinkedList<LinkedList<T>> listShort = new LinkedList<>();
        // LinkedList<LinkedList<T>> listLong = new LinkedList<>();

        while (!multiQueue.get(queue).isEmpty()) {

            int total1 = lenght;
            LinkedList<T> list = new LinkedList<>();

            for (Tubolar<T> elem : multiQueue.get(queue)) {

                while (elem.getQuantity() > 0 && ((int) total1 - (int) elem.getLenght()) >= 0) {

                    list.add(elem.getLenght());
                    elem.setQuantity(elem.getQuantity() - 1);
                 total1 = (int) total1 - (int) elem.getLenght();

                }
            }
            for(var myIterator = multiQueue.get(queue).iterator() ; myIterator.hasNext() ;  ){
                if(myIterator.next().getQuantity() == 0 ){
                    myIterator.remove();
                }
            }

            System.out.println(list);// da rimuovere

            listShort.add(list);
        }
        return listShort;

    }

}
