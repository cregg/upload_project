package hello;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.PrintStream;

/**
 * Created by craigleclair on 2016-04-14.
 */
public class FizzBuzzTest {

    private FizzBuzz fizzBuzz = new FizzBuzz();
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void fizzBuzzTestTo3(){
        fizzBuzz.buzz(3);
        Assert.assertEquals("1\n2\nfizz\n", outContent.toString());
    }

    @Test
    public void fizzBuzzTestTo5(){
        fizzBuzz.buzz(5);
        Assert.assertEquals("1\n2\nfizz\n4\nbuzz\n", outContent.toString());
    }

    @Test
    public void fizzBuzzTestTo17(){
        fizzBuzz.buzz(17);
        Assert.assertEquals("1\n2\nfizz\n4\nbuzz\nfizz\n7\n8\nfizz\nbuzz\n11\nfizz\n13\n14\nfizzbuzz\n16\n17\n",
                outContent.toString());
    }

}
