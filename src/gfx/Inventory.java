package gfx;

import entities.Tree;
import pkg2d.InputHandling;

/**
 *
 * @author Mantas
 */
public class Inventory {
    
    private Font font;
    private InputHandling input;
    private Screen screen;
    private Tree tree;
    private int color = Colors.get(000, -1, -1, -1);
    
    protected int numberOfWood;
    protected int numberOfStone;
    
    public Inventory(InputHandling input, Screen screen, Font font, Tree tree, int wood, int stone){
        this.input = input;
        this.screen = screen;
        this.font = font;
        this.tree = tree;
        this.numberOfStone = stone;
        this.numberOfWood = wood;
    }
    
    public void render(){
        
        String woodInfo = "Amount of wood: ";
        
        Font.render(woodInfo + Integer.toString(numberOfWood), screen, 
                screen.xOffset + screen.width / 2 - woodInfo.length() * 8 / 2, 
                screen.yOffset + screen.height / 5, color, 1);
    }
    
    public void tick(){
        if(input.inventory.isPressed())
            render();
        
        if(tree.destroyed == true && tree.getWoodAmount() != 0){
            numberOfWood += tree.getWoodAmount();
            tree.setWoodAmount(0);
        }           
    }
    
    public int getWoodFromInventory(){
        return numberOfWood;
    }
}
