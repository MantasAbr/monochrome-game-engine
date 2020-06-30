package entities;

import gfx.Colors;
import gfx.Screen;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import level.Level;
import pkg2d.InputHandling;

/**
 *
 * @author Mantas
 */
public class Player extends Mob {
    
    
    private InputHandling input;
    private Screen screen;
    private int color = Colors.get(-1, 111, 531, 543);
    private int scale = 1;
    public boolean action;
    public int ColDetX = x + 3;
    public int ColDetY = y + 2;
    public int width = 9;
    public int height = 13;
    public List<Bullet> bullets = new ArrayList<Bullet>();
    public int bulletTimeCooldown = 100;

    public Player(Level level, Screen screen, int x, int y, InputHandling input) {
        super(level, "Player", x, y, 1, 100);
        this.input = input;
        this.screen = screen;
        
        for(int i = 0; i < 5; i++){
            bullets.add(new Bullet(level, this, screen, i));
        }
    }
    
    @Override
    public void tick() {
        
        //Moving
        int xa = 0;
        int ya = 0;

        if(input.up.isPressed()){
            ya--;
        }
        if(input.down.isPressed()){
            ya++;
        }
        if(input.left.isPressed()){
            xa--;
        }
        if(input.right.isPressed()){
            xa++;
        }

        if(xa != 0 || ya != 0){
            move(xa, ya);
            isMoving = true;
        }
        else
        {
            isMoving = false;
        }
        
        action = false;
        //Action key
        if(input.action.isPressed() && action == false && bulletTimeCooldown >= 100){
            action = true;
            
            bullets.get(bullets.size() - 1).shot = true;
            bullets.remove(bullets.size() - 1);
            bulletTimeCooldown = 0;
        }
        bulletTimeCooldown++;
        System.out.println("Remaining bullets: " + bullets.size() + "Cooldown: " + bulletTimeCooldown);    
    }


    @Override
    public void render(Screen screen) {
        int xTile = 0;
        int yTile = 28;
        int walkingSpeed = 4;
        int flipTop = (numOfSteps >> walkingSpeed) & 1;
        int flipBottom = (numOfSteps >> walkingSpeed) & 1;
        
        if(movingDirection == 1)
            xTile += 2;
        else if(movingDirection > 1){
            xTile += 4 + ((numOfSteps >> walkingSpeed) & 1) * 2;
            flipTop = (movingDirection - 1) % 2;
        }

        int modifier = 8 * scale;
        int xOffset = x - modifier / 2;
        int yOffset = y - modifier / 2 - 4;
        screen.render(xOffset + (modifier * flipTop), yOffset, xTile +  yTile * 32, color, flipTop, scale);
        screen.render(xOffset + modifier - (modifier * flipTop), yOffset,(xTile + 1) +  yTile * 32, color, flipTop, scale);
        screen.render(xOffset + (modifier * flipBottom), yOffset + modifier, xTile + (yTile + 1) * 32, color, flipBottom, scale);
        screen.render(xOffset + modifier - (modifier * flipBottom), yOffset + modifier, (xTile + 1) + (yTile + 1) * 32, color, flipBottom, scale);
    }
    @Override
    public boolean hasCollided(int xa, int ya) {
        int xMin = 0;
        int xMax = 7;
        int yMin = 3;
        int yMax = 7;
        
        for(int x = xMin; x < xMax; x++){
            if(isSolidTile(xa, ya, x, yMin)){
                return true;
            }
        }
        for(int x = xMin; x < xMax; x++){
            if(isSolidTile(xa, ya, x, yMax)){
                return true;
            }
        }
        for(int y = yMin; y < yMax; y++){
            if(isSolidTile(xa, ya, xMin, y)){
                return true;
            }
        }
        for(int y = yMin; y < yMax; y++){
            if(isSolidTile(xa, ya, xMax, y)){
                return true;
            }
        }
             
        return false;
    }
    
    @Override
    public boolean steppedOnSlow(int xa, int ya){
        int xMin = 0;
        int xMax = 7;
        int yMin = 3;
        int yMax = 7;
        
        for(int x = xMin; x < xMax; x++){
            if(isSlowingTile(xa, ya, x, yMin)){
                return true;
            }
        }
        for(int x = xMin; x < xMax; x++){
            if(isSlowingTile(xa, ya, x, yMax)){
                return true;
            }
        }
        for(int y = yMin; y < yMax; y++){
            if(isSlowingTile(xa, ya, xMin, y)){
                return true;
            }
        }
        for(int y = yMin; y < yMax; y++){
            if(isSlowingTile(xa, ya, xMax, y)){
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean steppedOnSlippery(int xa, int ya){
        int xMin = 0;
        int xMax = 7;
        int yMin = 3;
        int yMax = 7;
        
        for(int x = xMin; x < xMax; x++){
            if(isSlipperyTile(xa, ya, x, yMin)){
                return true;
            }
        }
        for(int x = xMin; x < xMax; x++){
            if(isSlipperyTile(xa, ya, x, yMax)){
                return true;
            }
        }
        for(int y = yMin; y < yMax; y++){
            if(isSlipperyTile(xa, ya, xMin, y)){
                return true;
            }
        }
        for(int y = yMin; y < yMax; y++){
            if(isSlipperyTile(xa, ya, xMax, y)){
                return true;
            }
        }
        return false;
    }

}
