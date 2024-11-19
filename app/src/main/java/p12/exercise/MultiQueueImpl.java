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
                    if ((int) o1.getLenght() < (int) o2.getLenght()) {
                        return 1;
                    } else {
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
    public String printAllQueue() {
        String out = "";
        if (!availableQueues().isEmpty()) {
            for (Entry<Q, Set<Tubolar<T>>> elemEntry : multiQueue.entrySet()) {
                out = out + elemEntry.getKey() + " ";
                System.out.print(elemEntry.getKey() + " ");
                for (Tubolar<T> elem : elemEntry.getValue()) {
                    out = out + elem.toString() + " ";
                    System.out.print(elem.toString() + " ");
                }
                out = out  + "\n";
                    System.out.print("\n");

            
            }
        } else {
            throw new IllegalArgumentException();
        }
        return out;
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

        
        LinkedList<LinkedList<T>> listShort = tubConfronto( multiQueue.get(queue) , 6000);
        LinkedList<LinkedList<T>> listLong =  tubConfronto( multiQueue.get(queue) , 12000);;


        return listShort.size()>(listLong.size()/2)? listShort : listLong;

    }

    @Override
    public String printCuttedTubolar(HashMap<Q, LinkedList<LinkedList<T>>> mapCut) {
        String out = "";
        if (!availableQueues().isEmpty()) {
            for (Entry<Q, LinkedList<LinkedList<T>>> elemEntry : mapCut.entrySet()) {
                out = out + elemEntry.getKey() + " ";
                System.out.print(elemEntry.getKey() + " ");
                for (LinkedList<T> elem : elemEntry.getValue()) {
                    out = out + elem.toString() + "\n";
                    System.out.print(elem.toString() + " ");
                }
                out = out  +"Numero Tubolari:" +elemEntry.getValue().size() + "\n";
                    System.out.print("\n");
            }
        } else {
            throw new IllegalArgumentException();
        }
        return out;
    }

   
private LinkedList<LinkedList<T>> tubConfronto (Set<Tubolar<T>> tempList , int lenght ){
        LinkedList<LinkedList<T>> listList = new LinkedList<>();

        Set<Tubolar<T>> temp = new TreeSet<>(new Comparator<Tubolar<T>>() {

            @Override
            public int compare(Tubolar<T> o1, Tubolar<T> o2) {
                if ((int) o1.getLenght() < (int) o2.getLenght()) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });
        
        for(Tubolar<T> elemTubolar : tempList){
            temp.add(new Tubolar<T>(elemTubolar.getLenght(), elemTubolar.getQuantity()));
        }

        while (!temp.isEmpty()) {

            int total1 = lenght;
            LinkedList<T> list = new LinkedList<>();

            for (Tubolar<T> elem : temp) {

                while (elem.getQuantity() > 0 && ( total1 - (int) elem.getLenght()) >= 0) {
                    list.add(elem.getLenght());
                    elem.setQuantity(elem.getQuantity() - 1);
                    total1 = total1 - (int) elem.getLenght();
                }
            }
            for (var myIterator = temp.iterator(); myIterator.hasNext();) {
                if (myIterator.next().getQuantity() == 0) {
                    myIterator.remove();
                }
            }
            listList.add(list);
        }
        return listList;
    }

}
