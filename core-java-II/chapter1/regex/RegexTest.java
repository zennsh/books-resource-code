package chapter1.Regex;

import com.sun.org.apache.xpath.internal.SourceTree;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This program tests regular expression matching.
 * Enter a pattern and strings to match, or hit Cancel
 * to exit. If the pattern contains groups, the group
 * boundaries are displayed in the match.
 * <p>
 * Created by ZHEN on 4/29/2016.
 */
public class RegexTest {
    static final String path = "./target/production/books-resource-code/resource/";

    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter Patten:");
        String pattenStr = in.nextLine();
        System.out.println(pattenStr);

        Pattern pattern = Pattern.compile(pattenStr);

//        in = new Scanner(new FileInputStream(path + "RegexTest.txt"), "utf-8");
        while (true) {
            System.out.println("Enter string to match:");
            String input = in.nextLine();
            System.out.println(input);
            if (input == null || input.isEmpty()) return;
            Matcher matcher = pattern.matcher(input);
            if (matcher.matches()) {
                System.out.println("Match");
                int g = matcher.groupCount();
                if (g > 0) {
                    for (int i = 0; i < input.length(); i++) {
                        for (int j = 1; j <= g; j++) {
                            if (i == matcher.start(j) && i == matcher.end(j)) {
                                System.out.print("()");
                            }
                        }
                        for (int j = 1; j <= g; j++) {
                            if (i == matcher.start(j) && i != matcher.end(j)) {
                                System.out.print("(");
                            }
                        }
                        System.out.print(input.charAt(i));
                        for (int j = 1; j <= g; j++) {
                            if (i + 1 != matcher.start(j) && i + 1 == matcher.end(j)) {
                                System.out.print(")");
                            }
                        }
                    }
                    System.out.println();
                }
            } else {
                System.out.println("No Match");
            }
        }
    }
}
