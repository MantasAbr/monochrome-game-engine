package gfx;

/**
 *
 * @author Mantas
 */
public class Font {
    private static String characters = "abcdefghijklmnopqrstuvwxyz      " + 
                                       "0123456789.,:;'\"!               ";
    
    public static void render(String message, Screen screen, int xCoord, int yCoord, int color, int scale){
        message = message.toLowerCase();
        
        for(int i = 0; i < message.length(); i++){
            int charIndex = characters.indexOf(message.charAt(i));
            if(charIndex >= 0)
                screen.render(xCoord + (i * 8), yCoord, charIndex + 30 * 32, color, 0x00, scale);
        }
    }
}
