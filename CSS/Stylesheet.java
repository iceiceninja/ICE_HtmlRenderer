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
public class Stylesheet {
    ArrayList<Rule> rules;
    public Stylesheet(ArrayList<Rule> rules)
    {
        this.rules = rules;
    }
    public ArrayList<Rule> getRules()
    {
        return rules;
    }
}
