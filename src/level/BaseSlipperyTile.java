package level;

/**
 *
 * @author Mantas
 */
public class BaseSlipperyTile extends BaseTile{
    
    public BaseSlipperyTile(int id, int x, int y, int tileColor, int levelColor) {
        super(id, x, y, tileColor, levelColor);
        this.isSlippery = true;
    }    
}
