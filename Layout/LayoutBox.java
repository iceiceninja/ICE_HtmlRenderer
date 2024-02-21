/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package htmlrenderer.Layout;

import htmlrenderer.CSS.Length;
import htmlrenderer.CSS.Unit;
import htmlrenderer.CSS.Value;
import htmlrenderer.Style.StyledNode;

import java.util.ArrayList;

/**
 *
 * @author iceiceninja
 */
public abstract class LayoutBox {

    Dimensions dimensions = new Dimensions();
    ArrayList<LayoutBox> children = new ArrayList<>();

    public ArrayList<LayoutBox> getChildren() {
        return children;
    }

    public Dimensions getDimensions() {
        return dimensions;
    }

    public abstract String getBoxType();

    LayoutBox getInlineContainer() {
        if (this instanceof InlineNode || this instanceof AnonymousBlock) {
            return this;
        } else if (this instanceof BlockNode) {
            if (this.children.isEmpty() || !(this.children.get(this.children.size() - 1) instanceof AnonymousBlock)) {
                this.children.add(new AnonymousBlock());
            }
            return this.children.get(this.children.size() - 1);
        } else {
            throw new UnsupportedOperationException("Unsupported box type");
        }
    }

    void layout(Dimensions containingBlock) {
        if (this instanceof BlockNode) {
            layoutBlock(containingBlock);
        }
        if (this instanceof InlineNode) {
            System.out.println("Warning: InlineNode layout not yet implemented");   //TODO
        }
        if (this instanceof AnonymousBlock) {
            System.out.println("Warning: AnonymousBlock layout not yet implemented");    //TODO
        }
    }

    void layoutBlock(Dimensions containingBlock) {
        calculateBlockWidth(containingBlock);   //Child width depends on parent width

        calculateBlockPosition(containingBlock);   //determine box location within container

        layoutBlockChildren();  // recursively lay out children 

        calculateBlockHeight();   //  parent height depends on child height

    }

    private void calculateBlockWidth(Dimensions containingBlock) {
        StyledNode style = getStyleNode();   //Implement

        String auto = "auto";

        Value width = style.getValue("width").orElse(new Value("auto"));

        Length zero = new Length(0, Unit.Px);
        Value zeroValue = new Value();
        zeroValue.setLength(zero);

        Value marginLeft = style.lookup("margin-left", "margin", zeroValue);
        Value marginRight = style.lookup("margin-right", "margin", zeroValue);

        Value borderLeft = style.lookup("border-left", "border", zeroValue);
        Value borderRight = style.lookup("border-right", "border", zeroValue);

        Value paddingLeft = style.lookup("padding-left", "padding", zeroValue);
        Value paddingRight = style.lookup("padding-right", "padding", zeroValue);

        /*
        TODO: See how CSS Calculates block width based on above values
         */
        float[] test = {marginLeft.getLength().getValue(), marginRight.getLength().getValue(),
            borderLeft.getLength().getValue(), borderRight.getLength().getValue(),
            paddingLeft.getLength().getValue(), paddingRight.getLength().getValue(),
            width.getLength().getValue()};
        float sum = 0;
        for (float num : test) {
            sum += num;
        }
        // If width is not auto and the total is wider than the container, treat auto margins as 0.
        if (!"auto".equals(width.getKeyword()) && sum > containingBlock.content.width) {
            if ("auto".equals(marginLeft.getKeyword())) {
                marginLeft.setLength(new Length(0.0f, Unit.Px));
            }
            if ("auto".equals(marginRight.getKeyword())) {
                marginRight.setLength(new Length(0.0f, Unit.Px));
            }
        }

        float underflow = containingBlock.content.width - sum;

        if (!"auto".equals(width.getKeyword())
                && !"auto".equals(marginLeft.getKeyword())
                && !"auto".equals(marginRight.getKeyword())) {
            marginRight.setLength(new Length(marginRight.getLength().getValue() + underflow, Unit.Px));
        } else if (!"auto".equals(width.getKeyword())
                && !"auto".equals(marginLeft.getKeyword())
                && "auto".equals(marginRight.getKeyword())) {
            marginRight.setLength(new Length(underflow, Unit.Px));
        } else if (!"auto".equals(width.getKeyword())
                && "auto".equals(marginLeft.getKeyword())
                && !"auto".equals(marginRight.getKeyword())) {
            marginLeft.setLength(new Length(underflow, Unit.Px));
        } else if ("auto".equals(width.getKeyword())) {
            if ("auto".equals(marginLeft.getKeyword())) {
                marginLeft.setLength(new Length(0.0f, Unit.Px));
            }
            if ("auto".equals(marginRight.getKeyword())) {
                marginRight.setLength(new Length(0.0f, Unit.Px));
            }
            if (underflow >= 0.0) {
                width.setLength(new Length(underflow, Unit.Px));
            } else {
                width.setLength(new Length(0.0f, Unit.Px));
                marginRight.setLength(new Length(marginRight.getLength().getValue() + underflow, Unit.Px));
            }
        } else if (!"auto".equals(width.getKeyword())
                && "auto".equals(marginLeft.getKeyword())
                && "auto".equals(marginRight.getKeyword())) {
            marginLeft.setLength(new Length(underflow / 2.0f, Unit.Px));
            marginRight.setLength(new Length(underflow / 2.0f, Unit.Px));
        }
        dimensions.content.width = width.getLength().getValue();

        dimensions.padding.left = paddingLeft.getLength().getValue();
        dimensions.padding.right = paddingRight.getLength().getValue();

        dimensions.border.left = borderLeft.getLength().getValue();
        dimensions.border.right = borderRight.getLength().getValue();

        dimensions.margin.left = marginLeft.getLength().getValue();
        dimensions.margin.right = marginRight.getLength().getValue();

    }

