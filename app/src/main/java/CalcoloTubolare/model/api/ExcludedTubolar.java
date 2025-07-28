package CalcoloTubolare.model.api;

/**
 * Enum representing different groups of merchandise for tubulars.
 * Each group corresponds to a specific type of tubular.
 */
public enum ExcludedTubolar {
    TBQ05022("Tubolare 50x50 sp2");

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
