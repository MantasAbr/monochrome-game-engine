package entities;

import gfx.Colors;
import gfx.Screen;
import java.util.Random;
import level.Level;

/**
 *
 * @author Mantas
 */
public class ShadowGhoul extends Mob{
    
    private int standingInterval = 300;
    private int movingInterval = 80;
    Random random = new Random();
    private int color = Colors.get(-1, 000, 500, 111);
    private int scale = 1;
    private boolean isAlive = true;
    int movingDirection = random.nextInt(4);


    public ShadowGhoul(Level level, String name, int x, int y, int speed) {
        super(level, name, x, y, speed, 200);
        
    }

    @Override
    public boolean hasCollided(int xa, int ya) {
        int xMin = 0;
        int xMax = 9;
        int yMin = 3;
        int yMax = 9;
        
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
    public boolean steppedOnSlow(int xa, int ya) {
        int xMin = 0;
        int xMax = 9;
        int yMin = 3;
        int yMax = 9;
        
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
    public boolean steppedOnSlippery(int xa, int ya) {
        int xMin = 0;
        int xMax = 9;
        int yMin = 3;
        int yMax = 9;
        
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

    @Override
    public void tick() {
        if(isAlive){
            int xa = 0;
            int ya = 0;
            if(movingInterval <= 80 && movingInterval > 0 && standingInterval == 300){
                
                if(movingDirection == 0){
                    ya--;
                    move(xa, ya);
                    movingInterval--;
                }
                if(movingDirection == 1){
                    ya++;
                    move(xa, ya);
                    movingInterval--;
                }
                if(movingDirection == 2){
                    xa--;
                    move(xa, ya);
                    movingInterval--;
                }
                if(movingDirection == 3){
                    xa++;
                    move(xa, ya);
                    movingInterval--;
                }
                if(hasCollided(xa, ya)){
                    movingInterval = 0;
                    standingInterval = 60;
                }
                    
            }
            if(movingInterval == 0 && standingInterval <= 300 && standingInterval > 0){
                standingInterval--;
            }
            if(movingInterval <= 0 && standingInterval <= 0){
                movingInterval = 80;
                standingInterval = 300;
                int newMovingDirection = random.nextInt(4);
                movingDirection = newMovingDirection;
            }
            
            //debug
//            System.out.println(movingInterval + " " + standingInterval);
//            if(movingDirection == 0){
//                System.out.println("Going north");
//            }
//            if(movingDirection == 1){
//                System.out.println("Going south");
//            }
//            if(movingDirection == 2){
//                System.out.println("Going west");
//            }
//            if(movingDirection == 3){
//                System.out.println("Going east");
//            }
        }
    }

    @Override
    public void render(Screen screen) {
        int xTile = 8;
        int yTile = 28;
        int walkingSpeed = 100;
        int flipTop = (numOfSteps >> walkingSpeed) & 1;
        int flipBottom = (numOfSteps >> walkingSpeed) & 1;
        
        if(this.movingDirection == 1)
            xTile += 2;
//        else if(this.movingDirection > 1){
//            xTile += 4 + ((numOfSteps >> walkingSpeed) & 1) * 2;
//            flipTop = (this.movingDirection - 1) % 2;
//        }
        if(this.movingDirection == 3){
            xTile += 4;
            
        }

        int modifier = 8 * scale;
        int xOffset = x - modifier / 2;
        int yOffset = y - modifier / 2 - 2;
        screen.render(xOffset + modifier * flipTop, yOffset, xTile +  yTile * 32, color, flipTop, scale);
        screen.render(xOffset + modifier - (modifier * flipTop), yOffset,(xTile + 1) +  yTile * 32, color, flipTop, scale);
        screen.render(xOffset + (modifier * flipBottom), yOffset + modifier, xTile + (yTile + 1) * 32, color, flipBottom, scale);
        screen.render(xOffset + modifier - (modifier * flipBottom), yOffset + modifier, (xTile + 1) + (yTile + 1) * 32, color, flipBottom, scale);
    }
    
}
