/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package htmlrenderer.Painting;

import htmlrenderer.CSS.CSSColor;
import htmlrenderer.Layout.Rect;

import java.util.ArrayList;

/**
 *
 * @author iceiceninja
 */
public class Canvas {

    ArrayList<CSSColor> pixels = new ArrayList<>();
    public float width;
    public float height;

    ArrayList<DisplayText> text = new ArrayList<>();

//    public Canvas(ArrayList<Color> pixels, float width, float height) {
//        this.pixels = pixels;
//        this.width = width;
//        this.height = height;
//    }

    public Canvas(float width, float height) {
        this.width = width;
        this.height = height;
        CSSColor white = new CSSColor(255, 255, 255, 255);
        for (int i = 0; i < width * height; i++) {
            this.pixels.add(white);
        }
        
    }

    public void paintItem(DisplayCommand item) {
        String className = item.getClass().getSimpleName();
        switch (className) {
            case "SolidColor":
                SolidColor solidColor = (SolidColor) item;
                CSSColor color = solidColor.color;
                Rect rect = solidColor.rect;
                
                // Clip the rectangle to the canvas boundaries.
                int x0 = (int) Math.min(Math.max(rect.x, 0), this.width);
                int x1 = (int) Math.min(Math.max(rect.x + rect.width, 0), this.width);
                int y0 = (int) Math.min(Math.max(rect.y, 0), this.height);
                int y1 = (int) Math.min(Math.max(rect.y + rect.height, 0), this.height);
                for (int y = y0; y < y1; y++) {
                    for (int x = x0; x < x1; x++) {
                        // TODO: alpha compositing with existing pixel
                        int index = (int) (Math.floor(x) + Math.floor(y) * this.width);
                        this.pixels.set(index,color);
                    }
                }
                break;
            case "DisplayText":
                DisplayText displayText = (DisplayText) item; // im pretty sure divs can overlap text
                // (if people try hard enough) so make them a part of display list and render here if possible.
                text.add(displayText);
                break;
            default:
                System.out.println("ERROR: classname \"" + className + "\" is not recognized");
        }
    }
}
