package hello;

import java.util.*;

/**
 * Created by craigleclair on 2016-04-14.
 */
public class Shoe {

    /**
     * If the only purpose of the shoe is to shuffle, then representing the cards as a simple array is probably
     * a better idea. I'll assume that there will be
     */
    public Stack<Card> cards;

    public Shoe(int numOfDecks){
        cards = new Stack<>();
        for(int i = 0; i < numOfDecks; i++){
            cards.addAll(new CardDeck().getDeck());
        }
    }

    public Stack<Card> getCards() {
        return cards;
    }

    public void setCards(Stack<Card> cards) {
        this.cards = cards;
    }

    public Stack<Card> shuffleCards(){
        // For simplicity you can use Collections.shuffle()
        Card[] arrayOfCards = cards.toArray(new Card[cards.size()]);
        for(int i = 0; i < arrayOfCards.length; i++){
            int randomNum = new Random().nextInt(arrayOfCards.length);
            Card tmpCard = arrayOfCards[i];
            arrayOfCards[i] = arrayOfCards[randomNum];
            arrayOfCards[randomNum] = tmpCard;
        }
        cards = new Stack<>();
        cards.addAll(Arrays.asList(arrayOfCards));
        return cards;
    }
}
