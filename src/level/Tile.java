package level;

import gfx.Colors;
import gfx.Screen;

/**
 *
 * @author Mantas
 */
public abstract class Tile {

    public static final Tile[] tiles = new Tile[256];
    public static final Tile VOID = new BaseSolidTile(0, 0, 0, Colors.get(000, -1, -1, -1), 0xFF000000);
    public static final Tile STONE = new BaseSolidTile(1, 1, 0, Colors.get(-1, 343, 332, 444), 0xFF555555);
    public static final Tile GRASS = new BaseTile(2, 2, 0, Colors.get(-1, 131, 141, 121), 0xFF00FF00);
    public static final Tile MUD = new BaseSlowingTile(3, 3, 0, Colors.get(-1, 420, 320, -1), 0xFF784200);
    public static final Tile ICE = new BaseSlipperyTile(4, 4, 0, Colors.get(-1, 055, 355, -1), 0xFF00FFFF);
    
    protected byte id;
    protected boolean isSolid;
    protected boolean isEmitter;
    protected boolean isSlow;
    protected boolean isSlippery;
    private int levelColor;
    
    public Tile(int id, boolean isSolid, boolean isEmitter, boolean isSlow, int levelColor){
        this.id = (byte) id;
        if(tiles[id] != null) throw new RuntimeException("Duplicate tile id on: " + id);
        this.isSolid = isSolid;
        this.isEmitter = isEmitter;
        this.isSlow = isSlow;
        this.levelColor = levelColor;
        tiles[id] = this;
    }
    
    public byte getId(){
        return id;
    }
    
    public boolean getIsSolid(){
        return isSolid;
    }
    
    public boolean getIsEmitter(){
        return isEmitter;
    }
    
    public boolean getIsSlow(){
        return isSlow;
    }
    
    public boolean getIsSlippery(){
        return isSlippery;
    }
    
    public int getLevelColor(){
        return levelColor;
    }

    public abstract void render(Screen screen, Level level, int x, int y);

    
}
