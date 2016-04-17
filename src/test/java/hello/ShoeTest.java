package hello;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by craigleclair on 2016-04-14.
 */
public class ShoeTest {

    private Shoe shoe;

    @Test
    public void oneDeckShoe(){
        shoe = new Shoe(1);
        Assert.assertEquals(52, shoe.getCards().size());
    }

    @Test
    public void twoDeckShoe(){
        shoe = new Shoe(2);
        Assert.assertEquals(2*52, shoe.getCards().size());
    }


    /**
     * This test is designed to test if a shoe is shuffled properly. The test is imperfect as the randomness
     * of shuffling cards can allow for decks to remain the same even after shuffling.
     */
    @Test
    public void oneDeckShoeShuffle(){
        shoe = new Shoe(1);
        Assert.assertEquals(52, shoe.getCards().size());
        Stack<Card> originalCards = shoe.getCards();
        shoe.shuffleCards();
        Assert.assertFalse(originalCards.equals(shoe.getCards()));
    }

    @Test
    public void sixDeckShoeShuffle(){
        shoe = new Shoe(6);
        Assert.assertEquals(6*52, shoe.getCards().size());
        Stack<Card> originalCards = shoe.getCards();
        shoe.shuffleCards();
        Assert.assertFalse(originalCards.equals(shoe.getCards()));
    }
}


