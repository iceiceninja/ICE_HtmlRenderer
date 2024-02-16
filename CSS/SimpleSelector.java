/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package htmlrenderer.CSS;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author iceiceninja
 */
public class SimpleSelector {
    public String tagName;
    public String id;
    public List<String> classes = new ArrayList<>();

    public Specificity getSpecificity()
    {
        Specificity specificity = new Specificity();
        specificity.a = id != null && id.isEmpty() ? 0 : 1;   //  If more than one id can be present then update this.
        specificity.b = classes.size();
        specificity.c = tagName != null && tagName.isEmpty()  ? 0 : 1; // If More than one tagnames can be present, update this
        return specificity;
    }
}
