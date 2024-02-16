/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package htmlrenderer.CSS;

import java.util.ArrayList;

/**
 *
 * @author iceiceninja
 */
public class Rule {
    public ArrayList<SimpleSelector> selectors;
    public ArrayList<Declaration> declarations;
    public Rule(ArrayList<SimpleSelector> selectors, ArrayList<Declaration> declarations)
    {
        this.selectors = selectors;
        this.declarations = declarations;
    }
}
