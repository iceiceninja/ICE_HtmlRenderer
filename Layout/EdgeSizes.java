/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package htmlrenderer.Layout;

/**
 *
 * @author iceiceninja
 */
public class EdgeSizes {
    public float left;
    public float right;
    public float top;
    public float bottom;
    public EdgeSizes(float left, float right, float top, float bottom)
    {
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;
    }
    public EdgeSizes(EdgeSizes edgeSizes)
    {
        this.left = edgeSizes.left;
        this.right = edgeSizes.right;
        this.top = edgeSizes.top;
        this.bottom = edgeSizes.bottom;
    }
}
