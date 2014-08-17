/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Memento;

/**
 *
 * @author Pozitron
 */
public class RandomSymbolsGenerator {
    private String randomString;
    private String symbolsSet;
    
    public final static String DIGITS_ONLY = "0123456789";
    public final static String LATINIC_LOWER_CASE = "abcdefghijklmnopqrstuvwxyz";
    public final static String LATINIC_UPPER_CASE = "ABDCEFGHIJKLMNOPQRSTUVWXYZ";
    public final static String CYRILLIC_LOWER_CASE = "àáâãäå¸æçèéêëìíîïğñòóôõö÷øùúûüışÿ";
    public final static String CYRYLLIC_UPPER_CASE = "ÀÁÂÃÄÅ¨ÆÇÈÉÊËÌÍÎÏĞÑÒÓÔÕÖ×ØÙÚÛÜİŞß";
    public final static String CYRYLLIC_LITE_UPPER_CASE = "ÀÁÂÃÄÅÆÇÈÊËÌÍÎÏĞÑÒÓÔÕÖ×ØÛÚİŞß";
    public final static String PUNCTUATION_MARKS = ".!?&,;:-()";
    
    public RandomSymbolsGenerator () {
        this.symbolsSet = RandomSymbolsGenerator.CYRILLIC_LOWER_CASE;
    }
    
    public RandomSymbolsGenerator (String... args) {
        if (args == null) {
            System.out.println("Null args array");
            return;
        }
        this.symbolsSet = "";
        for (String arg : args) {
            if (arg == null) {
                System.out.println("Null arg in array");
                return;
            }                
            this.symbolsSet += arg;
        }
    }
    
    public RandomSymbolsGenerator (String _symbolsSet) {
        this.symbolsSet = _symbolsSet;
    }
    
    public String generateRandomString (int randomStringLength) {
        randomString = "";
        char randomChar;
        //int randomIndex;
        for (int i = 0; i < randomStringLength; i++) {
            int randomIndex = (int)(Math.random() * this.symbolsSet.length());
            randomChar = this.symbolsSet.charAt(randomIndex);
            this.randomString += randomChar;           
            //this.randomString += randomIndex;
            //System.out.println("Method 'generateRandomString' gives a randomIndex = " + randomIndex);
        }
        
        return randomString;
        
    }
}
