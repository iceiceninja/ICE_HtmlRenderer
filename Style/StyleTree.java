/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package htmlrenderer.Style;

import htmlrenderer.CSS.*;
import htmlrenderer.HTML.Element;
import htmlrenderer.HTML.Node;
import htmlrenderer.HTML.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 *
 * @author iceiceninja
 */
public class StyleTree {

    boolean matches(Element element, SimpleSelector selector) {
        return matchesSimpleSelector(element, selector);
    }

    private boolean matchesSimpleSelector(Element element, SimpleSelector selector) {
//                // Check type selector
// I believe we want it to check if the element contains anything that is not a part of the selector, but the selector being blank/empty
// technically means that there is nothing that the element doesn't match with
        // Check type selector
        if (selector.tagName != null && !selector.tagName.isEmpty() && !selector.tagName.contentEquals(element.tagName)) {
            return false;
        }

        // Check ID selector
        if (selector.id != null && !selector.id.isEmpty() && !selector.id.equals(element.id)) {
            return false;
        }

        // Check class selectors
        ArrayList<String> elemClasses = element.getClasses();
        if (selector.classes.stream().anyMatch(classValue -> !elemClasses.contains(classValue))) {
            return false;
        }

        // We didn't find any non-matching selector components.
        return true;
    }

    Optional<MatchedRule> matchRule(Element element, Rule rule) {
        return rule.selectors.stream()
                .filter(selector -> matches(element, selector))
                .map(selector -> new MatchedRule(selector.getSpecificity(), rule))
                .findFirst();
    }

    ArrayList<MatchedRule> matchingRules(Element element, Stylesheet stylesheet) {
        return stylesheet.getRules().stream()
                .map(rule -> matchRule(element, rule))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    HashMap<String, Value> specifiedValues(Element element, Stylesheet stylesheet) {
        HashMap<String, Value> values = new HashMap<>();
        ArrayList<MatchedRule> rules = matchingRules(element, stylesheet);

//        Collections.reverse();
        // we want to go through rules from lowest to highest specificity
        rules.sort((a, b) -> Integer.compare(a.specificity.c, b.specificity.c));
        Collections.reverse(rules);
        for (MatchedRule rule : rules) {
            for (Declaration declaration : rule.rule.declarations) {
                values.put(declaration.getName(), declaration.getValue());
            }
        }
        return values;
    }

    public StyledNode styleTree(Node root, Stylesheet stylesheet) {
        HashMap<String, Value> specifiedValues;
        if (root instanceof Element) {
            specifiedValues = specifiedValues((Element) root, stylesheet);
        } else if (root instanceof Text) {
            specifiedValues = new HashMap<>();
        } else {
            throw new IllegalArgumentException("Unsupported node type");
        }
        ArrayList<StyledNode> children = root.children.stream()
                .map(child -> styleTree(child, stylesheet))
                .collect(Collectors.toCollection(ArrayList::new));

        return new StyledNode(root, specifiedValues, children);
    }
}
