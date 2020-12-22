package Graphics;

import Game.Cards.Card;
import Game.Game;

import java.util.List;

public class GameGraphics extends Scene {
    public GameGraphics(Game game) {
        super();
        this.drawHand(game.getPlayer().getHand());
        this.drawDealerHand(game.getDealer().getHand(), game.getNumTurns(), game.isGameOver(), game.getPlayer().isSitting());
        this.saveImage("image");
    }

    private void drawHand(List<Card> hand) {
        for (int i = 0; i < hand.size(); i++) {
            Card card = hand.get(i);
            this.addSprite(card.getSprite(), calculateX(card.getSprite(),
                    i,
                    hand.size()), (int) Math.floor(0.95 * this.getHeight() - card.getSprite().getImage().getHeight()));
        }
    }

    private void drawDealerHand(List<Card> hand, int turnNumber, boolean gameOver, boolean playerSitting) {
        for (int i = 0; i < hand.size(); i++) {
            Card card = hand.get(i);
            Sprite sprite = card.getSprite();
            if (turnNumber < 2 && i == 0) {
                sprite = Card.back;
            }
            if (i != 0 && !playerSitting) {
                sprite = Card.back;
            }

            this.addSprite(sprite,
                    calculateX(card.getSprite(), i, hand.size()),
                    (int) Math.floor(0.05 * this.getHeight()));
        }
    }

    private int calculateX(Sprite card, int i, int numCards) {
        int midPoint = this.getWidth() / 2;
        double half = 0.5 * card.getImage().getWidth();
        double separation = numCards > 5 ? 1.25 : 2;
        return (int) Math.floor(midPoint - (numCards * half) + (separation * i * half));
    }
}