    private void calculateBlockPosition(Dimensions containingBlock) {
        StyledNode styledNode = getStyleNode();
        Length zero = new Length(0, Unit.Px);
        Value zeroValue = new Value();
        zeroValue.setLength(zero);

        dimensions.margin.top = styledNode.lookup("margin-top", "margin", zeroValue).getLength().getValue();
        dimensions.margin.bottom = styledNode.lookup("margin-bottom", "margin", zeroValue).getLength().getValue();

        dimensions.padding.top = styledNode.lookup("padding-top", "padding", zeroValue).getLength().getValue();
        dimensions.padding.bottom = styledNode.lookup("padding-bottom", "padding", zeroValue).getLength().getValue();

        dimensions.border.top = styledNode.lookup("border-top", "border", zeroValue).getLength().getValue();
        dimensions.border.bottom = styledNode.lookup("border-bottom", "border", zeroValue).getLength().getValue();

        dimensions.content.x = containingBlock.content.x + dimensions.padding.left + dimensions.margin.left
                + dimensions.border.left;

        dimensions.content.y = containingBlock.content.y + containingBlock.content.height +
                dimensions.margin.top + dimensions.border.top + dimensions.padding.top;   //  originally was adding containingBlock.content.height
        //  but this was causing issues since the canvas is initially passed 
        //  in and it is 800 by 600 and would cause the height to be the max
        //  I am assuming that there is a purpose to this, so add back if 
        // encountering issues
    }

    private void layoutBlockChildren() {
        for (LayoutBox child : children) {
            child.layout(this.dimensions);
            // Track the height so each child is laid out below the previous content.
            this.dimensions.content.height = this.dimensions.content.height + child.dimensions.marginBox().height;
        }
    }

    public StyledNode getStyleNode() {
        return getStyleNodeInternal();
    }

    protected abstract StyledNode getStyleNodeInternal();

    void calculateBlockHeight() {
        // getStyleNode and then check to see if it has the 'height' attribute set, 
        //  if it does then set dimensions.content.height equal to it

        // If the height is set to an explicit length, use that exact length.
        // Otherwise, just keep the value set by `layout_block_children`.
        StyledNode styleNode = getStyleNode();
        Value height = styleNode.getValue("height").orElse(null);
        if (height != null && height.getLength().getUnit() == Unit.Px) {
            // Assuming dimensions is a member of the current class
            this.dimensions.content.height = height.getLength().getValue();
        }

    }
}
