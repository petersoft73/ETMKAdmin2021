package com.petersoft.mgl.ui.highlighter;
/**
 * If interested, check out HtmlHighlighterEx (provides same outcome but has string based operations),
 * this one is better in performance and has very simple code.
 */
public class HtmlHighlighter {
    private static final String HighLightTemplate = "<span style='background:yellow;'>$1</span>";

    public static String highlightText(String text, String textToHighlight) {
        if(textToHighlight.length()==0){
            return text;
        }
        text = text.replaceAll("(?i)(" + textToHighlight + ")", HighLightTemplate);
        return "<html>" + text + "</html>";
    }
}