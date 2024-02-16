/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package htmlrenderer.Painting;

import htmlrenderer.CSS.CSSColor;
import htmlrenderer.Layout.Rect;

/**
 *
 * @author iceiceninja
 */
public class SolidColor implements DisplayCommand{
    CSSColor color;
    Rect rect;
    
    public SolidColor(CSSColor color, Rect rect)
    {
        this.color = color;
        this.rect = rect;
    }
    
}
