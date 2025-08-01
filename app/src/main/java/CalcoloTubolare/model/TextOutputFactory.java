package CalcoloTubolare.model;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;

import org.javatuples.Pair;

import CalcoloTubolare.controller.ControllerModel;
import CalcoloTubolare.model.api.ExcludedTubolar;
import CalcoloTubolare.model.api.NameTubolar;
import CalcoloTubolare.model.api.Tubolar;
import CalcoloTubolare.model.api.TubolarMultiList;

/**
 * TextOutputFactory class for generating text output for tubular cutting
 * calculations.
 */
public class TextOutputFactory {

    private static final int MM_TO_M = 1000;
    private static final String A_CAPO = "\n";
    private static final String TUBOLARE_UTILIZZATO = "Tubolare Utilizzato:";
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
    public static String cuttedTubolarExtended(HashMap<String, LinkedList<Pair<Integer, LinkedList<Integer>>>> mapCut,
            Optional<CollectorPeace> collector) {
        String out = "";
        if (!mapCut.keySet().isEmpty()) {

            for (var elemEntry : mapCut.entrySet()) {
                out = out + elemEntry.getKey() + getNameTubolar(elemEntry.getKey(), collector) + SEPARATOR
                        + NUMERO_TUBOLARI_TOTALI + elemEntry.getValue().size()
                        + A_CAPO;
                for (var elem : elemEntry.getValue()) {
                    out = out + LUNGHEZZA_VERGA + elem.getValue0() + A_CAPO;
                    out = out + VERGA_UTILIZZATA + elem.getValue1().stream().mapToInt(t -> t).sum() + A_CAPO;
                    out = out + LISTA_DI_TAGLIO + A_CAPO;
                    out = out
                            + elem.getValue1().stream()
                                    .map(t -> LUNGHEZZA_SINGOLO + t + SEP + NUMERO
                                            + elem.getValue1().stream().mapToInt(e -> e).filter(g -> g == t).sum() / t)
                                    .distinct().toList()
                            + "\n\n";
                }
                out = out + A_CAPO;
            }
        } else {
            throw new IllegalArgumentException();
        }
        out = out + noTubolarElement(collector);
        return out;
    }

