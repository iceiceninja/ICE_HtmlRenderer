/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package htmlrenderer.CSS;

/**
 *
 * @author iceiceninja
 */
public class Value {
    String keyword = "auto";
    CSSColor color;
    Length length;
    public Value()
    {
        this.color = new CSSColor();
        this.length = new Length();
    }
    public Value(String keyword)
    {
        this.color = new CSSColor();
        this.length = new Length();
        this.keyword = keyword;
    }
    public Length getLength()
    {
        return length;
    }
    public void setLength(Length length)
    {
        this.length = length;
    }
    public String getKeyword()
    {
        return keyword;
    }
    public CSSColor getColor()
    {
        return color;
    }
   
}
