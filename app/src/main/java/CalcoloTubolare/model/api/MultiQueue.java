package CalcoloTubolare.model.api;

import java.util.*;

/**
 * A multiple (FIFO) queue, e.g., to manage people paying at the supermarket
 *
 * @param <T>, the type of requester, also called 'elements'
 * @param <Q>, the type of queues (e.g., they name, or ID, or position)
 */
public interface MultiQueue<T, Q> {
    public HashMap<Q, Set<Tubolar<T>>> getMultiQueue();

    /**
     * @return the set of queues currently available (or working)
     */
    Set<Q> availableQueues();

    /**
     * @return the set of queues currently available (or working)
     */
    HashMap<Q, Set<Tubolar<T>>> getMap();

    /**
     * @param creates a new queue
     * @throws IllegalArgumentException if queue is already available
     */
    void openNewQueue(Q queue);

    /**
     * @param queue, is the queue we check
     * @return whether queue is empty
     * @throws IllegalArgumentException if queue is not available
     */
    boolean isQueueEmpty(Q queue);

    /**
     * @param elem,  is the element to add
     * @param queue, is the queue where the element is to be added
     * @throws IllegalArgumentException if queue is not available
     */
    void addTubolar(T elem, Q queue, int quantity);

    /**
     * @param queue, is the queue where to take the next element
     * @return the next element in queue, or null if there's none
     * @throws IllegalArgumentException if queue is not available
     */
    void removeTubolar(Q queue, T lenght);

    /**
     * @return the set of all enqueued elements
     */
    Set<Tubolar<T>> allEnqueuedElements();

    /**
     * Empties a queue
     * 
     * @param queue, the queue to be emptied
     * @return the list of elements enqueued
     * @throws IllegalArgumentException if queue is not available
     */
    boolean dequeueAllFromQueue(Q queue);

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
    Set<Tubolar<T>> getTubolarList(Q queue);
}
