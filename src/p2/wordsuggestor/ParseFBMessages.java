package p2.wordsuggestor;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cse332.interfaces.worklists.LIFOWorkList;
import datastructures.worklists.ArrayStack;

public final class ParseFBMessages {
    private ParseFBMessages() {
        /* should not be instantiated */ }

    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.out.println("USAGE: ParseFBMessages <Your FB Name> <Your FB Archive>");
            System.exit(1);
        }

        String name = args[0];
        String archive = args[1];

        LIFOWorkList<String> messages = new ArrayStack<String>();
        Document doc = Jsoup
                .parse(new File(archive + File.separator + "html/messages.htm"), "UTF-8");
        Elements messagesElements = doc.getElementsByTag("p");

        for (Element content : messagesElements) {
            if (content.previousElementSibling().getElementsByClass("user").text()
                    .equals(name)) {
                messages.add(content.text());
            }
        }

        PrintWriter out = new PrintWriter("me.txt", "UTF-8");

        while (messages.hasWork()) {
            out.println(messages.next());
        }

        out.close();
    }
}
