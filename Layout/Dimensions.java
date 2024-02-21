/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package htmlrenderer.Layout;

/**
 *
 * @author iceiceninja
 */
public class Dimensions {
    Rect content = new Rect(0.0f,0.0f,0.0f,0.0f);
    
    EdgeSizes padding = new EdgeSizes(0.0f,0.0f,0.0f,0.0f);
    EdgeSizes border = new EdgeSizes(0.0f,0.0f,0.0f,0.0f);
    EdgeSizes margin = new EdgeSizes(0.0f,0.0f,0.0f,0.0f);
    
    public Dimensions(Rect content, EdgeSizes padding, EdgeSizes border, EdgeSizes margin)
    {
        this.content = content;
        this.padding = padding;
        this.border = border;
        this.margin = margin;
    }
    public Dimensions()
    {
//        this.content = new Rect(0.0f,0.0f,0.0f,0.0f);
//        this.padding = new EdgeSizes(0.0f,0.0f,0.0f,0.0f);
//        this.border = new EdgeSizes(0.0f,0.0f,0.0f,0.0f);
//        this.margin = new EdgeSizes(0.0f,0.0f,0.0f,0.0f);
    }
    public Rect getContent()
    {
        return this.content;
    }
    public Rect marginBox()
    {
        return this.borderBox().expandedBy(this.margin);
    }
    public Rect paddingBox()
    {
        return this.content.expandedBy(this.padding);
    }
    public Rect borderBox()
    {
        return this.paddingBox().expandedBy(border);
    }
    public EdgeSizes getBorder()
    {
        return this.border;
    }
    
}
