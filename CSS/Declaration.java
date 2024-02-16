/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package htmlrenderer.CSS;

/**
 *
 * @author iceiceninja
 */
public class Declaration {

    String name;
    Value value;
    public Declaration()
    {
        this.value = new Value();
    }

    public String getName() {
        return name;
    }

    public Value getValue() {
        return value;
    }
}
