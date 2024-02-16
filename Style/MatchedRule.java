/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package htmlrenderer.Style;

import htmlrenderer.CSS.Rule;
import htmlrenderer.CSS.Specificity;

/**
 *
 * @author iceiceninja
 */
public class MatchedRule {
    Specificity specificity;
    Rule rule;
    public MatchedRule(Specificity specificity, Rule rule)
    {
        this.specificity = specificity;
        this.rule = rule;
    }
}
