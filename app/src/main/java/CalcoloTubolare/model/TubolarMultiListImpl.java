package CalcoloTubolare.model;

import java.util.HashMap;
import java.util.Map.Entry;

import CalcoloTubolare.model.api.TubolarMultiList;
import CalcoloTubolare.model.api.Tubolar;

import java.util.Set;
import java.util.TreeSet;

public class TubolarMultiListImpl implements TubolarMultiList {

    HashMap<String, Set<Tubolar>> multiQueue = new HashMap<>();

    @Override
    public HashMap<String, Set<Tubolar>> getMultiQueue() {
        return multiQueue;
    }

    @Override
    public Set<String> availableQueues() {
        return multiQueue.keySet();
    }

    @Override
    public void openNewQueue(String queue) {

        if (!availableQueues().contains(queue)) {
            multiQueue.put(queue, new TreeSet<>(
                    (o1, o2) -> Integer.compare(o1.getLenght(), o2.getLenght())));
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void addTubolar(Integer elem, String queue, Integer quantity) {

        if (availableQueues().contains(queue)) {
            if (multiQueue.get(queue).contains(new Tubolar(elem, quantity))) {
                for (var myIterator = multiQueue.get(queue).iterator(); myIterator.hasNext();) {
                    var tub = myIterator.next();
                    if (tub.getLenght() == elem) {
                        tub.setQuantity(tub.getQuantity() + quantity);
                    }
                }
            } else {
                multiQueue.get(queue).add(new Tubolar(elem, quantity));
            }
        } else {
            throw new IllegalArgumentException();
        }

    }

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

    @Override
    public Set<Tubolar> getTubolarList(String queue) {
        return multiQueue.get(queue);
    }

}
