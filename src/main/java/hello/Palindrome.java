package hello;

/**
 * Created by craigleclair on 2016-04-14.
 */
public class Palindrome {

    public boolean isPalindrome(String phrase){
        phrase = phrase.replaceAll("[^A-Za-z0-9 ]", "").replaceAll("\\s+","").toLowerCase();
        for(int i = 0; i < phrase.length(); i ++){
            if(phrase.charAt(i) != phrase.charAt(phrase.length() - 1 - i)){
                return false;
            }
        }
        return true;
    }
}
