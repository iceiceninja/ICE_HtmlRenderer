/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package htmlrenderer.CSS;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 *
 * @author iceiceninja
 */
public class CSSParser {

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

    SimpleSelector parseSimpleSelector() {
        SimpleSelector selector = new SimpleSelector();
        while (!eof()) {
            switch (nextChar()) {
                case '#':
                    consumeChar();
                    selector.id = parseIdentifier();
                    break;
                case '.':
                    consumeChar();
                    selector.classes.add(parseIdentifier());
                    break;
                case '*':
                    consumeChar();
                    break;
                default:
                    char c = nextChar();
                    if (validIdentifierChar(c)) {
                        selector.tagName = parseIdentifier();
                    }
                    return selector;

            }
        }
        return selector;
    }

    Rule parseRule() {
        return new Rule(parseSelectors(), parseDeclarations());
    }

    public Stylesheet parse(String source) {
        this.input = source;
        ArrayList<Rule> rules = new ArrayList<>();
        while (!eof()) {
            consumeWhitespace();
            rules.add(parseRule());
        }
        return new Stylesheet(rules);
    }

    ArrayList<Declaration> parseDeclarations() {
        ArrayList<Declaration> declarations = new ArrayList<>();
        if (nextChar() == '{') {
            consumeChar();
            consumeWhitespace();
        }
        do {

            declarations.add(parseDeclaration());
            consumeWhitespace();
        } while (nextChar() != '}');
        assertChar('}');
        return declarations;   //  Eventually sort selectors by specificity
    }

    Declaration parseDeclaration() {
        Declaration declaration = new Declaration();

        declaration.name = consumeWhile(c -> c != ':' && c != ' ');
        consumeWhitespace();
        assertChar(':');
        String value = consumeWhile(c -> c != ';').strip();
        boolean startsWithNumber = Pattern.matches("^\\d.*", value);
        if (startsWithNumber) {
            declaration.value.length = declaration.value.length.stringToLength(value); // better way to do this?
        } else {
            switch (declaration.getName()) // figure out better way to do this
            {
                case "background":
                    declaration.value.color.parseString(value);
                    break;
                default:
                    declaration.value.keyword = value;
            }
        }

        assertChar(';');
        return declaration;
    }

    ArrayList<SimpleSelector> parseSelectors() {
        ArrayList<SimpleSelector> selectors = new ArrayList<>();
        do {
            selectors.add(parseSimpleSelector());
            consumeWhitespace();
            char c = nextChar();
            switch (c) {
                case ',':
                    consumeChar();
                    consumeWhitespace();
                    break;
                case '{':
                    return selectors;
                default:
                    System.out.println("Unexpected char in selector list: " + c);
            }
        } while (nextChar() != '{');
        selectors = sortSelectorsBySpecificity(selectors);
//        Collections.reverse(selectors);
        return selectors;   //  IGNORE THIS I FIX IT IN STYLETREE: Have it where sort selector by specificity returns most specific first, but we want least specific first
        //      so we can have the more specific overwrite if needed.
    }

    ArrayList<SimpleSelector> sortSelectorsBySpecificity(ArrayList<SimpleSelector> selectors) {
        return new ArrayList<>(selectors.stream()
                .sorted(Comparator.comparingInt((SimpleSelector s) -> s.getSpecificity().a).reversed() // first sorts by highest a
                        .thenComparingInt(s -> s.getSpecificity().b).reversed() //  then by highest b
                        .thenComparingInt(s -> s.getSpecificity().c).reversed()) // then by highest c

                .toList());
    }

    private String parseIdentifier() {
        consumeWhitespace();
        return consumeWhile(c -> c != '{' && !Character.isWhitespace(c));
//        return consumeWhile(c -> c != '{'); //  Make sure this is what you want
    }

    private void assertChar(char expected) {
        char actual = consumeChar();
        if (actual != expected) {
            System.out.println("Expected '" + expected + "', but found '" + actual + "'");
        }
    }

    private boolean validIdentifierChar(char c) {
        return true;    //  As of right now, assumes all characters are valid.
    }
}
