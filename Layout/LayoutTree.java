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
public class LayoutTree {

    public LayoutBox buildLayoutTree(StyledNode styledNode) {
        LayoutBox root;
        //Create root box
        switch (styledNode.display()) {
            case Block:
                root = new BlockNode(styledNode);
                break;
            case Inline:
                root = new InlineNode(styledNode);
                break;
            case None:
                System.out.println("ERROR: ROOT NODE HAS DISPLAY: NONE!!!");
            default:
                System.out.println("Display for root not defined. Defaulting to InLine");
                root = new InlineNode(styledNode);

        }
        for (StyledNode child : styledNode.getChildren()) {
            switch (child.display()) {
                case Block:
                    root.children.add(buildLayoutTree(child));
                    break;
                case Inline:
                    root.getInlineContainer().children.add(buildLayoutTree(child));
                    break;
                case None:
                    break; // skipping nodes with display none
                default:
                    System.out.println("Display for node not supported");
            }
        }
        return root;
    }
    public LayoutBox layoutTree(StyledNode node, Dimensions containingBlock)
    {
        // TODO: Save the initial containing block height, for calculating percent heights.
        containingBlock.content.height = 0.0f;
        LayoutBox rootBox = buildLayoutTree(node);
        rootBox.layout(containingBlock);
        return rootBox;
    } 
}
