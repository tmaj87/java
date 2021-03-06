package it.justDo.chat.common;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.StyleConstants;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import java.io.IOException;

public class HTMLJTextPane extends JTextPane {

    private final Log4j log4j = new Log4j(this);

    public HTMLJTextPane() {
        super();
        this.setContentType("text/html");
        reset();
    }

    public void append(String string) {
        HTMLDocument htmlDocument = (HTMLDocument) this.getStyledDocument();
        try {
            htmlDocument.insertBeforeEnd(findTag(htmlDocument, HTML.Tag.BODY), string);
        } catch (BadLocationException | IOException e) {
            log4j.INFO(e.getMessage());
        }
    }

    private void reset() {
        HTMLDocument htmlDocument = (HTMLDocument) this.getStyledDocument();
        try {
            htmlDocument.setInnerHTML(findTag(htmlDocument, HTML.Tag.BODY), "<i></i>");
        } catch (BadLocationException | IOException e) {
            log4j.INFO(e.getMessage());
        }
    }

    private Element findTag(HTMLDocument htmlDocument, HTML.Tag tag) {
        return htmlDocument.getElement(htmlDocument.getDefaultRootElement(), StyleConstants.NameAttribute, tag);
    }
}
