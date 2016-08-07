/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Memento;

import java.util.Observer;
import java.util.Observable;
import javax.swing.*;
import javax.swing.Action;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Pozitron
 */
public class MementoView implements Observer {
    private GameModel game;
    private GameController controller;
    
    private JFrame mainWindow;
    private Container container;
    private Dimension mainWindowDimension;
    private BorderLayout layout;
    
    private JTextField outputField;
    private JTextField inputField;
    private JTextField inputShowDuration;       
    //JTextField inputQuantityOfShows; 
    private JTextField inputStringLength;
    private InputFieldListener inputListener;
    
    private JButton startButton;
    private StartButtonListener startListener;  
    
    //разделители меню объявляются и инициализируются в методе createMenu ();
    private JMenuBar menuBar;
    private JMenu fontSettings;
    private JMenu symbolsSettings;
    private JMenuItem fontChooser;
    private JCheckBoxMenuItem useDigits;
    private JCheckBoxMenuItem useLCCyryllic;
    private JCheckBoxMenuItem useUCCyrillic;
    private JCheckBoxMenuItem useLiteUCCyryllic;
    private JCheckBoxMenuItem useLCLatinic;
    private JCheckBoxMenuItem useUCLatinic;
    private JCheckBoxMenuItem usePunctuationMarks;    

    private Highlighter lighter;
    private Highlighter.HighlightPainter yellowPainter;
    
    private Timer timer;
    private TimerListener timerListener;
    
    private int inputNumber;
    private String inputString;
    
    private Font bigOutputFont;
    
    public MementoView (GameModel _game, GameController _controller) {
        this.game = _game;
        this.controller = _controller;
        game.addObserver(this);
        this.timerListener = new TimerListener ();
        this.timer = new Timer (5000, getTimerListener());
    }
    
    @Override
    public void update (Observable observable, Object arg) {
        if (this.getGame() != null && this.getGame().isGameOn()) {
            //this.outputField.setText(Integer.toString(this.game.getCurrentRandomNumber()));
            //showNumber(this.game.getCurrentRandomNumber());
            showString(this.getGame().getCurrentRandomString());
            highlight();            
            getTimer().restart();            // // начинаем отсчет (именно restart'ом)
            highlight();
            //timer = null;            
            
        } else if (this.getGame() != null  && !this.game.isGameOn()) {
            int result = this.getGame().getResult();
            JOptionPane.showMessageDialog(MementoView.this.getContainer(), result + " correct from " + this.getGame().getCurrentQtyOfShows());            
        }
    }
        
    public void createView () {
        createMainWindow();        
        createTextFields();
        createButtons(); 
        createMenu();
        fillTheContainer();        
        highlight();      
        getMainWindow().setVisible(true);        
    }
    
    public void createMainWindow () {
        setMainWindow(new JFrame());
        setMainWindowDimension(new Dimension (480, 160));            
        getMainWindow().setSize(getMainWindowDimension());
        getMainWindow().setTitle("Memento");
        getMainWindow().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContainer(getMainWindow().getContentPane());
        setLayout(new BorderLayout());
        
    }
    
    // highlighter for outputfield is been initialized here
    public void createTextFields () {
        setOutputField(new JTextField ("Number_To_Be_Generated"));           
        getOutputField().setEditable(false);        
        getOutputField().setHorizontalAlignment(JTextField.CENTER);
        setBigOutputFont(new Font ("Arial", Font.PLAIN, 26));
        getOutputField().setFont(getBigOutputFont());
        setInputField(new JTextField ("Input_Your_Guess_Here"));
        getInputField().setHorizontalAlignment(JTextField.CENTER);
        setInputListener(new InputFieldListener());
        getInputField().selectAll();         
        getInputField().requestFocus();         
        getInputField().addActionListener(getInputListener());
        setInputShowDuration(new JTextField ("500"));
        getInputShowDuration().setHorizontalAlignment(JTextField.CENTER);
        setInputStringLength(new JTextField ("7"));
        getInputStringLength().setHorizontalAlignment(JTextField.CENTER); 
        setLighter(getOutputField().getHighlighter());
        setYellowPainter(new DefaultHighlighter.DefaultHighlightPainter(Color.yellow)); 
    }
    
    public void createButtons () {                          // only one button
        setStartButton(new JButton ("Start"));
        setStartListener(new StartButtonListener ());
        getStartButton().addActionListener(getStartListener());        
    }
    
