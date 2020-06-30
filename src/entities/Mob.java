package entities;

import level.Level;
import level.Tile;

/**
 *
 * @author Mantas
 */
public abstract class Mob extends Entity{
    
    protected String name;
    protected int speed;
    protected double health;
    protected int numOfSteps = 0;
    protected boolean isMoving;
    protected int movingDirection = 1;
    protected int scale = 1;
    
    public Mob(Level level, String name, int x, int y, int speed, double health) {
        super(level);
        this.name = name;
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.health = health;
    }
    
    public void move(int xa, int ya){
        if(xa != 0 && ya != 0){
            move(xa, 0);
            move(0, ya);
            numOfSteps--;
            return;
        }
        numOfSteps++;
        if(!hasCollided(xa, ya)){
            if(ya < 0) movingDirection = 0;     //Goes up
            if(ya > 0) movingDirection = 1;     //Goes down
            if(xa < 0) movingDirection = 2;     //Goes left
            if(xa > 0) movingDirection = 3;     //Goes right
            if(level.getTile(this.x >> 3, this.y >> 3).getId() == 3){
                x += xa * (speed / 2);
                y += ya * (speed / 2);
            }
            if(level.getTile(this.x >> 3, this.y >> 3).getId() == 4){
                x += xa * (speed * 2);
                y += ya * (speed * 2);
            }
                x += xa * speed;
                y += ya * speed; 
        }
    }
    
    @Override
    public abstract boolean hasCollided(int xa, int ya);
    
    public abstract boolean steppedOnSlow(int xa, int ya);
    
    public abstract boolean steppedOnSlippery(int xa, int ya);
    
    protected boolean isSolidTile(int xa, int ya, int x, int y){
        if(level == null){
            return false;
        }
        
        Tile lastTile = level.getTile((this.x + x) >> 3, (this.y + y) >> 3);
        Tile newTile = level.getTile((this.x + x + xa) >> 3, (this.y + y + ya) >> 3);
        if(!lastTile.equals(newTile) && newTile.getIsSolid()){
            return true;
        }
        return false;
    }
    
    protected boolean isSlowingTile(int xa, int ya, int x, int y){
        if(level == null){
            return false;
        }
        
        Tile lastTile = level.getTile((this.x + x) >> 3, (this.y + y) >> 3);
        Tile newTile = level.getTile((this.x + x + xa) >> 3, (this.y + y + ya) >> 3);
        if(!lastTile.equals(newTile) && newTile.getIsSlow()){
            return true;
        }
        return false;
    }
    
    protected boolean isSlipperyTile(int xa, int ya, int x, int y){
        if(level == null){
            return false;
        }
        
        Tile lastTile = level.getTile((this.x + x) >> 3, (this.y + y) >> 3);
        Tile newTile = level.getTile((this.x + x + xa) >> 3, (this.y + y + ya) >> 3);
        if(!lastTile.equals(newTile) && newTile.getIsSlippery()){
            return true;
        }
        return false;
    }
    
    public String getName(){
        return name;
    }
}
