/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package htmlrenderer.Layout;

import htmlrenderer.CSS.Value;
import htmlrenderer.HTML.Node;
import htmlrenderer.Style.StyledNode;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author iceiceninja
 */
public class AnonymousBlock extends LayoutBox {

    @Override
    protected StyledNode getStyleNodeInternal() {
        // Implement behavior specific to AnonymousBlock
        // Example: No need to return anything, just perform actions
        System.out.println("Anonymous block box has no style node!!!!!!!!");
        return new StyledNode(new Node(), new HashMap<String, Value>(), new ArrayList<StyledNode>());
//        throw new UnsupportedOperationException("Anonymous block box has no style node");
    }

    @Override
    public String getBoxType() {
        return "AnonymousBlock";
    }
}
