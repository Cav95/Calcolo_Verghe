package p12.exercise;

public enum Nametubolar {
    TBQ15023("Tubolare 150x150 sp3 "),
    TBQ14023("Tubolare 140x140 sp3 "),
    TBQ12023("Tubolare 120x120 sp3 ");

    private final String actualName ;
    
        Nametubolar(String actualName) {
            this . actualName = actualName ;
        }

        public String getName () {
            return this . actualName ;
             }  
    
}
