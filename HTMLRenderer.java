/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package htmlrenderer;

import htmlrenderer.CSS.CSSParser;
import htmlrenderer.CSS.Stylesheet;
import htmlrenderer.HTML.HTMLParser;
import htmlrenderer.HTML.Node;
import htmlrenderer.Layout.*;
import htmlrenderer.Painting.Canvas;
import htmlrenderer.Painting.Painter;
import htmlrenderer.Style.StyleTree;
import htmlrenderer.Style.StyledNode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author iceiceninja
 */
public class HTMLRenderer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //parse command line args. -c for css and -h for html
        System.out.println(Arrays.toString(args));
        String htmlFileName = "";
        String cssFileName = "";
        for(int i = 0; i < args.length; i++)
        {
            String currArg = args[i];
            switch(currArg)
            {
                case "-h":
                    htmlFileName = args[i+1];
                    break;
                case "-c":
                    cssFileName = args[i+1];
                    break;
                default:
                    break;
            }
        }
        if(htmlFileName.isEmpty())
        {
            System.out.println("HTML file not found: " + htmlFileName);
        }
        if(cssFileName.isEmpty())
        {
            System.out.println("CSS File not found: " + cssFileName);
        }
        EdgeSizes zero = new EdgeSizes(0.0f,0.0f,0.0f,0.0f);
        Dimensions initContainingBlock = new Dimensions
        (
                new Rect(0.0f,0.0f,800,800), //hard coded size 800, 600
                new EdgeSizes(zero),
                new EdgeSizes(zero),
                new EdgeSizes(zero)
        );
        float canvasHeight = initContainingBlock.getContent().height;
        HTMLParser htmlParser = new HTMLParser();
        CSSParser cssParser = new CSSParser();
        StyleTree styleTree = new StyleTree();
        LayoutTree layoutTree = new LayoutTree();
        Painter painter = new Painter();
        
        Node rootNode = htmlParser.parse(readFileToString(htmlFileName)); // Takes in html source file's text. Needs string
        Stylesheet styleSheet = cssParser.parse(readFileToString(cssFileName));    //Eventually get the css from html?
        StyledNode styleRoot = styleTree.styleTree(rootNode, styleSheet);
        LayoutBox layoutRoot =  layoutTree.layoutTree(styleRoot, initContainingBlock);
        initContainingBlock.getContent().height = canvasHeight; // Check over, this may cause issue
        painter.paint(layoutRoot, initContainingBlock.getContent());
//        painter.CanvasToPng(canvas);
    }
    
    public static String readFileToString(String filePath) {
        try {
            // Read all lines from the file into a List<String>
            List<String> lines = Files.readAllLines(Paths.get(filePath));

            // Join the lines into a single string using newline as the delimiter
            return String.join(System.lineSeparator(), lines);
        } catch (IOException e) {
            // Handle IOException (e.g., file not found, permission issues)
            e.printStackTrace();
            return null;
        }
    }
    
}
