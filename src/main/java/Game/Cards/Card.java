package Game.Cards;

import Graphics.Sprite;

import static Game.Cards.Card.Suit.CLUBS;

public class Card {

    public static enum Suit {
        SPADES, CLUBS, DIAMONDS, HEARTS
    }

    public static enum Type {
        NUMERIC, ACE, JACK, QUEEN, KING
    }

    private int value;
    private Sprite sprite;
    private Suit suit;
    private Type type;

    public Card(int value, Suit suit, Type type, String code) {
        this.value = value;
        this.suit = suit;
        this.type = type;
        this.sprite = new Sprite("src/main/resources/Images/Cards/" + code + ".png");
    }

    public static Card decodeCardFromString(String code) {
        char suitCode = code.charAt(0);
        String valCode = code.substring(1);

        Suit suit;
        switch (suitCode) {
            case 'C' -> suit = CLUBS;
            case 'D' -> suit = Suit.DIAMONDS;
            case 'S' -> suit = Suit.SPADES;
            case 'H' -> suit = Suit.HEARTS;
            default -> throw new IllegalStateException("Unexpected value: " + suitCode);
        }

        // 1 means 10 here, A means 1 (ace)
        Type type;
        switch (valCode.charAt(0)) {
            case 'A' -> type = Type.ACE;
            case '2', '3', '4', '5', '6', '7', '8', '9', '1' -> type = Type.NUMERIC;
            case 'J' -> type = Type.JACK;
            case 'Q' -> type = Type.QUEEN;
            case 'K' -> type = Type.KING;
            default -> throw new IllegalStateException("Unexpected value: " + valCode.charAt(0));
        }

        int val;
        switch (valCode.charAt(0)) {
            case 'A' -> val = 1;
            case '2', '3', '4', '5', '6', '7', '8', '9' -> val = valCode.charAt(0) - '0';
            case '1', 'J', 'Q', 'K' -> val = 10;
            default -> throw new IllegalStateException("Unexpected value: " + valCode.charAt(0));
        }

        return new Card(val, suit, type, code);
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        if (type != Type.NUMERIC) {
            return String.format("%s%c", suit.toString().charAt(0), type.toString().charAt(0));
        }
        return String.format("%c%d", suit.toString().charAt(0), value);
    }
}
