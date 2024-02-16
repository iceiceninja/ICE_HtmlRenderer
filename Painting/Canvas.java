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
//                int y0 = x0;
//                int y1 = x1;
// y0 is not accounting for 12px padding (it should increase like x0) and
                // y1 is not accounting for 12 px padding (it should decrease like x1)
                // the rect.y is increasing by 12 and the rect.height is increasing by 12px 
                
                // y should be increased by 12px (due to padding) but should not be at 800 like it currently is
                // it makes sense that it takes the previous dimensions and adds them, but initially 
                // it takes the bounds of the canvas and sets y = 800. Figure out a way around this
                // beyond that I believe the height should only be increased for the outer/parent classes/rects
                // since they need to contain the children, but I believe the center child should only height of 24px
                // (due to padding top + padding bottom) but all of the outer rectangles need to have 12px all around their child
                // 
                // Width appears to be correct (stretching the whole screen), but height should be calculated by height of children
                // which the smallest child should only be 24px tall
                // and the other heights should be 24px more that the prev
                // the y however should 
                for (int y = y0; y < y1; y++) {
                    for (int x = x0; x < x1; x++) {
                        // TODO: alpha compositing with existing pixel
                        int index = (int) (Math.floor(x) + Math.floor(y) * this.width);
                        this.pixels.set(index,color);
                    }
                }
                break;
            default:
                System.out.println("ERROR: classname \"" + className + "\" is not recognized");
        }
    }
}
