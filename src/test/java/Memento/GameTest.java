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
        controller.getView().getStartButton().doClick();   
        
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
        
        assertTrue("Parameter game.gameOn has to be true ", controller.getGame().isGameOn());        
    }
    

    @Test
    public void testRandomSymbolGenerateAndDisplay() {
        assertEquals("The text in the outputfield must be the same as in controller.game.randomString ", 
                controller.getView().getOutputField().getText(), controller.getGame().getCurrentRandomString());       
    }    
    

    @Test
    public void testEndGame() {
        
        for (int i = 0; i < controller.getGame().getCurrentQtyOfShows(); i++) {
            
            controller.receiveUserInput(controller.getView().getInputField().getText());
//            robot.keyPress(KeyEvent.VK_ENTER);
//            robot.keyRelease(KeyEvent.VK_ENTER);
            controller.getGame().setShowsCounter();
            System.out.println(controller.getGame().getShowsCounter());
        }
        assertFalse("The game should already be finished ", controller.getGame().isGameOn());
    }
    
    @Test
    public void testReadSettingsForCreateRandomSymbolsGenerator () {
        prepareForReadSettingsTest();              
        controller.readSettings();

        controller.getGame().initializeGame(controller.getShowDuration(), controller.getRandomStringLength(), controller.isUseDigits(), controller.isUseLowerCaseCyrillic(), 
                controller.isUseUpperCaseCyrillic(), controller.isUseLowerCaseLatinic(), controller.isUseUpperCaseLatinic(), controller.isUsePunctiationMarks(), 
                controller.isUseLiteUpperCaseCyrillic());

        assertEquals(controller.getGame().getCurrentSymbolsSet(), 
                RandomSymbolsGenerator.DIGITS_ONLY + RandomSymbolsGenerator.CYRILLIC_LOWER_CASE + 
                RandomSymbolsGenerator.CYRYLLIC_UPPER_CASE  + RandomSymbolsGenerator.CYRYLLIC_LITE_UPPER_CASE + 
                RandomSymbolsGenerator.LATINIC_LOWER_CASE + RandomSymbolsGenerator.LATINIC_UPPER_CASE + 
                RandomSymbolsGenerator.PUNCTUATION_MARKS, controller.getGame().getCurrentSymbolsSet());
       
       
    }
    
    public void prepareForReadSettingsTest() {        
        controller.getView().getUseDigits().setState(true);
        controller.getView().getUseLCCyryllic().setState(true);
        controller.getView().getUseUCCyrillic().setState(true);
        controller.getView().getUseLCLatinic().setState(true);
        controller.getView().getUseUCLatinic().setState(true);
        controller.getView().getUsePunctuationMarks().setState(true);  
        controller.getView().getUseLiteUCCyryllic().setState(true);
        
    }
}