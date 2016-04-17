package hello;

/**
 * Created by craigleclair on 2016-04-14.
 */
public class Card {

    private int value;
    private Suit suit;

    public Card(int value, Suit suit){
        this.value = value;
        this.suit = suit;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Suit getSuit() {
        return suit;
    }

    public void setSuit(Suit suit) {
        this.suit = suit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Card card = (Card) o;

        if (value != card.value) return false;
        return suit == card.suit;

    }

    @Override
    public int hashCode() {
        int result = value;
        result = 31 * result + (suit != null ? suit.hashCode() : 0);
        return result;
    }
}
