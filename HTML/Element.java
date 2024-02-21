/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package htmlrenderer.HTML;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 *
 * @author iceiceninja
 */
public class Element extends Node{
    public String tagName;
    public HashMap<String, String> attributes;
    public String id;
    public Element(String name, HashMap<String,String> attrs, ArrayList<Node> children)
    {
        this.children = children;
        this.tagName = name;
        this.attributes = attrs;
    }
    public ArrayList<String> getClasses()
    {
        ArrayList<String> classes = new ArrayList<>(Arrays.asList(attributes
                .getOrDefault("class", "")
                .split(" ")));
        return classes;
    }
    
    public String getId()
    {
        return attributes.getOrDefault("id",null);
    }
}
