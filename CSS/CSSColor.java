/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package htmlrenderer.CSS;

import java.awt.*;

/**
 *
 * @author iceiceninja
 */
public class CSSColor {

    private int r;
    private int g;
    private int b;
    private int a;

    public CSSColor() {

    }

    public CSSColor(int r, int g, int b, int a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public int getR() {
        return r;
    }

    public int getG() {
        return g;
    }

    public int getB() {
        return b;
    }

    public int getA() {
        return a;
    }
    public void parseString(String colorString) {
        //String rgbValues = colorString.substring(5, colorString.length() - 1); // only parses rgb(255,255,255) Have it identify and decode hex

// Split the substring into individual components
        //String[] rgbComponents = rgbValues.split(",");
        String[] rgbComponents = colorString.replaceAll("[^0-9,]", "").split(",");
// Parse the components as integers
        this.r = Integer.parseInt(rgbComponents[0]);
        this.g = Integer.parseInt(rgbComponents[1]);
        this.b = Integer.parseInt(rgbComponents[2]);
        this.a = 255;    // HARD CODING OPAQUE COLORS!!!!! FIX FOR TRANSPARENCY
    }
     public Color toAwtColor() {
        return new Color(r, g, b, a);
    }
}
