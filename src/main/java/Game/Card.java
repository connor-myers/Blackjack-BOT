package Game;

import Graphics.Sprite;

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

    @Override
    public String toString() {
        if (type != Type.NUMERIC) {
            return String.format("%s of %s", type.toString(), suit.toString());
        }
        return String.format("%d of %s", value, suit.toString());
    }
}
