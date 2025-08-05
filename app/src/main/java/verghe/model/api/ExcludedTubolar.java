package verghe.model.api;

/**
 * Enum representing different groups of merchandise for tubulars.
 * Each group corresponds to a specific type of tubular to be excluded from
 * calculate.
 */
public enum ExcludedTubolar {
    TBQ05002("Tubolare 50x50 sp2"),
    TBQ05004("Tubolare 50x50 sp4"),
    TBQ03003("TUBOLARE 30x30 sp3"),
    TBQ03002("TUBOLARE 30x30 sp2");

    ExcludedTubolar(String actualName) {
        this.actualName = actualName;
    }

    private final String actualName;

    /**
     * Gets the name of the tubular.
     * 
     * @return the name of the tubular.
     */
    public String getactualName() {
        return actualName;
    }
}
