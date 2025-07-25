package CalcoloTubolare.model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;

import org.javatuples.Pair;

import CalcoloTubolare.controller.ControllerModel;
import CalcoloTubolare.model.api.Tubolar;
import CalcoloTubolare.model.api.TubolarMultiList;

/**
 * TextOutputFactory class for generating text output for tubular cutting
 * calculations.
 */
public class TextOutputFactory {

    private static final String QUANTITÀ = " Quantità=";
    private static final String CODICE_DELLA_STRUTURA = "Codice della strutura: ";
    private static final String CASO_PESSIMO_TUBOLARI_SOLO_6MT = "Con solo verghe da 6mt \n\n";
    private static final String CASO_OTTIMO_TUBOLARI_12M_6MT = "Con verghe da 12m\\6mt\n\n";
    private static final String LISTA_DI_TAGLIO = "Lista di taglio:";
    private static final String VERGA_UTILIZZATA = "Somma Millimetri Utilizzati: ";
    private static final String NUMERO = "Q.TA=";
    private static final String SEP = "-";
    private static final String LUNGHEZZA_SINGOLO = "L=";
    private static final String LUNGHEZZA_VERGA = "Lunghezza Verga:";
    private static final String NUMERO_TUBOLARI_TOTALI = "Quantità Verghe:";
    private static final String SEPARATOR = " -> ";

    /**
     * Prints the cutted tubulars in a formatted string.
     * 
     * @param mapCut    the map containing cutting results
     * @param collector the collector peace containing tubular and sample data
     * @return a formatted string representing the cutted tubulars
     */
    public static String printCuttedTubolar(HashMap<String, LinkedList<Pair<Integer, LinkedList<Integer>>>> mapCut,
            Optional<CollectorPeace> collector) {
        String out = "";
        if (!mapCut.keySet().isEmpty()) {

            for (var elemEntry : mapCut.entrySet()) {
                out = out + elemEntry.getKey() + getNameTubolar(elemEntry.getKey(), collector) + SEPARATOR
                        + NUMERO_TUBOLARI_TOTALI + elemEntry.getValue().size()
                        + "\n";
                for (var elem : elemEntry.getValue()) {
                    out = out + LUNGHEZZA_VERGA + elem.getValue0() + "\n";
                    out = out + VERGA_UTILIZZATA + elem.getValue1().stream().mapToInt(t -> t).sum() + "\n";
                    out = out + LISTA_DI_TAGLIO + "\n";
                    out = out
                            + elem.getValue1().stream()
                                    .map(t -> LUNGHEZZA_SINGOLO + t + SEP + NUMERO
                                            + elem.getValue1().stream().mapToInt(e -> e).filter(g -> g == t).sum() / t)
                                    .distinct().toList()
                            + "\n\n";
                }
                out = out + "\n";
            }
        } else {
            throw new IllegalArgumentException();
        }
        out = out + noTubolarElement(collector);
        System.out.print(out);
        return out;
    }

    /**
     * Gets the name of the tubular based on its code.
     * 
     * @param codeTubolar the code of the tubular
     * @param collector   the collector peace containing tubular data
     * @return a string representing the name of the tubular
     */
    public static String printCuttedTubolarReduced(
            HashMap<String, LinkedList<Pair<Integer, LinkedList<Integer>>>> mapCut,
            Optional<CollectorPeace> collector) {
        String out = "";
        if (!mapCut.keySet().isEmpty()) {

            for (var elem : mapCut.entrySet()) {
                out = out + elem.getKey() + getNameTubolar(elem.getKey(), collector) + "\n";

                out = out + LUNGHEZZA_VERGA + elem.getValue().getFirst().getValue0() + "\n";

                out = out + NUMERO_TUBOLARI_TOTALI + elem.getValue().size() + "\n\n";
            }
        } else {
            throw new IllegalArgumentException();
        }
        out = out + noTubolarElement(collector);
        System.out.print(out);
        return out;
    }

    /**
     * Provides rules for using the Excel file in the application.
     * 
     * @return a string containing the rules for using the Excel file
     */
    public static String rulesOfUseExcel() {
        return "Il file Excel deve provenire dalla tabella SolidWork \n"
                + "Il file Excel deve avere le colonne: \n"
                + "Codice, Lunghezza, Quantità, Diametro, Spessore\n"
                + "In questo Ordine\n"
                + "Il file deve essere salvato con estenzione .xlsx\n";
    }

    private static String getNameTubolar(String codeTubolar, Optional<CollectorPeace> collector) {
        if (!collector.isEmpty()
                && collector.get().getTableTubolarList().stream().anyMatch(t -> t.getValue0().equals(codeTubolar))) {
            String ret = collector.get().getTableTubolarList().stream().filter(t -> t.getValue0() == codeTubolar)
                    .map(t -> " (" + t.getValue1()).toList().getFirst() + ")";
            return ret;
        } else {
            return "";
        }
    }

    private static String noTubolarElement(Optional<CollectorPeace> collector) {
        String out = "";
        if (!collector.isEmpty()) {
            List<String> ret = collector.get().getTableSeampleList().stream()
                    .map(t -> t.code() + " (" + t.description() + ") " + QUANTITÀ + t.quantity() + " \n").toList();
            for (var elem : ret) {
                out = out + elem;

            }
            return out;
        } else {
            return out;
        }
    }

    public static String printAllQueue(TubolarMultiList multi, Optional<CollectorPeace> collector) {
        String out = "";
        if (!multi.availableQueues().isEmpty()) {
            for (Entry<String, Set<Tubolar>> elemEntry : multi.getMultiQueue().entrySet()) {
                out = out + elemEntry.getKey() + getNameTubolar(elemEntry.getKey(), collector) + "\n";
                out = out + elemEntry.getValue().stream().map(t -> " L=" + t.getLenght() + " QT=" + t.getQuantity())
                        .distinct().reduce("", (a, b) -> a + b);
                out = out + "\n\n";
            }
        } else {
            throw new IllegalArgumentException();
        }
        out = out + noTubolarElement(collector);
        System.out.print(out);
        return out;
    }

    private static String ottimalOutputString(Boolean optimal) {
        return optimal ? CASO_OTTIMO_TUBOLARI_12M_6MT : CASO_PESSIMO_TUBOLARI_SOLO_6MT;

    }

    private static String structureCode(String codeSilo) {
        return codeSilo.isBlank() ? "" : CODICE_DELLA_STRUTURA + codeSilo + " ";

    }

    private static String userName() {
        return "Autore: " + System.getProperty("user.name") + " " + LocalDate.now();
    }

    public static String reducedResultString(String siloCode, Boolean optimal, ControllerModel controller) {
        return userName() + "\n" + structureCode(siloCode) + "\n" + ottimalOutputString(optimal)
                + controller.partialCalcolateTubolar(optimal);
    }

    public static String extendedResultString(String siloCode, Boolean optimal, ControllerModel controller) {
        return userName() + "\n" + structureCode(siloCode) + "\n" + ottimalOutputString(optimal)
                + controller.totalCalcolateTubolar(optimal);
    }

}
