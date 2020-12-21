package Graphics;

import Game.Cards.Card;
import Game.Game;

import java.util.List;

public class GameGraphics extends Scene {
    public GameGraphics(Game game) {
        super();
        this.drawHand(game.getPlayer().getHand(), 0);
    }

    private void drawHand(List<Card> hand, int y) {
        for (int i = 0; i < hand.size(); i++) {
            Card card = hand.get(i);
            this.addSprite(card.getSprite(),
                    (int) Math.floor((double) this.getWidth() / 2 + i * 1.25 * card.getSprite().getImage().getWidth()),
                    y,
                    0.5);
        }
    }
}