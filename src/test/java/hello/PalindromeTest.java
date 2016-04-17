package hello;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by craigleclair on 2016-04-14.
 */
public class PalindromeTest extends AbstractTest {

    Palindrome palindrome = new Palindrome();

    @Test
    public void checkPalindrome1(){
        Assert.assertFalse(palindrome.isPalindrome("test"));
    }

    @Test
    public void checkPalindrome2(){
        Assert.assertFalse(palindrome.isPalindrome("testtt"));
    }

    @Test
    public void checkPalindrome3(){
        Assert.assertTrue(palindrome.isPalindrome("otto"));
    }

    @Test
    public void checkPalindrome4(){
        Assert.assertTrue(palindrome.isPalindrome("oto"));
    }

    @Test
    public void checkPanama(){
        Assert.assertTrue(palindrome.isPalindrome("A man, a plan, a canal; Panama!"));
    }
}
