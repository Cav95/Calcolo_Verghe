package p12.exercise;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import org.javatuples.Pair;

public class CalcolatorTubolar<T,Q> implements CalcolatorTubolarInterface<T, Q> {
    MultiQueue<Integer,String> multiQueue = new MultiQueueImpl<>();
    
    @Override
    public HashMap<Q, LinkedList<Pair<Integer, LinkedList<T>>>> calcoloTotal() {
        HashMap<Q, LinkedList<Pair<Integer, LinkedList<T>>>> mapCut = new HashMap<>();

        for (Entry<Q, Set<Tubolar<T>>> elemEntry : multiQueue.entrySet()) {
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
                for (Pair<Integer, LinkedList<T>> elem : elemEntry.getValue()) {
                    out = out + "Lunghezza tubolare:" + elem.getValue0() + "\n";
                    out = out + elem.getValue1().toString() + "\n";
                }
                out = out + "Numero Tubolari:" + elemEntry.getValue().size() + "\n";
            }
        } else {
            throw new IllegalArgumentException();
        }
        System.out.print(out);
        return out;
    }

    private LinkedList<Pair<Integer, LinkedList<T>>> tubConfronto(Set<Tubolar<T>> tempList, int lenght) {
        LinkedList<Pair<Integer, LinkedList<T>>> listList = new LinkedList<>();

        Set<Tubolar<T>> temp = new TreeSet<>((o1, o2) -> (int) o2.getLenght() - (int) o1.getLenght());

        for (Tubolar<T> elemTubolar : tempList) {
            temp.add(new Tubolar<T>(elemTubolar.getLenght(), elemTubolar.getQuantity()));
        }

        while (temp.stream().mapToInt( t -> t.getQuantity()).sum() != 0) {

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
            listList.add(pairList);
        }
        return listList;
    }
}
