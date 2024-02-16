/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package htmlrenderer.Painting;

import htmlrenderer.CSS.CSSColor;
import htmlrenderer.CSS.Value;
import htmlrenderer.Layout.Dimensions;
import htmlrenderer.Layout.LayoutBox;
import htmlrenderer.Layout.Rect;
import htmlrenderer.Style.StyledNode;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

/**
 *
 * @author iceiceninja
 */
public class Painter {

    DisplayList buildDisplayList(LayoutBox layoutRoot) {
        DisplayList displayList = new DisplayList();
        renderLayoutBox(displayList, layoutRoot);
        return displayList;
    }

    private void renderLayoutBox(DisplayList displayList, LayoutBox layoutBox) {
        renderBackground(displayList, layoutBox);
        renderBorders(displayList, layoutBox);
        //renderText(displayList, layoutBox);
        // TODO: render text
        for (LayoutBox child : layoutBox.getChildren()) {
            renderLayoutBox(displayList, child);
        }
    }
    public void renderText(DisplayList displayList, LayoutBox layoutBox)
    {

    }
    public void renderBackground(DisplayList list, LayoutBox layoutBox) {
        Optional<CSSColor> color = getColor(layoutBox, "background");
        if (color != null && color.isPresent()) {
            list.displayList.add(new SolidColor(color.get(), layoutBox.getDimensions().borderBox()));
        }

    }

    // Return the specified color for CSS property `name`, or Optional.empty() if no color was specified.
    public Optional<CSSColor> getColor(LayoutBox layoutBox, String name) {
        switch (layoutBox.getBoxType()) {
            case "BlockNode":
            case "InlineNode":
                StyledNode style = layoutBox.getStyleNode();
                if (style != null) {
                    Value value = style.getValue(name).orElse(null);
//                    if (value instanceof CSSColor) {
//                        return Optional.of(((ColorValue) value).getColor());
//                    }
                    if (value != null) {
                        return Optional.of(value.getColor());
                    }
                    return Optional.empty();
                }
                return Optional.empty();
            case "AnonymousBlock":
            default:
                return Optional.empty();
        }
    }

    public void renderBorders(DisplayList list, LayoutBox layoutBox) {
        CSSColor color = getColor(layoutBox, "border-color").orElse(new CSSColor(0,0,0,255)); // default is black
        Dimensions d = layoutBox.getDimensions();
        Rect borderBox = d.borderBox();

        //left border
        list.displayList.add(new SolidColor(color, new Rect(
                borderBox.x,
                borderBox.y,
                d.getBorder().left,
                borderBox.height
        )));

        //right border
        list.displayList.add(new SolidColor(color, new Rect(
                borderBox.x + borderBox.width - d.getBorder().right,
                borderBox.y,
                d.getBorder().right,
                borderBox.height
        )));

        //top border
        list.displayList.add(new SolidColor(color, new Rect(
                borderBox.x,
                borderBox.y,
                borderBox.width,
                d.getBorder().top
        )));

        //bottom border
        list.displayList.add(new SolidColor(color, new Rect(
                borderBox.x,
                borderBox.y + borderBox.height - d.getBorder().bottom,
                borderBox.width,
                d.getBorder().bottom
        )));

    }

    public Canvas paint(LayoutBox layoutRoot, Rect bounds) {
        DisplayList displayList = buildDisplayList(layoutRoot);
        Canvas canvas = new Canvas(bounds.width, bounds.height);
        for (DisplayCommand item : displayList.displayList) {
            canvas.paintItem(item);
        }
        return canvas;
    }

    public void CanvasToPng(Canvas canvas) {
        BufferedImage image = new BufferedImage(Math.round(canvas.width), Math.round(canvas.height), BufferedImage.TYPE_INT_ARGB);
        for (int x = 0; x < canvas.width; x++) {
            for (int y = 0; y < canvas.height; y++) {

                image.setRGB(x, y, canvas.pixels.get(x + (Math.round(canvas.height) * y)).toAwtColor().getRGB());
            }
        }
        File outputFile = new File("output.png");
        try {
            ImageIO.write(image, "png", outputFile);
            System.out.println("Image saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving image: " + e.getMessage());
        }
    }

}
