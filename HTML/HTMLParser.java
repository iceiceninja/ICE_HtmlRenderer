/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package htmlrenderer.HTML;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * @author iceiceninja
 */
public class HTMLParser {
    int pos; //position in input
    String input; // input string

    char nextChar() {
        return input.charAt(pos);
    }

    boolean startsWith(String str) {
        return input.substring(pos).startsWith(str);
    }

    boolean eof() {
        return this.pos >= this.input.length();
    }

    char consumeChar() {
        char currChar = input.charAt(pos);
        pos++;
        return currChar;
    }

    // Consume characters until `test` returns false.   STUDY THIS
    public String consumeWhile(Predicate<Character> test) {
        StringBuilder result = new StringBuilder();
        while (!eof() && test.test(nextChar())) {
            result.append(consumeChar());
        }
        return result.toString();
    }

    void consumeWhitespace() {
        consumeWhile(Character::isWhitespace);  //  The double colon references the method without invoking it btw
    }

    String parseTagName() {
        return consumeWhile(Character::isLetterOrDigit);
    }

    Optional<Node> parseNode() {
        return nextChar() == '<' ? parseElement()  : Optional.of(parseText());
    }

    Node parseText() {
        return new Text(consumeWhile(c -> !c.equals('<')));
    }

    Optional<Node> parseElement() {
        // Opening tag.
        assertChar('<');
        if(startsWith("!--")) //encounter a comment
        {
            while(!startsWith("-->"))
            {
                consumeChar();
            }
            consumeWhile(c -> !c.equals('>'));
            assertChar('>'); // consumes final comment bracket
            return Optional.empty();
        }
        String tagName = parseTagName();
        HashMap<String, String> attrs = parseAttributes();
        assertChar('>');

        // Contents.
        ArrayList<Node> children = parseNodes();

        // Closing tag.
        assertChar('<');
        assertChar('/');
        assertTagName(tagName);
        assertChar('>');

        return Optional.of(new Element(tagName, attrs, children));
    }

    HashMap<String, String> parseAttribute() {
        String name = parseTagName();
        assertChar('=');
        String value = parseAttrValue();
        HashMap<String, String> map = new HashMap<>();
        map.put(name, value);
        return map;
    }

    String parseAttrValue() {
        char openQuote = consumeChar();
        if (openQuote != '"' && openQuote != '\'') //if it is neither double nor single quote
        {
            System.out.println("Parsing of attrubute value error. Expected quote but got: " + openQuote);
        }
        String value = consumeWhile(c -> !c.equals(openQuote));
        char consumedChar = consumeChar();
        if (consumedChar != openQuote) {
            System.out.println("Mismatch between openQuote: " + openQuote + " and consumed char: " + consumedChar);
        }
        return value;
    }

    HashMap<String, String> parseAttributes() {
        HashMap<String, String> attributes = new HashMap<>();
        do {
            consumeWhitespace();
            if (nextChar() == '>' || eof()) {
                break;
            }
            attributes.putAll(parseAttribute());
        } while (true);//nextChar() != '>'
        return attributes;
    }

    ArrayList<Node> parseNodes() {
        ArrayList<Node> nodes = new ArrayList<>();
        do {
            consumeWhitespace();
            if (eof() || startsWith("</")) {
                break;
            }
            Optional<Node> node = parseNode();
            node.ifPresent(nodes::add);
        } while (true);//!eof() && !startsWith("</")
        return nodes;
    }


    //This function below might be external to the HTMLParser class. Don't know if the class needs to worry about this
    public Node parse(String source) {
        this.input = source;
        ArrayList<Node> nodes = parseNodes();
        if (nodes.size() == 1) {
            return nodes.get(0);
        } else {
            return new Element("html", new HashMap<String, String>(), nodes);
        }
    }

    private void assertChar(char expected) {
        char actual = consumeChar();
        if (actual != expected) {
            System.out.println("Expected '" + expected + "', but found '" + actual + "'");
        }
    }

    private void assertTagName(String expected) {
        String actual = parseTagName();
        if (!actual.equals(expected)) {
            System.out.println("Expected tag name '" + expected + "', but found '" + actual + "'");
        }
    }
}
