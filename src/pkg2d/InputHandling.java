package pkg2d;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author Mantas
 */
public class InputHandling implements KeyListener{

    public class Key{
        
        public int numberOfTimesPressed = 0;
        
        private boolean pressed = false;
        
        public Key(int timesPressed){
            this.numberOfTimesPressed = timesPressed;
        }
        
        public boolean isPressed(){
            return pressed;
        }
        
        public int getNumberOfTimesPressed(){
            return numberOfTimesPressed;
        }
        
        public void toggle(boolean isPressed){
            pressed = isPressed;
            if(isPressed) numberOfTimesPressed++;
        }
    }
    
    
    public InputHandling(Game game){
        game.requestFocus();
        game.addKeyListener(this);
    }
    
    //All the key presses
    public Key up = new Key(0);
    public Key down = new Key(0);
    public Key left = new Key(0);
    public Key right = new Key(0);
    public Key shift = new Key(0);
    public Key action = new Key(0);
    public Key inventory = new Key(0);
    public Key pause = new Key(1);
    
    
    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        toggleKey(e.getKeyCode(), true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        toggleKey(e.getKeyCode(), false);
    }
    
    public void toggleKey(int keyCode, boolean isPressed){
        if(keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP) 
            up.toggle(isPressed);
        if(keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN) 
            down.toggle(isPressed);
        if(keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT) 
            left.toggle(isPressed);
        if(keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT) 
            right.toggle(isPressed);
        if(keyCode == KeyEvent.VK_SHIFT)
            shift.toggle(isPressed);
        if(keyCode == KeyEvent.VK_E)
            action.toggle(isPressed);
        if(keyCode == KeyEvent.VK_I)
            inventory.toggle(isPressed);
        if(keyCode == KeyEvent.VK_SPACE)
            pause.toggle(isPressed);
    }
    
}
