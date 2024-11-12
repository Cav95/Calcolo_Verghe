package p12.exercise;

public enum NameTubolar {
    TBQ15023("Tubolare 150x150 sp3 "),
    TBQ14023("Tubolare 140x140 sp3 "),
    TBQ12023("Tubolare 120x120 sp3 ");

    private final String actualName ;
    private final int num = 3;

    
        public int getNum() {
          return num;
           }
           
        public String getactualName() {
            return actualName;
             }
       
    
        NameTubolar(String actualName) {
            this . actualName = actualName ;
        } 
    
}