    public void createMenu () {
        setMenuBar(new JMenuBar ());
        setFontSettings(new JMenu ("Font Settings"));
        JSeparator menuSeparator = new JSeparator ();
        setSymbolsSettings(new JMenu ("Symbols Settings"));     
        setFontChooser(new JMenuItem ("Choose Font"));
        ChooseFontListener chooseFontListener = new ChooseFontListener ();
        getFontChooser().addActionListener(chooseFontListener);
        getFontSettings().add(getFontChooser());
        setUseDigits(new JCheckBoxMenuItem ("Use Digits"));
//        useDigits.setState(true);                       // default mode is digits only
        setUseLCCyryllic(new JCheckBoxMenuItem ("Use LowerCase Cyrillic"));
        setUseUCCyrillic(new JCheckBoxMenuItem ("Use UpperCase Cyrillic"));
        
        setUseLiteUCCyryllic(new JCheckBoxMenuItem ("Use Lite UpperCase Cyrillic"));
        getUseLiteUCCyryllic().setState(true);                   // new default mode
        
        setUseLCLatinic(new JCheckBoxMenuItem ("Use LowerCase Latinic"));
        setUseUCLatinic(new JCheckBoxMenuItem ("Use UpperCase Latinic"));
        setUsePunctuationMarks(new JCheckBoxMenuItem ("Use Punctuation marks"));
        getSymbolsSettings().add(getUseDigits());
        getSymbolsSettings().add(getUseLCCyryllic());
        getSymbolsSettings().add(getUseUCCyrillic());
        
        getSymbolsSettings().add(getUseLiteUCCyryllic());
        
        getSymbolsSettings().add(getUseLCLatinic());
        getSymbolsSettings().add(getUseUCLatinic());
        getSymbolsSettings().add(getUsePunctuationMarks());
        getMenuBar().add(getFontSettings());
        getMenuBar().add(menuSeparator);
        getMenuBar().add(getSymbolsSettings());
        
    }
    
    public void fillTheContainer () {
        getContainer().add(getOutputField(), BorderLayout.NORTH);
        getContainer().add(getInputField(), BorderLayout.SOUTH);
        getContainer().add(getInputShowDuration(), BorderLayout.WEST);
        //container.add(inputQuantityOfShows, BorderLayout.EAST);
        getContainer().add(getInputStringLength(), BorderLayout.EAST);
        getContainer().add(getStartButton(), BorderLayout.CENTER);  
        getMainWindow().setJMenuBar(getMenuBar());
        
    }        
    
    public void highlight () {
        // highlight either one central symbol (if string length is odd) or two (or generate exception)        
        try {
            if (getOutputField().getText().length() % 2 == 0) {
                getLighter().addHighlight(getOutputField().getText().length() / 2 - 1, getOutputField().getText().length() / 2 + 1, getYellowPainter()); 
            } else if (getOutputField().getText().length () % 2 != 0) {
                getLighter().addHighlight(getOutputField().getText().length() / 2, getOutputField().getText().length() / 2 + 1, getYellowPainter());
            } else throw new BadLocationException ("error trying to highlight outputField", 0);
        } catch (BadLocationException ex) {
            System.out.println(ex.toString());
        }
        getInputField().requestFocus();
        
    }
    
    // Obsolete
    public void showNumber (int _numberToShow) {
        this.getOutputField().setText(Integer.toString(_numberToShow));  
        highlight();
    }
    
    public void showString (String _stringToShow) {
        this.getOutputField().setText(_stringToShow);
    }
    
    public void hideNumber () {
        this.getOutputField().setText("  ");
        highlight();
    }
    
    public void readNumber () {
        try {
            setInputNumber(Integer.parseInt(getInputField().getText()));
        }
        catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }
    
    public void readString () {
        this.setInputString(this.getInputField().getText());
    }

    public GameModel getGame() {
        return game;
    }

    public void setGame(GameModel game) {
        this.game = game;
    }

    public GameController getController() {
        return controller;
    }

    public void setController(GameController controller) {
        this.controller = controller;
    }

    public JFrame getMainWindow() {
        return mainWindow;
    }

    public void setMainWindow(JFrame mainWindow) {
        this.mainWindow = mainWindow;
    }

    public Container getContainer() {
        return container;
    }

    public void setContainer(Container container) {
        this.container = container;
    }

    public Dimension getMainWindowDimension() {
        return mainWindowDimension;
    }

    public void setMainWindowDimension(Dimension mainWindowDimension) {
        this.mainWindowDimension = mainWindowDimension;
    }

    public BorderLayout getLayout() {
        return layout;
    }

    public void setLayout(BorderLayout layout) {
        this.layout = layout;
    }

    public JTextField getOutputField() {
        return outputField;
    }

    public void setOutputField(JTextField outputField) {
        this.outputField = outputField;
    }

    public JTextField getInputField() {
        return inputField;
    }

    public void setInputField(JTextField inputField) {
        this.inputField = inputField;
    }

    public JTextField getInputShowDuration() {
        return inputShowDuration;
    }

    public void setInputShowDuration(JTextField inputShowDuration) {
        this.inputShowDuration = inputShowDuration;
    }

    public JTextField getInputStringLength() {
        return inputStringLength;
    }

    public void setInputStringLength(JTextField inputStringLength) {
        this.inputStringLength = inputStringLength;
    }

    public InputFieldListener getInputListener() {
        return inputListener;
    }

    public void setInputListener(InputFieldListener inputListener) {
        this.inputListener = inputListener;
    }

    public JButton getStartButton() {
        return startButton;
    }

