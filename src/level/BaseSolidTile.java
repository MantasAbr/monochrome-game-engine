package level;

/**
 *
 * @author Mantas
 */
public class BaseSolidTile extends BaseTile{
    
    public BaseSolidTile(int id, int x, int y, int tileColor, int levelColor) {
        super(id, x, y, tileColor, levelColor);
        this.isSolid = true;
    }
    
}
