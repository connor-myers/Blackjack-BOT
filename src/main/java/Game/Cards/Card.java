package Game.Cards;

import Graphics.Sprite;

import static Game.Cards.Card.Suit.CLUBS;

public class Card {

    public enum Suit {
        SPADES, CLUBS, DIAMONDS, HEARTS
    }

    public enum Type {
        NUMERIC, ACE, JACK, QUEEN, KING
    }

    private final static double CARD_SCALE = 0.5;
    public final static Sprite back = new Sprite("src/main/resources/Images/Cards/blue_back.png", CARD_SCALE);

    private final int value;
    private final Sprite sprite;
    private final Suit suit;
    private final Type type;

    public Card(int value, Suit suit, Type type, String code) {
        this.value = value;
        this.suit = suit;
        this.type = type;
        this.sprite = new Sprite("src/main/resources/Images/Cards/" + code + ".png", CARD_SCALE);
    }

    public static Card decodeCardFromString(String code) {
        char suitCode = code.charAt(0);
        String valCode = code.substring(1);

        Suit suit;
        switch (suitCode) {
            case 'C':
                suit = CLUBS;
                break;
            case 'D':
                suit = Suit.DIAMONDS;
                break;
            case 'S':
                suit = Suit.SPADES;
                break;
            case 'H':
                suit = Suit.HEARTS;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + suitCode);
        }

        // 1 means 10 here, A means 1 (ace)
        Type type;
        switch (valCode.charAt(0)) {
            case 'A':
                type = Type.ACE;
                break;
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
            case '1':
                type = Type.NUMERIC;
                break;
            case 'J':
                type = Type.JACK;
                break;
            case 'Q':
                type = Type.QUEEN;
                break;
            case 'K':
                type = Type.KING;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + valCode.charAt(0));
        }

        int val;
        // changed from 1 to 11, hope this doesn't break the program
        switch (valCode.charAt(0)) {
            case 'A':
                val = 11;
                break;
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
                val = valCode.charAt(0) - '0';
                break;
            case '1':
            case 'J':
            case 'Q':
            case 'K':
                val = 10;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + valCode.charAt(0));
        }

        return new Card(val, suit, type, code);
    }

    public int getValue() {
        return value;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public Suit getSuit() {
        return suit;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        if (type != Type.NUMERIC) {
            return String.format("%s%c", suit.toString().charAt(0), type.toString().charAt(0));
        }
        return String.format("%c%d", suit.toString().charAt(0), value);
    }
}
