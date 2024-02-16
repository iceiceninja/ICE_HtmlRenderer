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
public class InlineNode extends LayoutBox {

    StyledNode node;

    public InlineNode(StyledNode styledNode) {
        this.node = styledNode;
    }

    @Override
    protected StyledNode getStyleNodeInternal() {
        // Implement behavior specific to InlineNode
        return node;
    }

    @Override
    public String getBoxType() {
        return "InlineNode";
    }
}
