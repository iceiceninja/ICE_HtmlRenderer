/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package htmlrenderer.CSS;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author iceiceninja
 */
public class Length {

    private final float value;
    private final Unit unit;

    public Length() {
        this.value = 0;
        this.unit = Unit.Px;
    }

    public Length(Length length) {
        this.value = length.getValue();
        this.unit = length.getUnit();
    }

    public Length(float value, Unit unit) {
        this.value = value;
        this.unit = unit;
    }

    public float getValue() {
        return this.value;
    }

    public Unit getUnit() {
        return this.unit;
    }

    public Length stringToLength(String str) //  This function expects something like "120px" and sets the length to 120 and unit to px
    {

        Pattern pattern = Pattern.compile("\\s*(\\d+(?:\\.\\d+)?)\\s*(\\D+)?"); // Regular expression pattern
        Matcher matcher = pattern.matcher(str);

        if (matcher.matches()) {
            String numberPart = matcher.group(1); // Extract the number part

            String unitPart = matcher.group(2);   // Extract the unit part
            switch (unitPart.toLowerCase().strip()) {
                case "px":
                    return new Length(Float.parseFloat(numberPart), Unit.Px);
                default:
                    return new Length(Float.parseFloat(numberPart), Unit.Px);
            }
        }
        return new Length(0.0f,Unit.Px);
    }
}
