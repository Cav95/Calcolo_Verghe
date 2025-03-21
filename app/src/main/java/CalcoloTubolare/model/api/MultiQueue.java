package CalcoloTubolare.model.api;

import java.util.*;

/**
 * A multiple (FIFO) queue, e.g., to manage people paying at the supermarket
 *
 * @param <T>, the type of requester, also called 'elements'
 * @param <Q>, the type of queues (e.g., they name, or ID, or position)
 */
public interface MultiQueue<T, Q> {
    public HashMap<Q, Set<Tubolar>> getMultiQueue();

    /**
     * @return the set of queues currently available (or working)
     */
    Set<Q> availableQueues();

    /**
     * @param creates a new queue
     * @throws IllegalArgumentException if queue is already available
     */
    void openNewQueue(Q queue);

    /**
     * @param elem,  is the element to add
     * @param queue, is the queue where the element is to be added
     * @throws IllegalArgumentException if queue is not available
     */
    void addTubolar(int elem, Q queue, int quantity);

    /**
     * @param queue, is the queue where to take the next element
     * @return the next element in queue, or null if there's none
     * @throws IllegalArgumentException if queue is not available
     */
    void removeTubolar(Q queue, int lenght);

    /**
     * Empties a queue and move all of its elements in some other available queue
     * 
     * @param , the queue to be emptied
     * @throws IllegalArgumentException if queue is not available
     * @throws IllegalStateException    if there's no alternative queue for moving
     *                                  elements to
     */
    String printAllQueue();

    /**
     * Get list of my same tubolare cut
     * 
     * @param , the queue to be cheched
     * @throws IllegalArgumentException if queue is not available
     */
    Set<Tubolar> getTubolarList(Q queue);
}
