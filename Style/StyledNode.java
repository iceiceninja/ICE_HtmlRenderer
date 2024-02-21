/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package htmlrenderer.Style;

import htmlrenderer.CSS.Value;
import htmlrenderer.HTML.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

/**
 *
 * @author iceiceninja
 */
public class StyledNode {

    Node node;
    HashMap<String, Value> specifiedValues;
    ArrayList<StyledNode> children;

    public StyledNode(Node node, HashMap<String, Value> specifiedValues, ArrayList<StyledNode> children) {
        this.node = node;
        this.specifiedValues = specifiedValues;
        this.children = children;
    }

    public Optional<Value> getValue(String name) {
        return Optional.ofNullable(specifiedValues.get(name));
    }

    public Display display() {
        String test = getValue("display").orElse(new Value("inline")).getKeyword().toLowerCase().strip();
        return switch (test) {
            case "block" -> Display.Block;
            case "none" -> Display.None;
            default -> Display.Inline;
        };
    }

    public Node getNode()
    {
            return node;
    }
    public ArrayList<StyledNode> getChildren() {
        return this.children;
    }

    /*
        This uses a helper function called lookup, which just tries a series of values in sequence. 
        If the first property isn’t set, it tries the second one. If that’s not set either, it returns the given default value. 
        This provides an incomplete (but simple) implementation of shorthand properties and initial values.
     */
    public Value lookup(String value1, String value2, Value defaultValue) {
        if(this.specifiedValues.get(value1) != null)
        {
            return this.specifiedValues.get(value1);
        }else if(this.specifiedValues.get(value2) != null)
        {
            return this.specifiedValues.get(value2);
        }else
        {
            return defaultValue;
        }
    }
}
