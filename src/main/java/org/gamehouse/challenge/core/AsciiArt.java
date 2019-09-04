package org.gamehouse.challenge.core;

import java.awt.*;
import java.awt.image.BufferedImage;

public class AsciiArt {

    public static final int ART_SIZE_MINI = 10;
    public static final int ART_SIZE_SMALL = 12;
    public static final int ART_SIZE_MEDIUM = 18;
    public static final int ART_SIZE_LARGE = 24;
    public static final int ART_SIZE_HUGE = 32;

    private boolean negative;
    private static final String DEFAULT_ART_SYMBOL = "*";

    public enum AsciiArtFont {
        ART_FONT_DIALOG("Dialog"), ART_FONT_DIALOG_INPUT("DialogInput"),
        ART_FONT_MONO("Monospaced"), ART_FONT_SERIF("Serif"), ART_FONT_SANS_SERIF("SansSerif");

        private String value;

        public String getValue() {
            return value;
        }

        private AsciiArtFont(String value) {
            this.value = value;
        }
    }

    public static void main(String[] args) throws Exception {

        System.out.println();
        AsciiArt.printTextArt("Hello", AsciiArt.ART_SIZE_MEDIUM);
        System.out.println();

        System.out.println();
        AsciiArt.printTextArt("Love is life!", AsciiArt.ART_SIZE_SMALL, AsciiArtFont.ART_FONT_MONO, "@");
        System.out.println();

        AsciiArt.printTextArt("Fight!", AsciiArt.ART_SIZE_MINI, AsciiArtFont.ART_FONT_DIALOG_INPUT, "/");
        System.out.println();
    }

    public static String convert(final BufferedImage image, boolean negative) {
        StringBuilder sb = new StringBuilder((image.getWidth() + 1) * image.getHeight());
        for (int y = 0; y < image.getHeight(); y++) {
            if (sb.length() != 0) sb.append("\n");
            for (int x = 0; x < image.getWidth(); x++) {
                Color pixelColor = new Color(image.getRGB(x, y));
                double gValue = (double) pixelColor.getRed() * 0.2989 + (double) pixelColor.getBlue() * 0.5870 + (double) pixelColor.getGreen() * 0.1140;
                final char s = negative ? returnStrNeg(gValue) : returnStrPos(gValue);
                sb.append(s);
            }
        }
        return sb.toString();
    }

    private static char returnStrPos(double g)//takes the grayscale value as parameter
    {
        final char str;

        if (g >= 230.0) {
            str = ' ';
        } else if (g >= 200.0) {
            str = '.';
        } else if (g >= 180.0) {
            str = '*';
        } else if (g >= 160.0) {
            str = ':';
        } else if (g >= 130.0) {
            str = 'o';
        } else if (g >= 100.0) {
            str = '&';
        } else if (g >= 70.0) {
            str = '8';
        } else if (g >= 50.0) {
            str = '#';
        } else {
            str = '@';
        }
        return str; // return the character

    }

    private static char returnStrNeg(double g) {
        final char str;

        if (g >= 230.0) {
            str = '@';
        } else if (g >= 200.0) {
            str = '#';
        } else if (g >= 180.0) {
            str = '8';
        } else if (g >= 160.0) {
            str = '&';
        } else if (g >= 130.0) {
            str = 'o';
        } else if (g >= 100.0) {
            str = ':';
        } else if (g >= 70.0) {
            str = '*';
        } else if (g >= 50.0) {
            str = '.';
        } else {
            str = ' ';
        }
        return str;

    }

    /**
     * Prints ASCII art for the specified text. For size, you can use predefined sizes or a custom size.
     * Usage - printTextArt("Hi",30,AsciiArtFont.ART_FONT_SERIF,"@");
     *
     * @param artText
     * @param textHeight - Use a predefined size or a custom type
     * @param fontType   - Use one of the available fonts
     * @param artSymbol  - Specify the character for printing the ascii art
     * @throws Exception
     */
    public static void printTextArt(String artText, int textHeight, AsciiArtFont fontType, String artSymbol) {
        String fontName = fontType.getValue();
        int imageWidth = findImageWidth(textHeight, artText, fontName);

        BufferedImage image = new BufferedImage(imageWidth, textHeight, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        Font font = new Font(fontName, Font.BOLD, textHeight);
        g.setFont(font);

        Graphics2D graphics = (Graphics2D) g;
        graphics.drawString(artText, 0, getBaselinePosition(g, font));

        for (int y = 0; y < textHeight; y++) {
            StringBuilder sb = new StringBuilder();
            for (int x = 0; x < imageWidth; x++)
                sb.append(image.getRGB(x, y) == Color.WHITE.getRGB() ? artSymbol : " ");
            if (sb.toString().trim().isEmpty())
                continue;
            System.out.println(sb);
        }
    }

    /**
     * Convenience method for printing ascii text art.
     * Font default - Dialog,  Art symbol default - *
     *
     * @param artText
     * @param textHeight
     * @throws Exception
     */
    private static void printTextArt(String artText, int textHeight) {
        printTextArt(artText, textHeight, AsciiArtFont.ART_FONT_DIALOG, DEFAULT_ART_SYMBOL);
    }

    /**
     * Using the Current font and current art text find the width of the full image
     *
     * @param textHeight
     * @param artText
     * @param fontName
     * @return
     */
    private static int findImageWidth(int textHeight, String artText, String fontName) {
        BufferedImage im = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        Graphics g = im.getGraphics();
        g.setFont(new Font(fontName, Font.BOLD, textHeight));
        return g.getFontMetrics().stringWidth(artText);
    }

    /**
     * Find where the text baseline should be drawn so that the characters are within image
     *
     * @param g
     * @param font
     * @return
     */
    private static int getBaselinePosition(Graphics g, Font font) {
        FontMetrics metrics = g.getFontMetrics(font);
        int y = metrics.getAscent() - metrics.getDescent();
        return y;
    }
}
