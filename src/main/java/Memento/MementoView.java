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
    GameModel game;
    GameController controller;
    
    JFrame mainWindow;
    Container container;
    Dimension mainWindowDimension;
    BorderLayout layout;
    
    JTextField outputField;
    JTextField inputField;
    JTextField inputShowDuration;       
    //JTextField inputQuantityOfShows; 
    JTextField inputStringLength;
    InputFieldListener inputListener;
    
    JButton startButton;
    StartButtonListener startListener;  
    
    //разделители меню объявляются и инициализируются в методе createMenu ();
    JMenuBar menuBar;
    JMenu fontSettings;
    JMenu symbolsSettings;
    JMenuItem fontChooser;
    JCheckBoxMenuItem useDigits;
    JCheckBoxMenuItem useLCCyryllic;
    JCheckBoxMenuItem useUCCyrillic;
    JCheckBoxMenuItem useLiteUCCyryllic;
    JCheckBoxMenuItem useLCLatinic;
    JCheckBoxMenuItem useUCLatinic;
    JCheckBoxMenuItem usePunctuationMarks;    

    Highlighter lighter;
    Highlighter.HighlightPainter yellowPainter;
    
    Timer timer;
    TimerListener timerListener;
    
    int inputNumber;
    String inputString;
    
    Font bigOutputFont;
    
    public MementoView (GameModel _game, GameController _controller) {
        this.game = _game;
        this.controller = _controller;
        game.addObserver(this);
        this.timerListener = new TimerListener ();
        this.timer = new Timer (5000, timerListener);
    }
    
    @Override
    public void update (Observable observable, Object arg) {
        if (this.game != null && this.game.isGameOn()) {
            //this.outputField.setText(Integer.toString(this.game.getCurrentRandomNumber()));
            //showNumber(this.game.getCurrentRandomNumber());
            showString(this.game.getCurrentRandomString());
            highlight();            
            timer.restart();            // // начинаем отсчет (именно restart'ом)
            highlight();
            //timer = null;            
            
        } else if (this.game != null  && !this.game.isGameOn()) {
            int result = this.game.getResult();
            JOptionPane.showMessageDialog(MementoView.this.container, result + " correct from " + this.game.getCurrentQtyOfShows());            
        }
    }
        
    public void createView () {
        createMainWindow();        
        createTextFields();
        createButtons(); 
        createMenu();
        fillTheContainer();        
        highlight();      
        mainWindow.setVisible(true);        
    }
    
    public void createMainWindow () {
        mainWindow = new JFrame();
        mainWindowDimension = new Dimension (480, 160);            
        mainWindow.setSize(mainWindowDimension);
        mainWindow.setTitle("Memento");
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        container = mainWindow.getContentPane();
        layout = new BorderLayout();
        
    }
    
    // highlighter for outputfield is been initialized here
    public void createTextFields () {
        outputField = new JTextField ("Number_To_Be_Generated");           
        outputField.setEditable(false);        
        outputField.setHorizontalAlignment(JTextField.CENTER);
        bigOutputFont = new Font ("Arial", Font.PLAIN, 26);
        outputField.setFont(bigOutputFont);
        inputField = new JTextField ("Input_Your_Guess_Here");
        inputField.setHorizontalAlignment(JTextField.CENTER);
        inputListener = new InputFieldListener();
        inputField.selectAll();         
        inputField.requestFocus();         
        inputField.addActionListener(inputListener);
        inputShowDuration = new JTextField ("500");
        inputShowDuration.setHorizontalAlignment(JTextField.CENTER);
        inputStringLength = new JTextField ("7");
        inputStringLength.setHorizontalAlignment(JTextField.CENTER); 
        lighter = outputField.getHighlighter();
        yellowPainter = new DefaultHighlighter.DefaultHighlightPainter(Color.yellow); 
    }
    
    public void createButtons () {                          // only one button
        startButton = new JButton ("Start");
        startListener =  new StartButtonListener ();
        startButton.addActionListener(startListener);        
    }
    
    public void createMenu () {
        menuBar = new JMenuBar ();
        fontSettings = new JMenu ("Font Settings");
        JSeparator menuSeparator = new JSeparator ();
        symbolsSettings = new JMenu ("Symbols Settings");     
        fontChooser = new JMenuItem ("Choose Font");
        ChooseFontListener chooseFontListener = new ChooseFontListener ();
        fontChooser.addActionListener(chooseFontListener);
        fontSettings.add(fontChooser);
        useDigits = new JCheckBoxMenuItem ("Use Digits");
//        useDigits.setState(true);                       // default mode is digits only
        useLCCyryllic = new JCheckBoxMenuItem ("Use LowerCase Cyrillic");
        useUCCyrillic = new JCheckBoxMenuItem ("Use UpperCase Cyrillic");
        
        useLiteUCCyryllic = new JCheckBoxMenuItem ("Use Lite UpperCase Cyrillic");
        useLiteUCCyryllic.setState(true);                   // new default mode
        
        useLCLatinic = new JCheckBoxMenuItem ("Use LowerCase Latinic");
        useUCLatinic = new JCheckBoxMenuItem ("Use UpperCase Latinic");
        usePunctuationMarks = new JCheckBoxMenuItem ("Use Punctuation marks");
        symbolsSettings.add(useDigits);
        symbolsSettings.add(useLCCyryllic);
        symbolsSettings.add(useUCCyrillic);
        
        symbolsSettings.add(useLiteUCCyryllic);
        
        symbolsSettings.add(useLCLatinic);
        symbolsSettings.add(useUCLatinic);
        symbolsSettings.add(usePunctuationMarks);
        menuBar.add(fontSettings);
        menuBar.add(menuSeparator);
        menuBar.add(symbolsSettings);
        
    }
    
    public void fillTheContainer () {
        container.add(outputField, BorderLayout.NORTH);
        container.add(inputField, BorderLayout.SOUTH);
        container.add(inputShowDuration, BorderLayout.WEST);
        //container.add(inputQuantityOfShows, BorderLayout.EAST);
        container.add(inputStringLength, BorderLayout.EAST);
        container.add(startButton, BorderLayout.CENTER);  
        mainWindow.setJMenuBar(menuBar);
        
    }        
    
    public void highlight () {
        // highlight either one central symbol (if string length is odd) or two (or generate exception)        
        try {
            if (outputField.getText().length() % 2 == 0) {
                lighter.addHighlight(outputField.getText().length() / 2 - 1, outputField.getText().length() / 2 + 1, yellowPainter); 
            } else if (outputField.getText().length () % 2 != 0) {
                lighter.addHighlight(outputField.getText().length() / 2, outputField.getText().length() / 2 + 1, yellowPainter);
            } else throw new BadLocationException ("error trying to highlight outputField", 0);
        } catch (BadLocationException ex) {
            System.out.println(ex.toString());
        }
        inputField.requestFocus();
        
    }
    
    // Obsolete
    public void showNumber (int _numberToShow) {
        this.outputField.setText(Integer.toString(_numberToShow));  
        highlight();
    }
    
    public void showString (String _stringToShow) {
        this.outputField.setText(_stringToShow);
    }
    
    public void hideNumber () {
        this.outputField.setText("  ");
        highlight();
    }
    
    public void readNumber () {
        try {
            inputNumber = Integer.parseInt(inputField.getText());
        }
        catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }
    
    public void readString () {
        this.inputString = this.inputField.getText();
    }
    
    private class InputFieldListener implements ActionListener {
        @Override
        public void actionPerformed (ActionEvent ae) {
            //readNumber();
            if (MementoView.this.game != null && MementoView.this.game.isGameOn()) {
                readString();
                System.out.println("Received from user: " + inputString);
                MementoView.this.controller.receiveUserInput(inputString);
                MementoView.this.inputField.setText("");
                MementoView.this.inputField.requestFocus();
                //System.out.println(inputNumber);
            } else if (MementoView.this.game != null && !MementoView.this.game.isGameOn()) {
                JOptionPane.showMessageDialog(MementoView.this.container, "The game is off");                
            }
            
        }        
    }
    
    private class StartButtonListener implements ActionListener {
        @Override
        public void actionPerformed (ActionEvent ae) {
            if (MementoView.this.game.isGameOn()) {
                JOptionPane.showMessageDialog(MementoView.this.container, "The game is already running");
            } else {
                MementoView.this.controller.startGame();
            }
            //System.out.println(MementoView.this.useLCCyryllic.getState());
        }              
    } 
    
    private class TimerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Clear text
            timer.stop();            
            hideNumber();            
        }
    }
    
    private class ChooseFontListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {            
            JFontChooser fontChooser = new JFontChooser();
            fontChooser.showDialog(MementoView.this.container);
            MementoView.this.outputField.setFont(fontChooser.getSelectedFont());            
        }        
    }
}
