package level;

import gfx.Screen;

/**
 *
 * @author Mantas
 */
public class BaseTile extends Tile {
    
    protected int tileId;
    protected int tileColor;

    public BaseTile(int id, int x, int y, int tileColor, int levelColor) {
        super(id, false, false, false, levelColor);
        this.tileId = x + y;
        this.tileColor = tileColor;
    }

    @Override
    public void render(Screen screen, Level level, int x, int y) {
        screen.render(x, y, tileId, tileColor, 0x00, 1);
    }
    
}
