/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package htmlrenderer.Layout;

/**
 *
 * @author iceiceninja
 */
public class Rect {

    public float x;
    public float y;
    public float width;
    public float height;

    public Rect(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Rect() {

    }

    Rect expandedBy(EdgeSizes edge) {
        return new Rect(
                this.x - edge.left,
                this.y - edge.top,
                this.width + edge.left + edge.right,
                this.height + edge.top + edge.bottom
        );
    }
}
