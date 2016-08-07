/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Memento;

/**
 *
 * @author Pozitron
 */
public class MementoTestDrive {

    
    public static void main (String[] args) {
        try {
            GameModel game = new GameModel ();
            GameController controller = new GameController (game);    
        }
        catch (Exception ex) {
            System.out.println(ex.toString());
        }
        
    }
}
