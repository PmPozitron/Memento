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
    private GameModel game;
    private MementoView view;
    private int inputNumber;
    private String inputString;
    
    private int showDuration;
    private int quantityOfShows;
    private int randomStringLength;
    private boolean useDigits;
    private boolean useLowerCaseCyrillic;
    private boolean useUpperCaseCyrillic;
    
    private boolean useLiteUpperCaseCyrillic;
    
    private boolean useLowerCaseLatinic;
    private boolean useUpperCaseLatinic;
    private boolean usePunctiationMarks;
    
    public GameController (GameModel _game) {
        this.game = _game;
        view = new MementoView (this.getGame(), this);
        view.createView();
        
        //game.initializeGame();        
    }
    
    public void readSettings () {
        this.setShowDuration(Integer.parseInt(this.getView().getInputShowDuration().getText()));
       //this.quantityOfShows = Integer.parseInt(this.view.inputQuantityOfShows.getText());
        this.setRandomStringLength(Integer.parseInt(this.getView().getInputStringLength().getText()));
        this.setUseDigits(this.getView().getUseDigits().getState());
        this.setUseLowerCaseCyrillic(this.getView().getUseLCCyryllic().getState());
        this.setUseUpperCaseCyrillic(this.getView().getUseUCCyrillic().getState());
       
        this.setUseLiteUpperCaseCyrillic(this.getView().getUseLiteUCCyryllic().getState());
       
        this.setUseLowerCaseLatinic(this.getView().getUseLCLatinic().getState());
        this.setUseUpperCaseLatinic(this.getView().getUseUCLatinic().getState());
        this.setUsePunctiationMarks(this.getView().getUsePunctuationMarks().getState());
       //this.randomStringLength = Integer.parseInt(this.view.inputStringLength.getText());
    }
    
    public void startGame () {
        readSettings();             
        this.getGame().initializeGame(this.getShowDuration(), this.getRandomStringLength(), 
                this.isUseDigits(), this.isUseLowerCaseCyrillic(), this.isUseUpperCaseCyrillic(), this.isUseLiteUpperCaseCyrillic(), 
                this.isUseLowerCaseLatinic(), this.isUseUpperCaseLatinic(), this.isUsePunctiationMarks());
        //this.game.startGame();
        //System.out.println("Wow");
        this.getView().getTimer().setInitialDelay(this.getShowDuration());
        this.getGame().setGameOn(true);     
        this.getGame().startGame();
        
    }     
    
    public void receiveUserInput (String _inputString) {
        //this.inputNumber = _inputNumber;
        this.setInputString(_inputString);
        //this.game.compareNums(this.inputNumber);
        //this.game.compareStrings(_inputString);                                           // straightforward comparison of random string with user input
        this.getGame().compareStrings(new StringBuffer(_inputString).reverse().toString());      // reverse and default mode
        //this.game.generateNumber();  
        this.getGame().setShowsCounter();
        if (getGame().getShowsCounter() == getGame().getCurrentQtyOfShows()) {
            getGame().endGame();
        } else {
            this.getGame().generateString(this.getRandomStringLength());
            this.getGame().requestNotify();
        }           
    }
    
    // Obsolete
    public void readUserInput() {
        this.getView().getInputField().getText();
    }   

    public GameModel getGame() {
        return game;
    }

    public void setGame(GameModel game) {
        this.game = game;
    }

    public MementoView getView() {
        return view;
    }

    public void setView(MementoView view) {
        this.view = view;
    }

    public int getInputNumber() {
        return inputNumber;
    }

    public void setInputNumber(int inputNumber) {
        this.inputNumber = inputNumber;
    }

    public String getInputString() {
        return inputString;
    }

    public void setInputString(String inputString) {
        this.inputString = inputString;
    }

    public int getShowDuration() {
        return showDuration;
    }

    public void setShowDuration(int showDuration) {
        this.showDuration = showDuration;
    }

    public int getQuantityOfShows() {
        return quantityOfShows;
    }

    public void setQuantityOfShows(int quantityOfShows) {
        this.quantityOfShows = quantityOfShows;
    }

    public int getRandomStringLength() {
        return randomStringLength;
    }

    public void setRandomStringLength(int randomStringLength) {
        this.randomStringLength = randomStringLength;
    }

    public boolean isUseDigits() {
        return useDigits;
    }

    public void setUseDigits(boolean useDigits) {
        this.useDigits = useDigits;
    }

    public boolean isUseLowerCaseCyrillic() {
        return useLowerCaseCyrillic;
    }

    public void setUseLowerCaseCyrillic(boolean useLowerCaseCyrillic) {
        this.useLowerCaseCyrillic = useLowerCaseCyrillic;
    }

    public boolean isUseUpperCaseCyrillic() {
        return useUpperCaseCyrillic;
    }

    public void setUseUpperCaseCyrillic(boolean useUpperCaseCyrillic) {
        this.useUpperCaseCyrillic = useUpperCaseCyrillic;
    }

    public boolean isUseLiteUpperCaseCyrillic() {
        return useLiteUpperCaseCyrillic;
    }

    public void setUseLiteUpperCaseCyrillic(boolean useLiteUpperCaseCyrillic) {
        this.useLiteUpperCaseCyrillic = useLiteUpperCaseCyrillic;
    }

    public boolean isUseLowerCaseLatinic() {
        return useLowerCaseLatinic;
    }

    public void setUseLowerCaseLatinic(boolean useLowerCaseLatinic) {
        this.useLowerCaseLatinic = useLowerCaseLatinic;
    }

    public boolean isUseUpperCaseLatinic() {
        return useUpperCaseLatinic;
    }

    public void setUseUpperCaseLatinic(boolean useUpperCaseLatinic) {
        this.useUpperCaseLatinic = useUpperCaseLatinic;
    }

    public boolean isUsePunctiationMarks() {
        return usePunctiationMarks;
    }

    public void setUsePunctiationMarks(boolean usePunctiationMarks) {
        this.usePunctiationMarks = usePunctiationMarks;
    }
}
