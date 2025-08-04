package verghe.model.api;

import java.util.*;

/**
 * A multiple (FIFO) queue, e.g., to manage people paying at the supermarket.
 * 
 */
public interface TubolarMultiList {
    public HashMap<String, Set<Tubolar>> getMultiQueue();

    /**
     * @return the set of queues currently available (or working).
     */
    Set<String> availableQueues();

    /**
     * @param creates a new queue.
     * @throws IllegalArgumentException if queue is already available.
     */
    void openNewQueue(String code);

    /**
     * @param lenght, is the element to add.
     * @param code,   is the queue where the element is to be added.
     * @throws IllegalArgumentException if queue is not available.
     */
    public void addTubolar(Integer lenght, String code, Integer quantity);

    /**
     * @param code,   is the queue where to take the next element.
     * @param lenght, is the length of the element to be removed.
     * @return the next element in queue, or null if there's none.
     * @throws IllegalArgumentException if queue is not available.
     */
    void removeTubolar(String code, Integer lenght);

    /**
     * Get list of my same tubolare cut
     * 
     * @param , the queue to be cheched.
     * @throws IllegalArgumentException if queue is not available.
     */
    Set<Tubolar> getTubolarList(String code);
}
