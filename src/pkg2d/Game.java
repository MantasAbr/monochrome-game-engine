package pkg2d;

import entities.Mob;
import entities.Player;
import entities.ShadowGhoul;
import entities.Tree;
import gfx.Colors;
import gfx.Font;
import gfx.Inventory;
import gfx.Screen;
import gfx.SpriteSheet;
import level.Level;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;

/**
 *
 * @author Mantas
 */
public class Game extends Canvas implements Runnable{

    public static final int WIDTH = 300;                //Frame width
    public static final int HEIGHT = WIDTH / 12 * 9;    //Frame height
    public static final int SCALE = 3;                  //Scale factor
    public static final String NAME = "A game";         //Frame name
    public boolean isRunning = false;                   //is the game running boolean
    
    public long tickCount = 0;
    
    private final JFrame frame;
    private final BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    private final int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
    private final int[] colors = new int[6 * 6 * 6];
    
    //Object declarations
    private Screen screen;
    public InputHandling input;
    public Font font;
    public Level level;
    public Inventory inventory;
    
    //Entity object declaration
    public Player player;
    public List<Tree> tree = new ArrayList<Tree>();
    public List<ShadowGhoul> ghost = new ArrayList<ShadowGhoul>();
    
    /**
     * Constructor
     */
    public Game(){
        setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        
        frame = new JFrame(NAME);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());       
        frame.add(this, BorderLayout.CENTER);
        frame.pack();
        frame.setResizable(false);              //Can not resize
        frame.setLocationRelativeTo(null);      //Centered
        frame.setVisible(true);                 //Visible
    }
    
    public void init(){
        
        int index = 0;
        
        for(int r = 0; r < 6; r++){
            for(int g = 0; g < 6; g++){
                for(int b = 0; b < 6; b++){
                    int red = (r * 255/5);
                    int green = (g * 255/5);
                    int blue = (b * 255/5);
                    
                    colors[index++] = red << 16 | green << 8 | blue;
                }
            }
        }
        
        //Object assignments
        screen = new Screen(WIDTH, HEIGHT, new SpriteSheet("grid.png"));
        input = new InputHandling(this);
        level = new Level("/levels/level_test_64x.png");
        
        //Entity assignments
        player = new Player(level, screen, 8, 8, input);
//        tree.add(new Tree(level, screen, player, 10, 20, 120, 120));
//        tree.add(new Tree(level, screen, player, 16, 12, 190, 100));
//        ghost.add(new ShadowGhoul(level, "Ghost_1", 200, 200, 1));
        
        
        //Adding entities
        
//        for(int i = 0; i < tree.size(); i++){
//            level.addEntity(tree.get(i));
//        }
//        for(int i = 0; i < ghost.size(); i++){
//            level.addEntity(ghost.get(i));
//        }
        level.addEntity(player);
        
        //inventory = new Inventory(input, screen, font, 0, 0);
    }
    
    /**
     * FPS and Tick logic, limited to about 60fps and 60tps
     */
    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double NanosPerTick = 1000000000D / 60D;
        
        int ticks = 0;
        int frames = 0;
        
        long lastTimer = System.currentTimeMillis();
        double delta = 0;
        
        
        init();
        
        while(true){
            long now = System.nanoTime();
            delta += (now - lastTime) / NanosPerTick;
            lastTime = now;
            boolean shouldRender = true;
            
                
            while(delta >= 1){
                ticks++;
                tick();
                delta -= 1;
                shouldRender = true;
            }
                                
            try {
                Thread.sleep(5);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            
            if(shouldRender){                         
                frames++;
                render();
            
            }
            
            if(System.currentTimeMillis() - lastTimer >= 1000){
                lastTimer += 1000;
                System.out.println(ticks + " ticks, " + frames + " frames per second");
                frames = 0;
                ticks = 0;                
            }
        }
    }
    
    /**
     * Increments the tick count
     */
    public void tick(){
        if(input.pause.getNumberOfTimesPressed() % 2 != 0){
            tickCount++;                
            level.tick();
            
        }            
                   
    }
    
    /**
     * renders the screen
     * Uses triple buffering
     */
    public void render(){
        BufferStrategy strat = getBufferStrategy();
        if(strat == null){
            createBufferStrategy(3);
            return;
        }
        
        int xOffset = player.x - (screen.width / 2);
        int yOffset = player.y - (screen.width / 2);

        
        level.renderTiles(screen, xOffset, yOffset);
        

        
                
        if(input.pause.getNumberOfTimesPressed() % 2 == 0){
            String message = "paused";
            Font.render(message, screen, screen.xOffset + screen.width / 2 - message.length() * 8 / 2, 
                    screen.yOffset + screen.height / 2, Colors.get(000, -1, -1, -1), 1);
        }
        
        
//        
//        for (int x = 0; x < level.width; x++) {
//            int colour = Colors.get(-1, -1, -1, 000);
//            if (x % 10 == 0 && x != 0) {
//                colour = Colors.get(-1, -1, -1, 500);
//            }
//            Font.render((x % 10) + "", screen, 0 + (x * 8), 0, colour);
//        }

        level.renderEntities(screen);
                    
        for(int y = 0; y < screen.height; y++){
            for(int x = 0; x < screen.width; x++){
                int colorCode = screen.pixels[x + y * screen.width];
                if(colorCode < 255)
                    pixels[x + y * WIDTH] = colors[colorCode];
            }
        }
//        
//        System.out.println(player.x + " " + player.y);
//        int boxX = tree.get(0).hitBox.width + tree.get(0).hitBox.x;
//        int boxY = tree.get(0).hitBox.height + tree.get(0).hitBox.y;
//        System.out.println("tree rectangle: x and y:" + tree.get(0).hitBox.x + " " + tree.get(0).hitBox.y + 
//                           " Hitbox x: " + boxX + " Hitbox y: " + 
//                            boxY);
        
        Graphics g = strat.getDrawGraphics();
        
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        
        g.dispose();
        strat.show();
    }
    
    /**
     * Starts the game
     * synchronized for future cross-compatibility
     */
    public synchronized void start(){
        isRunning = true;
        new Thread(this).start();
        
    }
    
    /**
     * Stops the game
     */
    public synchronized void stop(){
        isRunning = false;
    }

    /**
     * Starts
     * @param args 
     */
    public static void main(String[] args) {
        new Game().start();
    }
}
