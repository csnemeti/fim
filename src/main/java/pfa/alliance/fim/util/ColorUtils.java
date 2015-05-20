/**
 * 
 */
package pfa.alliance.fim.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This is an utility class for handling colors.
 * 
 * @author Csaba
 */
public final class ColorUtils
{
    private static final String[] HEX_NUMBERS = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D",
        "E", "F" };

    /** The list of all supported colors. */
    private static final List<ColorWithName> COLORS;

    /** The list of color codes that are dark. */
    private static final Set<String> DARK_COLOR_CODES = new HashSet<>();
    static
    {
        List<ColorWithName> colors = new ArrayList<>();
        colors.add( new ColorWithName( "Black", 0x00, 0x00, 0x00 ) );

        colors.add( new ColorWithName( "AliceBlue", 0xF0, 0xF8, 0xFF ) );
        colors.add( new ColorWithName( "AntiqueWhite", 0xFA, 0xEB, 0xD7 ) );
        colors.add( new ColorWithName( "Aqua", 0x00, 0xFF, 0xFF ) );
        colors.add( new ColorWithName( "Aquamarine", 0x7F, 0xFF, 0xD4 ) );
        colors.add( new ColorWithName( "Azure", 0xF0, 0xFF, 0xFF ) );

        colors.add( new ColorWithName( "Beige", 0xF5, 0xF5, 0xDC ) );
        colors.add( new ColorWithName( "Bisque", 0xFF, 0xE4, 0xC4 ) );
        colors.add( new ColorWithName( "BlanchedAlmond", 0xFF, 0xEB, 0xCD ) );
        colors.add( new ColorWithName( "Blue", 0x00, 0x00, 0xFF ) );
        colors.add( new ColorWithName( "BlueViolet", 0x8A, 0x2B, 0xE2 ) );
        colors.add( new ColorWithName( "Brown", 0xA5, 0x2A, 0x2A ) );
        colors.add( new ColorWithName( "BurlyWood", 0xDE, 0xB8, 0x87 ) );

        colors.add( new ColorWithName( "CadetBlue", 0x5F, 0x9E, 0xA0 ) );
        colors.add( new ColorWithName( "Chartreuse", 0x7F, 0xFF, 0x00 ) );
        colors.add( new ColorWithName( "Chocolate", 0xD2, 0x69, 0x1E ) );
        colors.add( new ColorWithName( "Coral", 0xFF, 0x7F, 0x50 ) );
        colors.add( new ColorWithName( "CornflowerBlue", 0x64, 0x95, 0xED ) );
        colors.add( new ColorWithName( "Cornsilk", 0xFF, 0xF8, 0xDC ) );
        colors.add( new ColorWithName( "Crimson", 0xDC, 0x14, 0x3C ) );
        colors.add( new ColorWithName( "Cyan", 0x00, 0xFF, 0xFF ) );

        colors.add( new ColorWithName( "DarkBlue", 0x00, 0x00, 0x8B ) );
        colors.add( new ColorWithName( "DarkCyan", 0x00, 0x8B, 0x8B ) );
        colors.add( new ColorWithName( "DarkGoldenRod", 0xB8, 0x86, 0x0B ) );
        colors.add( new ColorWithName( "DarkGray", 0xA9, 0xA9, 0xA9 ) );
        colors.add( new ColorWithName( "DarkGreen", 0x00, 0x64, 0x00 ) );
        colors.add( new ColorWithName( "DarkKhaki", 0xBD, 0xB7, 0x6B ) );
        colors.add( new ColorWithName( "DarkMagenta", 0x8B, 0x00, 0x8B ) );
        colors.add( new ColorWithName( "DarkOliveGreen", 0x55, 0x6B, 0x2F ) );
        colors.add( new ColorWithName( "DarkOrange", 0xFF, 0x8C, 0x00 ) );
        colors.add( new ColorWithName( "DarkOrchid", 0x99, 0x32, 0xCC ) );
        colors.add( new ColorWithName( "DarkRed", 0x8B, 0x00, 0x00 ) );
        colors.add( new ColorWithName( "DarkSalmon", 0xE9, 0x96, 0x7A ) );
        colors.add( new ColorWithName( "DarkSeaGreen", 0x8F, 0xBC, 0x8F ) );
        colors.add( new ColorWithName( "DarkSlateBlue", 0x48, 0x3D, 0x8B ) );
        colors.add( new ColorWithName( "DarkSlateGray", 0x2F, 0x4F, 0x4F ) );
        colors.add( new ColorWithName( "DarkTurquoise", 0x00, 0xCE, 0xD1 ) );
        colors.add( new ColorWithName( "DarkViolet", 0x94, 0x00, 0xD3 ) );
        colors.add( new ColorWithName( "DeepPink", 0xFF, 0x14, 0x93 ) );
        colors.add( new ColorWithName( "DeepSkyBlue", 0x00, 0xBF, 0xFF ) );
        colors.add( new ColorWithName( "DimGray", 0x69, 0x69, 0x69 ) );
        colors.add( new ColorWithName( "DodgerBlue", 0x1E, 0x90, 0xFF ) );

        colors.add( new ColorWithName( "FireBrick", 0xB2, 0x22, 0x22 ) );
        colors.add( new ColorWithName( "FloralWhite", 0xFF, 0xFA, 0xF0 ) );
        colors.add( new ColorWithName( "ForestGreen", 0x22, 0x8B, 0x22 ) );
        colors.add( new ColorWithName( "Fuchsia", 0xFF, 0x00, 0xFF ) );
        colors.add( new ColorWithName( "Gainsboro", 0xDC, 0xDC, 0xDC ) );
        colors.add( new ColorWithName( "GhostWhite", 0xF8, 0xF8, 0xFF ) );
        colors.add( new ColorWithName( "Gold", 0xFF, 0xD7, 0x00 ) );
        colors.add( new ColorWithName( "GoldenRod", 0xDA, 0xA5, 0x20 ) );
        colors.add( new ColorWithName( "Gray", 0x80, 0x80, 0x80 ) );
        colors.add( new ColorWithName( "Green", 0x00, 0x80, 0x00 ) );
        colors.add( new ColorWithName( "GreenYellow", 0xAD, 0xFF, 0x2F ) );

        colors.add( new ColorWithName( "HoneyDew", 0xF0, 0xFF, 0xF0 ) );
        colors.add( new ColorWithName( "HotPink", 0xFF, 0x69, 0xB4 ) );

        colors.add( new ColorWithName( "IndianRed", 0xCD, 0x5C, 0x5C ) );
        colors.add( new ColorWithName( "Indigo", 0x4B, 0x00, 0x82 ) );
        colors.add( new ColorWithName( "Ivory", 0xFF, 0xFF, 0xF0 ) );

        colors.add( new ColorWithName( "Khaki", 0xF0, 0xE6, 0x8C ) );

        colors.add( new ColorWithName( "Lavender", 0xE6, 0xE6, 0xFA ) );
        colors.add( new ColorWithName( "LavenderBlush", 0xFF, 0xF0, 0xF5 ) );
        colors.add( new ColorWithName( "LawnGreen", 0x7C, 0xFC, 0x00 ) );
        colors.add( new ColorWithName( "LemonChiffon", 0xFF, 0xFA, 0xCD ) );
        colors.add( new ColorWithName( "LightBlue", 0xAD, 0xD8, 0xE6 ) );
        colors.add( new ColorWithName( "LightCoral", 0xF0, 0x80, 0x80 ) );
        colors.add( new ColorWithName( "LightCyan", 0xE0, 0xFF, 0xFF ) );
        colors.add( new ColorWithName( "LightGoldenRodYellow", 0xFA, 0xFA, 0xD2 ) );
        colors.add( new ColorWithName( "LightGray", 0xD3, 0xD3, 0xD3 ) );
        colors.add( new ColorWithName( "LightGreen", 0x90, 0xEE, 0x90 ) );
        colors.add( new ColorWithName( "LightPink", 0xFF, 0xB6, 0xC1 ) );
        colors.add( new ColorWithName( "LightSalmon", 0xFF, 0xA0, 0x7A ) );
        colors.add( new ColorWithName( "LightSeaGreen", 0x20, 0xB2, 0xAA ) );
        colors.add( new ColorWithName( "LightSkyBlue", 0x87, 0xCE, 0xFA ) );
        colors.add( new ColorWithName( "LightSlateGray", 0x77, 0x88, 0x99 ) );
        colors.add( new ColorWithName( "LightSteelBlue", 0xB0, 0xC4, 0xDE ) );
        colors.add( new ColorWithName( "LightYellow", 0xFF, 0xFF, 0xE0 ) );
        colors.add( new ColorWithName( "Lime", 0x00, 0xFF, 0x00 ) );
        colors.add( new ColorWithName( "LimeGreen", 0x32, 0xCD, 0x32 ) );
        colors.add( new ColorWithName( "Linen", 0xFA, 0xF0, 0xE6 ) );

        colors.add( new ColorWithName( "Magenta", 0xFF, 0x00, 0xFF ) );
        colors.add( new ColorWithName( "Maroon", 0x80, 0x00, 0x00 ) );
        colors.add( new ColorWithName( "MediumAquaMarine", 0x66, 0xCD, 0xAA ) );
        colors.add( new ColorWithName( "MediumBlue", 0x00, 0x00, 0xCD ) );
        colors.add( new ColorWithName( "MediumOrchid", 0xBA, 0x55, 0xD3 ) );
        colors.add( new ColorWithName( "MediumPurple", 0x93, 0x70, 0xDB ) );
        colors.add( new ColorWithName( "MediumSeaGreen", 0x3C, 0xB3, 0x71 ) );
        colors.add( new ColorWithName( "MediumSlateBlue", 0x7B, 0x68, 0xEE ) );
        colors.add( new ColorWithName( "MediumSpringGreen", 0x00, 0xFA, 0x9A ) );
        colors.add( new ColorWithName( "MediumTurquoise", 0x48, 0xD1, 0xCC ) );
        colors.add( new ColorWithName( "MediumVioletRed", 0xC7, 0x15, 0x85 ) );
        colors.add( new ColorWithName( "MidnightBlue", 0x19, 0x19, 0x70 ) );
        colors.add( new ColorWithName( "MintCream", 0xF5, 0xFF, 0xFA ) );
        colors.add( new ColorWithName( "MistyRose", 0xFF, 0xE4, 0xE1 ) );
        colors.add( new ColorWithName( "Moccasin", 0xFF, 0xE4, 0xB5 ) );

        colors.add( new ColorWithName( "NavajoWhite", 0xFF, 0xDE, 0xAD ) );
        colors.add( new ColorWithName( "Navy", 0x00, 0x00, 0x80 ) );

        colors.add( new ColorWithName( "OldLace", 0xFD, 0xF5, 0xE6 ) );
        colors.add( new ColorWithName( "Olive", 0x80, 0x80, 0x00 ) );
        colors.add( new ColorWithName( "OliveDrab", 0x6B, 0x8E, 0x23 ) );
        colors.add( new ColorWithName( "Orange", 0xFF, 0xA5, 0x00 ) );
        colors.add( new ColorWithName( "OrangeRed", 0xFF, 0x45, 0x00 ) );
        colors.add( new ColorWithName( "Orchid", 0xDA, 0x70, 0xD6 ) );

        colors.add( new ColorWithName( "PaleGoldenRod", 0xEE, 0xE8, 0xAA ) );
        colors.add( new ColorWithName( "PaleGreen", 0x98, 0xFB, 0x98 ) );
        colors.add( new ColorWithName( "PaleTurquoise", 0xAF, 0xEE, 0xEE ) );
        colors.add( new ColorWithName( "PaleVioletRed", 0xDB, 0x70, 0x93 ) );
        colors.add( new ColorWithName( "PapayaWhip", 0xFF, 0xEF, 0xD5 ) );
        colors.add( new ColorWithName( "PeachPuff", 0xFF, 0xDA, 0xB9 ) );
        colors.add( new ColorWithName( "Peru", 0xCD, 0x85, 0x3F ) );
        colors.add( new ColorWithName( "Pink", 0xFF, 0xC0, 0xCB ) );
        colors.add( new ColorWithName( "Plum", 0xDD, 0xA0, 0xDD ) );
        colors.add( new ColorWithName( "PowderBlue", 0xB0, 0xE0, 0xE6 ) );
        colors.add( new ColorWithName( "Purple", 0x80, 0x00, 0x80 ) );

        colors.add( new ColorWithName( "Red", 0xFF, 0x00, 0x00 ) );
        colors.add( new ColorWithName( "RosyBrown", 0xBC, 0x8F, 0x8F ) );
        colors.add( new ColorWithName( "RoyalBlue", 0x41, 0x69, 0xE1 ) );

        colors.add( new ColorWithName( "SaddleBrown", 0x8B, 0x45, 0x13 ) );
        colors.add( new ColorWithName( "Salmon", 0xFA, 0x80, 0x72 ) );
        colors.add( new ColorWithName( "SandyBrown", 0xF4, 0xA4, 0x60 ) );
        colors.add( new ColorWithName( "SeaGreen", 0x2E, 0x8B, 0x57 ) );
        colors.add( new ColorWithName( "SeaShell", 0xFF, 0xF5, 0xEE ) );
        colors.add( new ColorWithName( "Sienna", 0xA0, 0x52, 0x2D ) );
        colors.add( new ColorWithName( "Silver", 0xC0, 0xC0, 0xC0 ) );
        colors.add( new ColorWithName( "SkyBlue", 0x87, 0xCE, 0xEB ) );
        colors.add( new ColorWithName( "SlateBlue", 0x6A, 0x5A, 0xCD ) );
        colors.add( new ColorWithName( "SlateGray", 0x70, 0x80, 0x90 ) );
        colors.add( new ColorWithName( "Snow", 0xFF, 0xFA, 0xFA ) );
        colors.add( new ColorWithName( "SpringGreen", 0x00, 0xFF, 0x7F ) );
        colors.add( new ColorWithName( "SteelBlue", 0x46, 0x82, 0xB4 ) );

        colors.add( new ColorWithName( "Tan", 0xD2, 0xB4, 0x8C ) );
        colors.add( new ColorWithName( "Teal", 0x00, 0x80, 0x80 ) );
        colors.add( new ColorWithName( "Thistle", 0xD8, 0xBF, 0xD8 ) );
        colors.add( new ColorWithName( "Tomato", 0xFF, 0x63, 0x47 ) );
        colors.add( new ColorWithName( "Turquoise", 0x40, 0xE0, 0xD0 ) );

        colors.add( new ColorWithName( "Violet", 0xEE, 0x82, 0xEE ) );

        colors.add( new ColorWithName( "Wheat", 0xF5, 0xDE, 0xB3 ) );
        colors.add( new ColorWithName( "White", 0xFF, 0xFF, 0xFF ) );
        colors.add( new ColorWithName( "WhiteSmoke", 0xF5, 0xF5, 0xF5 ) );

        colors.add( new ColorWithName( "Yellow", 0xFF, 0xFF, 0x00 ) );
        colors.add( new ColorWithName( "YellowGreen", 0x9A, 0xCD, 0x32 ) );
        COLORS = Collections.unmodifiableList( colors );

        DARK_COLOR_CODES.add( "#000000" );
        DARK_COLOR_CODES.add( "#0000FF" );
        DARK_COLOR_CODES.add( "#8A2BE2" );
        DARK_COLOR_CODES.add( "#A52A2A" );
        DARK_COLOR_CODES.add( "#D2691E" );
        DARK_COLOR_CODES.add( "#6495ED" );
        DARK_COLOR_CODES.add( "#DC143C" );
        DARK_COLOR_CODES.add( "#00008B" );
        DARK_COLOR_CODES.add( "#008B8B" );
        DARK_COLOR_CODES.add( "#B8860B" );
        DARK_COLOR_CODES.add( "#006400" );
        DARK_COLOR_CODES.add( "#8B008B" );
        DARK_COLOR_CODES.add( "#556B2F" );
        DARK_COLOR_CODES.add( "#8B0000" );
        DARK_COLOR_CODES.add( "#E9967A" );
        DARK_COLOR_CODES.add( "#8FBC8F" );
        DARK_COLOR_CODES.add( "#483D8B" );
        DARK_COLOR_CODES.add( "#2F4F4F" );
        DARK_COLOR_CODES.add( "#9400D3" );
        DARK_COLOR_CODES.add( "#FF1493" );
        DARK_COLOR_CODES.add( "#696969" );
        DARK_COLOR_CODES.add( "#1E90FF" );
        DARK_COLOR_CODES.add( "#B22222" );
        DARK_COLOR_CODES.add( "#228B22" );
        DARK_COLOR_CODES.add( "#808080" );
        DARK_COLOR_CODES.add( "#008000" );
        DARK_COLOR_CODES.add( "#CD5C5C" );
        DARK_COLOR_CODES.add( "#4B0082" );
        DARK_COLOR_CODES.add( "#20B2AA" );
        DARK_COLOR_CODES.add( "#778899" );
        DARK_COLOR_CODES.add( "#32CD32" );
        DARK_COLOR_CODES.add( "#800000" );
        DARK_COLOR_CODES.add( "#0000CD" );
        DARK_COLOR_CODES.add( "#BA55D3" );
        DARK_COLOR_CODES.add( "#9370DB" );
        DARK_COLOR_CODES.add( "#3CB371" );
        DARK_COLOR_CODES.add( "#7B68EE" );
        DARK_COLOR_CODES.add( "#C71585" );
        DARK_COLOR_CODES.add( "#191970" );
        DARK_COLOR_CODES.add( "#000080" );
        DARK_COLOR_CODES.add( "#808000" );
        DARK_COLOR_CODES.add( "#6B8E23" );
        DARK_COLOR_CODES.add( "#FF4500" );
        DARK_COLOR_CODES.add( "#DB7093" );
        DARK_COLOR_CODES.add( "#CD853F" );
        DARK_COLOR_CODES.add( "#800080" );
        DARK_COLOR_CODES.add( "#663399" );
        DARK_COLOR_CODES.add( "#FF0000" );
        DARK_COLOR_CODES.add( "#BC8F8F" );
        DARK_COLOR_CODES.add( "#4169E1" );
        DARK_COLOR_CODES.add( "#8B4513" );
        DARK_COLOR_CODES.add( "#FA8072" );
        DARK_COLOR_CODES.add( "#F4A460" );
        DARK_COLOR_CODES.add( "#2E8B57" );
        DARK_COLOR_CODES.add( "#A0522D" );
        DARK_COLOR_CODES.add( "#6A5ACD" );
        DARK_COLOR_CODES.add( "#708090" );
        DARK_COLOR_CODES.add( "#4682B4" );
        DARK_COLOR_CODES.add( "#008080" );
        DARK_COLOR_CODES.add( "#FF6347" );
    }

    /**
     * Gets the defined colors.
     * 
     * @return the list of all defined colors
     */
    public static List<ColorWithName> getColors()
    {
        return COLORS;
    }

    /**
     * Verifies if the given color is registered as dark color.
     * 
     * @param color the color to be verified
     * @return true if is registered as dark color
     */
    public static boolean isDarkColor( String color )
    {
        return DARK_COLOR_CODES.contains( color.toUpperCase() );
    }

    /**
     * Class used to define one color and it's corresponding name.
     */
    public static class ColorWithName
    {
        public String hexCode;

        public String name;

        public ColorWithName( String name, int r, int g, int b )
        {
            this.hexCode =
                "#" + HEX_NUMBERS[( r & 0xF0 ) >> 4] + HEX_NUMBERS[( r & 0xF )] + HEX_NUMBERS[( g & 0xF0 ) >> 4]
                    + HEX_NUMBERS[( g & 0xF )] + HEX_NUMBERS[( b & 0xF0 ) >> 4] + HEX_NUMBERS[( b & 0xF )];
            this.name = name;
        }

        public String getHexCode()
        {
            return hexCode;
        }

        public String getName()
        {
            return name;
        }

    }

}
