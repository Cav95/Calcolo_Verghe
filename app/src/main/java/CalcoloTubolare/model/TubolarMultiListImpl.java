package CalcoloTubolare.model;

import java.util.HashMap;
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

    HashMap<String, Set<Tubolar>> cuttedTubolarMap = new HashMap<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public HashMap<String, Set<Tubolar>> getMultiQueue() {
        return this.cuttedTubolarMap;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<String> availableQueues() {
        return this.cuttedTubolarMap.keySet();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void openNewQueue(String queue) {

        if (!availableQueues().contains(queue)) {
            this.cuttedTubolarMap.put(queue, new TreeSet<>(
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
            if (this.cuttedTubolarMap.get(code).contains(new Tubolar(lenght, quantity))) {
                for (var myIterator = this.cuttedTubolarMap.get(code).iterator(); myIterator.hasNext();) {
                    var tub = myIterator.next();
                    if (tub.getLenght() == lenght) {
                        tub.setQuantity(tub.getQuantity() + quantity);
                    }
                }
            } else {
                this.cuttedTubolarMap.get(code).add(new Tubolar(lenght, quantity));
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

            if (this.cuttedTubolarMap.get(queue).isEmpty()) {
                this.cuttedTubolarMap.remove(queue);
            }
        } else {
            throw new IllegalArgumentException();
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Tubolar> getTubolarList(String queue) {
        return this.cuttedTubolarMap.get(queue);
    }

}
