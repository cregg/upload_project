package hello;

import java.util.Collections;
import java.util.Stack;

/**
 * Created by craigleclair on 2016-04-14.
 */
public class CardDeck {

    public Stack<Card> deck;

    public CardDeck(){
        deck = new Stack<>();
        for(int i = 1; i < 14; i++){
            deck.push(new Card(i, Suit.CLUB));
            deck.push(new Card(i, Suit.HEART));
            deck.push(new Card(i, Suit.DIAMOND));
            deck.push(new Card(i, Suit.SPADE));
        }
    }

    public Stack<Card> getDeck() {
        return deck;
    }

    public void setDeck(Stack<Card> deck) {
        this.deck = deck;
    }

}
