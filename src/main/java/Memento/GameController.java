/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Memento;

/**
 *
 * @author Pozitron
 */
public class GameController {
    GameModel game;
    MementoView view;
    int inputNumber;
    String inputString;
    
    int showDuration;
    int quantityOfShows;
    int randomStringLength;
    boolean useDigits;
    boolean useLowerCaseCyrillic;
    boolean useUpperCaseCyrillic;
    
    boolean useLiteUpperCaseCyrillic;
    
    boolean useLowerCaseLatinic;
    boolean useUpperCaseLatinic;
    boolean usePunctiationMarks;
    
    public GameController (GameModel _game) {
        this.game = _game;
        view = new MementoView (this.game, this);
        view.createView();
        
        //game.initializeGame();        
    }
    
    public void readSettings () {
       this.showDuration = Integer.parseInt(this.view.inputShowDuration.getText());
       //this.quantityOfShows = Integer.parseInt(this.view.inputQuantityOfShows.getText());
       this.randomStringLength = Integer.parseInt(this.view.inputStringLength.getText());
       this.useDigits = this.view.useDigits.getState();
       this.useLowerCaseCyrillic = this.view.useLCCyryllic.getState();
       this.useUpperCaseCyrillic = this.view.useUCCyrillic.getState();
       
       this.useLiteUpperCaseCyrillic = this.view.useLiteUCCyryllic.getState();
       
       this.useLowerCaseLatinic = this.view.useLCLatinic.getState();
       this.useUpperCaseLatinic = this.view.useUCLatinic.getState();
       this.usePunctiationMarks = this.view.usePunctuationMarks.getState();
       //this.randomStringLength = Integer.parseInt(this.view.inputStringLength.getText());
    }
    
    public void startGame () {
        readSettings();             
        this.game.initializeGame(this.showDuration, this.randomStringLength, this.useDigits, this.useLowerCaseCyrillic, 
                this.useUpperCaseCyrillic, this.useLiteUpperCaseCyrillic, this.useLowerCaseLatinic, this.useUpperCaseLatinic, this.usePunctiationMarks);
        //this.game.startGame();
        //System.out.println("Wow");
        this.view.timer.setInitialDelay(this.showDuration);
        this.game.setGameOn(true);     
        this.game.startGame();
        
    }     
    
    public void receiveUserInput (String _inputString) {
        //this.inputNumber = _inputNumber;
        this.inputString = _inputString;
        //this.game.compareNums(this.inputNumber);
        //this.game.compareStrings(_inputString);                                           // straightforward comparison of random string with user input
        this.game.compareStrings(new StringBuffer(_inputString).reverse().toString());      // reverse and default mode
        //this.game.generateNumber();  
        this.game.setShowsCounter();
        if (game.getShowsCounter() == game.getCurrentQtyOfShows()) {
            game.endGame();
        } else {
            this.game.generateString(this.randomStringLength);
            this.game.requestNotify();
        }           
    }
    
    // Obsolete
    public void readUserInput() {
        this.view.inputField.getText();
    }   
}
