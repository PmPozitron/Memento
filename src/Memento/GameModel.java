/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Memento;

/**
 *
 * @author Pozitron
 */
public class GameModel extends java.util.Observable {
    public RandomSymbolsGenerator stringGenerator;
    private String symbolsSet;
    
    private int randomNumber;
    private String randomString = "";
    private int randomStringLength;
    private int gameDuration;
    private int showDuration;
    private int quantityOfShows;
    private int quantityOfCorrect;
    private int showsCounter; 
    private int result;
    
    private boolean gameOn;
    private boolean numberShown;
    private boolean userInputAwaited;    
    
    public GameModel () {
        gameDuration = 10000;   // not used yet;
        showDuration = 500;
        quantityOfShows = 20;
        quantityOfCorrect = 0;
        showsCounter = 0;
        
        symbolsSet = "";
        stringGenerator = new RandomSymbolsGenerator();
    }
    
    public GameModel (int _showDuration, int _quantityOfShows) {
        this.showDuration = _showDuration;  
        this.quantityOfShows = _quantityOfShows;
    }
    
    public String getCurrentSymbolsSet() {
        return this.symbolsSet;
    }
    
    public int getRandomStringLength () {
        return this.randomStringLength;
    }
    
    public void setRandomStringLength (int _value) {
        this.randomStringLength = _value;
    }
    
    public String getCurrentRandomString () {
        return this.randomString;
    }   
    
    public int getResult () {
        return this.result;
    }
    
    public void setResult () {
        this.result = this.getCurrentQtyOfCorrect();
    }
    
    public void resetResult () {
        this.resetQtyOfCorrect();
        this.result = 0;
    }
    
    public int getCurrentRandomNumber () {
        return this.randomNumber;
    }
    
    public int getGameDuration () {
        return this.gameDuration;
    }
    
    public int getShowDuration () {
        return this.showDuration;
    }
    
    public int getCurrentQtyOfShows () {
        return this.quantityOfShows;
    }
    
    public int getCurrentQtyOfCorrect () {
        return this.quantityOfCorrect;
    }
    
    public void setCurrentQtyOfCorrect () {
        this.quantityOfCorrect++;
    }
    
    public void resetQtyOfCorrect () {
        this.quantityOfCorrect = 0;
    }
    
    public int getShowsCounter () {
        return this.showsCounter;
    }
    
    public void setShowsCounter () {
        this.showsCounter++;

    }
    
    public void resetShowsCounter () {
        this.showsCounter = 0;       
    }
    
    public boolean isGameOn () {
        return this.gameOn;
    }
    
    public void setGameOn (boolean _gameOn) {
        this.gameOn = _gameOn;
    }
    
    public boolean isNumberShown () {
        return this.numberShown;
    }
    
    public void setNumberShown (boolean _isShown) {
        this.numberShown = _isShown;
    }
       
    // User Setting are being applied here
    public void initializeGame (int _showDuration, int _randomStringLength, boolean _useDigits, boolean _useLCCyrillic, 
            boolean _useUCCyrillic, boolean _useLiteUCCyrillic, boolean _useLCLatinic, boolean _useUCLatinic, boolean _usePunctuationMarks) {
        
        this.showDuration = _showDuration;
        //this.quantityOfShows = _qtyOfShows;
        this.randomStringLength = _randomStringLength;
        
        symbolsSet = "";
        if (_useDigits) { this.symbolsSet += RandomSymbolsGenerator.DIGITS_ONLY; }
        if (_useLCCyrillic) { this.symbolsSet += RandomSymbolsGenerator.CYRILLIC_LOWER_CASE; }
        if (_useUCCyrillic) { this.symbolsSet += RandomSymbolsGenerator.CYRYLLIC_UPPER_CASE; }
        
        if(_useLiteUCCyrillic) { this.symbolsSet += RandomSymbolsGenerator.CYRYLLIC_LITE_UPPER_CASE; }
        
        if (_useLCLatinic) { this.symbolsSet += RandomSymbolsGenerator.LATINIC_LOWER_CASE; }
        if (_useUCLatinic) { this.symbolsSet += RandomSymbolsGenerator.LATINIC_UPPER_CASE; }
        if (_usePunctuationMarks) { this.symbolsSet += RandomSymbolsGenerator.PUNCTUATION_MARKS; }
        
        this.stringGenerator = new RandomSymbolsGenerator (this.symbolsSet);
        
    }
    
    public void showNextRandom () {
        
    }
    
    public void startGame () {  
        resetResult();
        System.out.println("Game started");
        generateString(this.randomStringLength);       
        setNumberShown(true);
        requestNotify();
    }
    
    public void endGame () {
        this.setGameOn(false);
        this.resetShowsCounter();
        this.setResult();
        this.requestNotify();
    
    }
        
    public int generateNumber () {
        StringBuilder _randomString = new StringBuilder ();
        for (int i = 0; i < 7; i++) {
            _randomString.append(Integer.toString((int)(Math.random()*10)));
        }
        //this.randomNumber = (int)(Math.random() * 10000000);     // 7 digits awaited
        this.randomNumber = Integer.parseInt(_randomString.toString());
        this.setShowsCounter();        
        return randomNumber;
    }
    
    public String generateString (int _randomStringLength) {
        this.randomString = this.stringGenerator.generateRandomString(_randomStringLength);
        //this.setShowsCounter(); 
        System.out.println("New random string is " + this.randomString);
        return this.randomString;
    }
        
    public void compareNums (int numToCompare) {
        System.out.println(this.getShowsCounter());
        if (numToCompare == this.randomNumber) {
            this.setCurrentQtyOfCorrect();            
        }   
//        if (this.getShowsCounter() == this.quantityOfShows) {
//            this.endGame();
//        }        
    }
    
    public void compareStrings (String _stringToCompare) {
        if (this.randomString.equals(_stringToCompare)) {
            this.setCurrentQtyOfCorrect();
        }
        if (this.getShowsCounter() == this.quantityOfShows) {
            this.endGame();
        }
        
    }
    
    public void calculateResult () {
        this.result = this.quantityOfCorrect;
    }
    
    public void requestNotify () {
        setChanged();
        notifyObservers();
    }
}
