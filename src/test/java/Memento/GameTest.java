/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Memento;

import java.awt.event.KeyEvent;
import java.awt.Robot;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Pozitron
 */
public class GameTest {
        private GameModel game;
        private GameController controller;
        Robot robot;
        
    public GameTest() {
        try {
            robot = new Robot();
        } catch (Exception ex) {
            System.out.println("Exception while driving GameControllerTest constructor\n" + ex.toString());
        }
        game = new GameModel (100, 5);
        controller = new GameController (game);
    }
    
    @BeforeClass
    public static void setUpClass() {

    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        //controller.game.getCurrentSymbolsSet();
        //controller.game.stringGenerator = new RandomSymbolsGenerator (RandomSymbolsGenerator.DIGITS_ONLY + RandomSymbolsGenerator.CYRILLIC_LOWER_CASE);
        controller.view.startButton.doClick();   
        
    }
    
    @After
    public void tearDown() {
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    

    @Test
    public void testStartGame() {
        
        assertTrue("Parameter game.gameOn has to be true ", controller.game.isGameOn());        
    }
    

    @Test
    public void testRandomSymbolGenerateAndDisplay() {
        assertEquals("The text in the outputfield must be the same as in controller.game.randomString ", 
                controller.view.outputField.getText(), controller.game.getCurrentRandomString());       
    }    
    

    @Test
    public void testEndGame() {
        for (int i = 0; i < controller.game.getCurrentQtyOfShows(); i++) {
            //robot.keyPress(KeyEvent.VK_ENTER);
            controller.receiveUserInput(controller.view.inputField.getText());
            //controller.game.setShowsCounter();
            //System.out.println(controller.game.getShowsCounter());
        }
        assertFalse("The game should already be finished ", controller.game.isGameOn());
    }
    
    @Test
    public void testReadSettingsForCreateRandomSymbolsGenerator () {
        prepareForReadSettingsTest();              
        controller.readSettings();

        controller.game.initializeGame(controller.showDuration, controller.randomStringLength, controller.useDigits, controller.useLowerCaseCyrillic, 
                controller.useUpperCaseCyrillic, controller.useLowerCaseLatinic, controller.useUpperCaseLatinic, controller.usePunctiationMarks, 
                controller.useLiteUpperCaseCyrillic);

        assertEquals(controller.game.getCurrentSymbolsSet(), 
                RandomSymbolsGenerator.DIGITS_ONLY + RandomSymbolsGenerator.CYRILLIC_LOWER_CASE + 
                RandomSymbolsGenerator.CYRYLLIC_UPPER_CASE  + RandomSymbolsGenerator.CYRYLLIC_LITE_UPPER_CASE + 
                RandomSymbolsGenerator.LATINIC_LOWER_CASE + RandomSymbolsGenerator.LATINIC_UPPER_CASE + 
                RandomSymbolsGenerator.PUNCTUATION_MARKS, controller.game.getCurrentSymbolsSet());
       
       
    }
    
    public void prepareForReadSettingsTest() {        
        controller.view.useDigits.setState(true);
        controller.view.useLCCyryllic.setState(true);
        controller.view.useUCCyrillic.setState(true);
        controller.view.useLCLatinic.setState(true);
        controller.view.useUCLatinic.setState(true);
        controller.view.usePunctuationMarks.setState(true);  
        controller.view.useLiteUCCyryllic.setState(true);
        
    }
}