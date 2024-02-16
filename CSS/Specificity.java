/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package htmlrenderer.CSS;

// https://www.w3.org/TR/selectors/#specificity

public class Specificity {
    public int a;    //count the number of ID selectors in the selector (= A) 
    public int b;    //count the number of class selectors, attributes selectors, and pseudo-classes in the selector (= B) 
    public int c;    //count the number of type selectors and pseudo-elements in the selector (= C) 
}
