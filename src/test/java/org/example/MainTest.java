package org.example;

import junit.framework.TestCase;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Test for the CityBuilder class that verifies "Hello World!" is printed
 */
public class MainTest extends TestCase {

    public void testMainPrintsHelloWorld() {
        // Redirect System.out to capture printed output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        try {
            // Call the main method
            CityBuilder.main(new String[]{});

            // Verify the output contains "Hello World!"
            String output = outContent.toString().trim();
            assertTrue(output.startsWith("Hello World!"));
        } finally {
            // Restore original System.out
            System.setOut(originalOut);
        }
    }
}
