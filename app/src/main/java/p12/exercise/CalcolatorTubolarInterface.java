package p12.exercise;

import java.util.HashMap;
import java.util.LinkedList;

import org.javatuples.Pair;

public interface CalcolatorTubolarInterface<T, Q> {

    HashMap<Q, LinkedList<Pair<Integer, LinkedList<T>>>> calcoloTotal();

    LinkedList<Pair<Integer, LinkedList<T>>> calcoloVerga(Q queue, int lenght);

    String printCuttedTubolar(HashMap<Q, LinkedList<Pair<Integer, LinkedList<T>>>> mapCut);

}