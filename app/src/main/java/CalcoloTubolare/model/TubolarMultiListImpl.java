package CalcoloTubolare.model;

import java.util.HashMap;
import java.util.Map.Entry;

import CalcoloTubolare.model.api.TubolarMultiList;
import CalcoloTubolare.model.api.Tubolar;

import java.util.Set;
import java.util.TreeSet;

/**
 * TubolarMultiListImpl class implementing the TubolarMultiList interface.
 * It manages multiple queues of tubulars with operations to add, remove, and
 * manipulate them.
 */
public class TubolarMultiListImpl implements TubolarMultiList {

    HashMap<String, Set<Tubolar>> multiQueue = new HashMap<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public HashMap<String, Set<Tubolar>> getMultiQueue() {
        return multiQueue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<String> availableQueues() {
        return multiQueue.keySet();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void openNewQueue(String queue) {

        if (!availableQueues().contains(queue)) {
            multiQueue.put(queue, new TreeSet<>(
                    (o1, o2) -> Integer.compare(o1.getLenght(), o2.getLenght())));
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addTubolar(Integer lenght, String code, Integer quantity) {

        if (availableQueues().contains(code)) {
            if (multiQueue.get(code).contains(new Tubolar(lenght, quantity))) {
                for (var myIterator = multiQueue.get(code).iterator(); myIterator.hasNext();) {
                    var tub = myIterator.next();
                    if (tub.getLenght() == lenght) {
                        tub.setQuantity(tub.getQuantity() + quantity);
                    }
                }
            } else {
                multiQueue.get(code).add(new Tubolar(lenght, quantity));
            }
        } else {
            throw new IllegalArgumentException();
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeTubolar(String queue, Integer lenght) {
        if (availableQueues().contains(queue)) {
            getTubolarList(queue).removeIf(t -> t.getLenght() == lenght);

            if (this.multiQueue.get(queue).isEmpty()) {
                this.multiQueue.remove(queue);
            }
        } else {
            throw new IllegalArgumentException();
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String printAllQueue() {
        String out = "";
        if (!availableQueues().isEmpty()) {
            for (Entry<String, Set<Tubolar>> elemEntry : multiQueue.entrySet()) {
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

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Tubolar> getTubolarList(String queue) {
        return multiQueue.get(queue);
    }

}
