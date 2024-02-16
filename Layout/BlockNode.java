/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package htmlrenderer.Layout;

import htmlrenderer.Style.StyledNode;

/**
 *
 * @author iceiceninja
 */
public class BlockNode extends LayoutBox{
    StyledNode node;
    public BlockNode(StyledNode node)
    {
        this.node = node;
    }
      @Override
    protected StyledNode getStyleNodeInternal() {
        return node;
    }

    @Override
    public String getBoxType() {
        return "BlockNode";
    }
}
