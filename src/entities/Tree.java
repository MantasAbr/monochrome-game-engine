package entities;

import gfx.Screen;
import level.Level;
import gfx.Colors;
import gfx.Inventory;
import java.awt.Rectangle;

/**
 *
 * @author Mantas
 */
public class Tree extends Entity{

    
    private int color = Colors.get(-1, 020, 420, 240);
    private int scale = 1;
    private int positionX;
    private int positionY;
    private int height = 16;
    private int width = 16;
    
    private Player player;
    private Inventory inventory;
    private Screen screen;
    public Rectangle hitBox = new Rectangle(positionX, positionY, 16, 16);
    protected int amountOfWood;
    protected int resistance;
    protected boolean gettingDestroyed;
    protected int destructionRate;
    public boolean destroyed = false;
    
    public Tree(Level level, Screen screen, Player player, int amount, int res, int posX, int posY) {
        super(level);
        this.screen = screen;
        this.amountOfWood = amount;
        this.resistance = res;
        this.player = player;
        this.positionX = posX;
        this.positionY = posY;
        hitBox = new Rectangle(posX, posY, 16, 16);
    }

    @Override
    public void tick() {
        if(!destroyed){
            render(screen);
            if(player.action == true){
                destructionRate++;
                gettingDestroyed = true;
            } 
            else{
                destructionRate = 0;
                gettingDestroyed = false;
            }
            if(destructionRate == 180)
                destroyed = true;
            
            if(playerIsNear());{
                System.out.println("Tree and player collided");
                //System.out.println("Tree x: " + this.positionX + "Tree y: " + this.positionY);
            }
        }
        if(destroyed){
            
        }      
    }

    @Override
    public void render(Screen screen) {
        int xTile = 0;
        int yTile = 26;
        int modifier = 8 * scale;
        
        screen.render(positionX, positionY, xTile + yTile * 32, color, 0, scale);
        screen.render(positionX + modifier, positionY, (xTile + 1) + yTile * 32, color, 0, scale);
        screen.render(positionX, positionY + modifier, xTile + (yTile + 1) * 32, color, 0, scale);
        screen.render(positionX + modifier, positionY + modifier, (xTile + 1) + (yTile + 1) * 32, color, 0, scale);
    }
    
    @Override
    public boolean hasCollided(int xa, int ya){
        return false;
    }
    
    public boolean playerIsNear(){
        if(player.ColDetX < (this.positionX + width) && (player.ColDetX + player.width) > this.positionX &&
           player.ColDetY < (this.positionY + height) && (player.ColDetY + player.height) > this.positionY){
            return true;
        }
        return false;
    }
    
    public int getWoodAmount(){
        return amountOfWood;
    }
    
    public void setWoodAmount(int wood){
        this.amountOfWood = wood;
    }
}
