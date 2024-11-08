package p12.exercise;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

public class MultiQueueImpl<T, Q> implements MultiQueue<T, Q> {

    HashMap<Q, Set<T>> multiQueue = new HashMap<>();

    @Override
    public Set<Q> availableQueues() {
        Set<Q> queueAvaiable = new HashSet<>();
        for (Entry<Q, Set<T>> elem : multiQueue.entrySet()) {
            queueAvaiable.add(elem.getKey());
        }
        return queueAvaiable;
    }

    @Override
    public void openNewQueue(Q queue) {

        if (!availableQueues().contains(queue)) {
            Set<T> newQueue = new TreeSet<>(new Comparator<T>() {

                @Override
                public int compare(T o1, T o2) {
                    return (int)o1-(int)o2;
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
    public void enqueue(T elem, Q queue) {

        if (availableQueues().contains(queue)) {
            multiQueue.get(queue).add(elem);
        } else {
            throw new IllegalArgumentException();

        }

    }

  /*  @Override
  //  public T dequeue(Q queue) {
        if (availableQueues().contains(queue)) {
            if (!multiQueue.get(queue).isEmpty()) {
             //   return multiQueue.get(queue).remove(queue);
            } else {
                return null;
            }
        } else {
            throw new IllegalArgumentException();

        }
        //TO DO
    }
*/
    @Override
    public Map<Q, T> dequeueOneFromAllQueues() {
        HashMap<Q, T> multiDequeueOne = new HashMap<>();

        for (Entry<Q, Set<T>> elem : multiQueue.entrySet()) {
            multiDequeueOne.put(elem.getKey(), dequeue(elem.getKey()));
        }
        return multiDequeueOne;
    }

    @Override
    public Set<T> allEnqueuedElements() {
        Set<T> allEnqueuedElementsSet = new TreeSet<>();

        for (Entry<Q, Set<T>> elemMap : multiQueue.entrySet()) {
            for (T elemT : elemMap.getValue()) {
                allEnqueuedElementsSet.add(elemT);
            }

        }
        return allEnqueuedElementsSet;

    }

    @Override
    public List<T> dequeueAllFromQueue(Q queue) {
        if (availableQueues().contains(queue)) {
            List<T> listDequeue = new ArrayList<>();

            while (!multiQueue.get(queue).isEmpty()) {
                listDequeue.addLast(dequeue(queue));
            }
            return listDequeue;

        } else {
            throw new IllegalArgumentException();

        }
    }

    @Override
    public void closeQueueAndReallocate(Q queue) {
        if (availableQueues().contains(queue)) {
            List<T> reallocatedListDequeue = dequeueAllFromQueue(queue);
            multiQueue.remove(queue);

            if (!availableQueues().isEmpty()) {

                while (!reallocatedListDequeue.isEmpty()) {
                    multiQueue.get(availableQueues().iterator().next()).add(reallocatedListDequeue.removeFirst());
                }
            } else {
                throw new IllegalStateException();
            }

        } else {
            throw new IllegalArgumentException();

        }

    }

    @Override
    public void printAllQueue() {
        if (!availableQueues().isEmpty()) {
            for(Entry<Q, Set<T>> elemEntry : multiQueue.entrySet()){
                System.out.print(elemEntry.getKey() + " ");
                for(T elem : elemEntry.getValue()){
                    System.out.print(elem.toString() + " ");
                    
                }
                System.out.println();
            }
            
        } else {
            throw new IllegalArgumentException();

        }
    }

    @Override
    public T dequeue(Q queue) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'dequeue'");
    }
}
