package entities;

import gfx.Colors;
import gfx.Screen;
import level.Level;

/**
 *
 * @author Mantas
 */
public class Bullet extends Entity{
    
    private double damage = 50;
    private int xPosition;
    private int yPosition;
    private int scale = 1;
    private int color = Colors.get(-1, -1, -1, 555);
    private Player player;
    private Screen screen;
    public int count;
    public boolean shot = false;

    public Bullet(Level level, Player player, Screen screen, int count) {
        super(level);
        this.xPosition = x;
        this.yPosition = y;
        this.player = player;
        this.count = count;
        this.screen = screen;
    }

    @Override
    public void tick() {
        if(shot){
            render(screen);
        }
    }

    @Override
    public boolean hasCollided(int xa, int ya) {
        return false;
    }

    @Override
    public void render(Screen screen) {
        int xTile = 5;
        int yTile = 0;
        
        screen.render(xPosition, yPosition, xTile * yTile * 32, color, 0, scale);
    }
    
}
