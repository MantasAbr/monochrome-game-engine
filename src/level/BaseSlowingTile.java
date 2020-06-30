package level;

/**
 *
 * @author Mantas
 */
public class BaseSlowingTile extends BaseTile{
    
    public BaseSlowingTile(int id, int x, int y, int tileColor, int levelColor) {
        super(id, x, y, tileColor, levelColor);
        this.isSlow = true;
    }   
}