    /**
     * Gets the list of the tubular cutted in ottimization mode.
     * 
     * @param mapCut    the map containing cutting results
     * @param collector the collector peace containing tubular and sample data
     * @return a formatted string representing the cutted tubulars in reduced form
     * 
     */
    public static String cuttedTubolarReduced(
            HashMap<String, LinkedList<Pair<Integer, LinkedList<Integer>>>> mapCut,
            Optional<CollectorPeace> collector) {
        String out = "";
        if (!mapCut.keySet().isEmpty()) {

            for (var elem : mapCut.entrySet()) {
                out = out + elem.getKey() + getNameTubolar(elem.getKey(), collector) + A_CAPO;

                out = out + LUNGHEZZA_VERGA + elem.getValue().getFirst().getValue0() + SEPARATOR
                        + NUMERO_TUBOLARI_TOTALI + elem.getValue().size() + SEPARATOR + TUBOLARE_UTILIZZATO
                        + elem.getValue().stream()
                                .mapToDouble(t -> t.getValue1().stream().mapToDouble(h -> h).sum() / MM_TO_M)
                                .sum()
                        + "m" + "\n\n";

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
     * @return a string containing the rules for using the Excel file.
     */
    public static String rulesOfUseExcel() {
        return "Il file Excel deve provenire dalla tabella SolidWork \n"
                + "Il file Excel deve avere le colonne: \n"
                + "Codice, Lunghezza, Quantità, Diametro, Spessore\n"
                + "In questo Ordine\n"
                + "Il file deve essere salvato con estenzione .xlsx\n";
    }

    /**
     * Generates a string output for the inserted tubulars in the multi-list.
     * 
     * @param multi     the TubolarMultiList containing tubular data
     * @param collector the optional CollectorPeace containing additional data
     * @return a formatted string representing the inserted tubulars
     */
    public static String tubolarInsertedOutput(TubolarMultiList multi, Optional<CollectorPeace> collector) {
        String out = "";
        if (!multi.availableQueues().isEmpty()) {
            for (Entry<String, Set<Tubolar>> elemEntry : multi.getMultiQueue().entrySet()) {
                out = out + elemEntry.getKey() + getNameTubolar(elemEntry.getKey(), collector) + A_CAPO;
                out = out + elemEntry.getValue().stream()
                        .map(t -> LUNGHEZZA_SINGOLO + t.getLenght() + " " + NUMERO + t.getQuantity())
                        .map(t -> "[ " + t + " ] ").map(t -> t.toUpperCase())
                        .distinct().reduce("", (a, b) -> a + b);
                out = out + "\n\n";
            }
        } else {
            throw new IllegalArgumentException();
        }
        out = out + noTubolarElement(collector);
        return out;
    }

    /**
     * Generates a reduced result string for the tubular cutting calculation.
     * 
     * @param siloCode   the code of the silo
     * @param optimal    whether the calculation is optimal
     * @param controller the ControllerModel containing calculation data
     * @return a formatted string representing the reduced result
     */
    public static String reducedResultString(String siloCode, Boolean optimal, ControllerModel controller,
            Integer numSilo) {
        return userName() + A_CAPO
                + siloPropretiesOutput(siloCode, numSilo) + A_CAPO
                + ottimalOutputString(optimal)
                + controller.partialCalcolateTubolar(optimal);
    }

    /**
     * Generates an extended result string for the tubular cutting calculation.
     * 
     * @param siloCode   the code of the silo
     * @param optimal    whether the calculation is optimal
     * @param controller the ControllerModel containing calculation data
     * @return a formatted string representing the extended result
     */
    public static String extendedResultString(String siloCode, Boolean optimal, ControllerModel controller,
            Integer numSilo) {
        return userName() + A_CAPO
                + siloPropretiesOutput(siloCode, numSilo) + A_CAPO
                + ottimalOutputString(optimal)
                + controller.totalCalcolateTubolar(optimal);
    }

    /**
     * Generate output for provider Confert.
     * 
     * @param controller
     * @return a formatted string representing the tubular data.
     */
    public static String confertOutPut(ControllerModel controller, String siloCode, int numSilo) {
        return controller.getCollector().isEmpty() ? ""
                : userName() + A_CAPO
                        + siloPropretiesOutput(siloCode, numSilo) + A_CAPO
                        + A_CAPO
                        + controller.getPeaceStream(numSilo)
                                .map(h -> h.description() + " (" + h.code() + ") " + SEPARATOR + QUANTITÀ
                                        + h.quantity()
                                        + SEPARATOR
                                        + LUNGHEZZA_SINGOLO + h.lenght() + A_CAPO)
                                .map(String::toUpperCase)
                                .sorted()
                                .distinct()
                                .reduce("", (a, b) -> a + b);
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

    private static String getNameTubolar(String codeTubolar, Optional<CollectorPeace> collector) {
        if (!collector.isEmpty()
                && collector.get().getTableSeampleList().stream()
                        .anyMatch(t -> t.code()
                                .equals(codeTubolar))) {
            return collector.get().getTableSeampleList().stream()
                    .filter(t -> t.code().equals(codeTubolar))
                    .map(t -> " (" + t.description())
                    .limit(1)
                    .reduce("", (a, b) -> a + b) + ")";
        } else {
            try {
                return " (" + Arrays.asList(NameTubolar.values()).stream().filter(t -> t.name().equals(codeTubolar))
                        .map(NameTubolar::getActualName)
                        .findFirst().get() + ")";
            } catch (Exception e) {
                System.out.println("NameTubolar not found for code: " + codeTubolar);
            }
            return "";
        }
    }

    private static String noTubolarElement(Optional<CollectorPeace> collector) {
        return collector.isEmpty() ? ""
                : collector.get().getTableSeampleList().stream()
                        .filter(h -> Arrays.asList(ExcludedTubolar.values()).stream()
                                .noneMatch(t -> h.code().contains(t.name())))
                        .filter(h -> h.lenght() == 0)
                        .map(h -> h.code() + " (" + h.description() + ") " + QUANTITÀ
                                + h.quantity() + A_CAPO + A_CAPO)
                        .reduce("", (a, b) -> a + b);

    }

    private static String numSilOuput(Integer numSilo) {
        return "Numero Silo: " + numSilo + A_CAPO;
    }

    private static String siloPropretiesOutput(String siloCode, Integer numSilo) {
        return structureCode(siloCode) + "  " + numSilOuput(numSilo);
    }

}
