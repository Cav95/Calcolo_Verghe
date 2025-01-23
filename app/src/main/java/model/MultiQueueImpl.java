package model;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

public class MultiQueueImpl<T, Q> implements MultiQueue<T, Q> {

    HashMap<Q, Set<Tubolar<T>>> multiQueue = new HashMap<>();

    @Override
    public HashMap<Q, Set<Tubolar<T>>> getMultiQueue() {
        return multiQueue;
    }

    @Override
    public Set<Q> availableQueues() {
        return multiQueue.keySet();
    }

    @Override
    public void openNewQueue(Q queue) {

        if (!availableQueues().contains(queue)) {
            Set<Tubolar<T>> newQueue = new TreeSet<>(
                    (o1, o2) -> ((int) o1.getLenght() < (int) o2.getLenght()) ? 1 : -1);
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
            if (this.multiQueue.get(queue).isEmpty()) {
                this.multiQueue.remove(queue);
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

            if (!multiQueue.get(queue).isEmpty()) {
                multiQueue.get(queue).clear();
                return true;
            } else {
                return false;
            }

        } else {
            throw new IllegalArgumentException();

        }
    }

    @Override
    public String printAllQueue() {
        String out = "";
        if (!availableQueues().isEmpty()) {
            for (Entry<Q, Set<Tubolar<T>>> elemEntry : multiQueue.entrySet()) {
                out = out + elemEntry.getKey() + " ";
                out = out + elemEntry.getValue().stream().map(t -> " L=" + t.getLenght() + " QT=" + t.getQuantity())
                        .toList().toString();
                out = out + "\n";
            }
        } else {
            throw new IllegalArgumentException();
        }
        System.out.print(out);
        return out;
    }

    @Override
    public Set<Tubolar<T>> getTubolarList(Q queue) {
        return multiQueue.get(queue);
    }

    @Override
    public HashMap<Q, Set<Tubolar<T>>> getMap() {
        return multiQueue;

    }

}