    public void setStartButton(JButton startButton) {
        this.startButton = startButton;
    }

    public StartButtonListener getStartListener() {
        return startListener;
    }

    public void setStartListener(StartButtonListener startListener) {
        this.startListener = startListener;
    }

    public JMenuBar getMenuBar() {
        return menuBar;
    }

    public void setMenuBar(JMenuBar menuBar) {
        this.menuBar = menuBar;
    }

    public JMenu getFontSettings() {
        return fontSettings;
    }

    public void setFontSettings(JMenu fontSettings) {
        this.fontSettings = fontSettings;
    }

    public JMenu getSymbolsSettings() {
        return symbolsSettings;
    }

    public void setSymbolsSettings(JMenu symbolsSettings) {
        this.symbolsSettings = symbolsSettings;
    }

    public JMenuItem getFontChooser() {
        return fontChooser;
    }

    public void setFontChooser(JMenuItem fontChooser) {
        this.fontChooser = fontChooser;
    }

    public JCheckBoxMenuItem getUseDigits() {
        return useDigits;
    }

    public void setUseDigits(JCheckBoxMenuItem useDigits) {
        this.useDigits = useDigits;
    }

    public JCheckBoxMenuItem getUseLCCyryllic() {
        return useLCCyryllic;
    }

    public void setUseLCCyryllic(JCheckBoxMenuItem useLCCyryllic) {
        this.useLCCyryllic = useLCCyryllic;
    }

    public JCheckBoxMenuItem getUseUCCyrillic() {
        return useUCCyrillic;
    }

    public void setUseUCCyrillic(JCheckBoxMenuItem useUCCyrillic) {
        this.useUCCyrillic = useUCCyrillic;
    }

    public JCheckBoxMenuItem getUseLiteUCCyryllic() {
        return useLiteUCCyryllic;
    }

    public void setUseLiteUCCyryllic(JCheckBoxMenuItem useLiteUCCyryllic) {
        this.useLiteUCCyryllic = useLiteUCCyryllic;
    }

    public JCheckBoxMenuItem getUseLCLatinic() {
        return useLCLatinic;
    }

    public void setUseLCLatinic(JCheckBoxMenuItem useLCLatinic) {
        this.useLCLatinic = useLCLatinic;
    }

    public JCheckBoxMenuItem getUseUCLatinic() {
        return useUCLatinic;
    }

    public void setUseUCLatinic(JCheckBoxMenuItem useUCLatinic) {
        this.useUCLatinic = useUCLatinic;
    }

    public JCheckBoxMenuItem getUsePunctuationMarks() {
        return usePunctuationMarks;
    }

    public void setUsePunctuationMarks(JCheckBoxMenuItem usePunctuationMarks) {
        this.usePunctuationMarks = usePunctuationMarks;
    }

    public Highlighter getLighter() {
        return lighter;
    }

    public void setLighter(Highlighter lighter) {
        this.lighter = lighter;
    }

    public Highlighter.HighlightPainter getYellowPainter() {
        return yellowPainter;
    }

    public void setYellowPainter(Highlighter.HighlightPainter yellowPainter) {
        this.yellowPainter = yellowPainter;
    }

    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    public TimerListener getTimerListener() {
        return timerListener;
    }

    public void setTimerListener(TimerListener timerListener) {
        this.timerListener = timerListener;
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

    public Font getBigOutputFont() {
        return bigOutputFont;
    }

    public void setBigOutputFont(Font bigOutputFont) {
        this.bigOutputFont = bigOutputFont;
    }
    
    private class InputFieldListener implements ActionListener {
        @Override
        public void actionPerformed (ActionEvent ae) {
            //readNumber();
            if (MementoView.this.getGame() != null && MementoView.this.getGame().isGameOn()) {
                readString();
                System.out.println("Received from user: " + getInputString());
                MementoView.this.getController().receiveUserInput(getInputString());
                MementoView.this.getInputField().setText("");
                MementoView.this.getInputField().requestFocus();
                //System.out.println(inputNumber);
            } else if (MementoView.this.getGame() != null && !MementoView.this.game.isGameOn()) {
                JOptionPane.showMessageDialog(MementoView.this.getContainer(), "The game is off");                
            }
            
        }        
    }
    
    private class StartButtonListener implements ActionListener {
        @Override
        public void actionPerformed (ActionEvent ae) {
            if (MementoView.this.getGame().isGameOn()) {
                JOptionPane.showMessageDialog(MementoView.this.getContainer(), "The game is already running");
            } else {
                MementoView.this.getController().startGame();
            }
            //System.out.println(MementoView.this.useLCCyryllic.getState());
        }              
    } 
    
    private class TimerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Clear text
            getTimer().stop();            
            hideNumber();            
        }
    }
    
    private class ChooseFontListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {            
            JFontChooser fontChooser = new JFontChooser();
            fontChooser.showDialog(MementoView.this.getContainer());
            MementoView.this.getOutputField().setFont(fontChooser.getSelectedFont());            
        }        
    }
}
