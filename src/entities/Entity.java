package entities;

import gfx.Screen;
import level.Level;

/**
 *
 * @author Mantas
 */
public abstract class Entity {
    
    public int x, y;
    protected Level level;
    
    public Entity(Level level){
        this.level = level;
        this.init(level);
    }
    
    public final void init(Level level){
        this.level = level;
    }
    
    public abstract void tick();
    
    public abstract boolean hasCollided(int xa, int ya);
    
    public abstract void render(Screen screen);
}
