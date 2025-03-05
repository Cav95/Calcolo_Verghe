package CalcoloTubolare.model;

import java.util.HashMap;
import java.util.Map.Entry;

import CalcoloTubolare.model.api.MultiQueue;
import CalcoloTubolare.model.api.Tubolar;

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
    public void addTubolar(T elem, Q queue, int quantity) {

        if (availableQueues().contains(queue)) {
            multiQueue.get(queue).add(new Tubolar<>(elem, quantity));
        } else {
            throw new IllegalArgumentException();
        }

    }

    @Override
    public void removeTubolar(Q queue, T lenght) {
        if (availableQueues().contains(queue)) {
            getTubolarList(queue).removeIf(t -> t.getLenght().equals(lenght));

            if (this.multiQueue.get(queue).isEmpty()) {
                this.multiQueue.remove(queue);
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
                out = out + elemEntry.getKey() + "\n";
                out = out + elemEntry.getValue().stream().map(t -> " L=" + t.getLenght() + " QT=" + t.getQuantity())
                        .distinct().reduce("", (a, b) -> a + b);
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

}
