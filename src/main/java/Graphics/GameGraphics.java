package Graphics;

import Game.Cards.Card;
import Game.Game;

import java.util.List;

public class GameGraphics extends Scene {
    public GameGraphics(Game game) {
        super();
        this.drawHand(game.getPlayer().getHand());
        this.drawDealerHand(game.getDealer().getHand(), game.getNumTurns(), game.isGameOver());
        this.saveImage("image");
    }

    private void drawHand(List<Card> hand) {
        for (int i = 0; i < hand.size(); i++) {
            Card card = hand.get(i);
//            this.addSprite(card.getSprite(),
//                    (int) Math.floor((double) this.getWidth() / 4 + i * 1.25 * card.getSprite().getImage().getWidth()),
//                    (int) Math.floor(0.9 * this.getHeight() - card.getSprite().getImage().getHeight()));
            this.addSprite(card.getSprite(), calculateX(card.getSprite(),
                    i,
                    hand.size()), (int) Math.floor(0.9 * this.getHeight() - card.getSprite().getImage().getHeight()));
        }
    }

    private void drawDealerHand(List<Card> hand, int turnNumber, boolean gameOver) {
        for (int i = 0; i < hand.size(); i++) {
            Card card = hand.get(i);
            Sprite sprite = card.getSprite();
            if (turnNumber < 3 && i == 0) {
                sprite = Card.back;
            }
            if (turnNumber < 5 && i == 1 && !gameOver) {
                sprite = Card.back;
            }

//            this.addSprite(sprite,
//                    (int) Math.floor((double) this.getWidth() / 4 + i * 1.25 * sprite.getImage().getWidth()),
//                    (int) Math.floor(0.05 * this.getHeight()));
            this.addSprite(sprite,
                    calculateX(card.getSprite(), i, hand.size()),
                    (int) Math.floor(0.05 * this.getHeight()));
        }
    }

    private int calculateX(Sprite card, int i, int numCards) {
        int midPoint = this.getWidth() / 2;
        double half = 0.5 * card.getImage().getWidth();
        return (int) Math.floor(midPoint - (numCards * half) + (2.1 * i * half));
    }
}