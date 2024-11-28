package p12.exercise;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;
import org.javatuples.Pair;

public class MultiQueueImpl<T, Q> implements MultiQueue<T, Q> {

    HashMap<Q, Set<Tubolar<T>>> multiQueue = new HashMap<>();

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
                System.out.print(elemEntry.getKey() + " ");
                //System.out.println(elemEntry.getValue().stream().map( t -> " L=" + t.getLenght() +  " QT=" + t.getQuantity()).toList().toString());
                for (Tubolar<T> elem : elemEntry.getValue()) {
                    out = out + elem.toString() + " ";
                    System.out.print(elem.toString() + " ");
                }
                out = out + "\n";
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
    public HashMap<Q, LinkedList<Pair<Integer, LinkedList<T>>>> calcoloTotal() {
        HashMap<Q, LinkedList<Pair<Integer, LinkedList<T>>>> mapCut = new HashMap<>();

        for (Entry<Q, Set<Tubolar<T>>> elemEntry : multiQueue.entrySet()) {
            System.out.println(elemEntry.getKey());
            mapCut.put(elemEntry.getKey(), calcoloVerga(elemEntry.getKey(), 6000));

        }
        return mapCut;

    }

    @Override
    public LinkedList<Pair<Integer, LinkedList<T>>> calcoloVerga(Q queue, int lenght) {

        LinkedList<Pair<Integer, LinkedList<T>>> listShort = tubConfronto(multiQueue.get(queue), lenght);
        LinkedList<Pair<Integer, LinkedList<T>>> listLong = tubConfronto(multiQueue.get(queue), lenght * 2);

        return listShort.size() <= (listLong.size() * 2) ? listShort : listLong;

    }

    @Override
    public String printCuttedTubolar(HashMap<Q, LinkedList<Pair<Integer, LinkedList<T>>>> mapCut) {
        String out = "";
        if (!availableQueues().isEmpty()) {

            for (Entry<Q, LinkedList<Pair<Integer, LinkedList<T>>>> elemEntry : mapCut.entrySet()) {
                out = out + elemEntry.getKey() + "\n";
                System.out.print(elemEntry.getKey() + " ");

                for (Pair<Integer, LinkedList<T>> elem : elemEntry.getValue()) {
                    System.out.println(elem.getValue0());
                    out = out +"Lunghezza tubolare:" + elem.getValue0() + "\n";
                    out = out + elem.getValue1().toString() + "\n";
                    System.out.print(elem.getValue1().toString() + " ");
                }
                out = out + "Numero Tubolari:" + elemEntry.getValue().size() + "\n";
                System.out.print("\n");
            }
        } else {
            throw new IllegalArgumentException();
        }
        return out;
    }

    private LinkedList<Pair<Integer, LinkedList<T>>> tubConfronto(Set<Tubolar<T>> tempList, int lenght) {
        LinkedList<Pair<Integer, LinkedList<T>>> listList = new LinkedList<>();

        Set<Tubolar<T>> temp = new TreeSet<>((o1, o2) -> (int) o2.getLenght() - (int) o1.getLenght());

        for (Tubolar<T> elemTubolar : tempList) {
            temp.add(new Tubolar<T>(elemTubolar.getLenght(), elemTubolar.getQuantity()));
        }

        while (!temp.isEmpty()) {

            int total1 = lenght;

            LinkedList<T> list = new LinkedList<>();
            Pair<Integer, LinkedList<T>> pairList = new Pair<Integer, LinkedList<T>>(Integer.valueOf(lenght), list);

            for (Tubolar<T> elem : temp) {

                while (elem.getQuantity() > 0 && (total1 - (int) elem.getLenght()) >= 0) {
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
            listList.add(pairList);
        }
        return listList;
    }

}
