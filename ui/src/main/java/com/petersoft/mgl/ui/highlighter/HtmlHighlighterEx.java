package com.petersoft.mgl.ui.highlighter;

@Deprecated
/**
 * We used this utility first but later used regex instead of string operations which
 * improved performance. See the new class HtmlHighlighter.
 */
public class HtmlHighlighterEx {
    private static final String HighLightTemplate = "%<span style='background:yellow;'>%s</span>%s";
    private static final int ExtraLength = HighLightTemplate.length() - 6;

    public static String highlightText(String text, String textToHighlight) {
        textToHighlight = textToHighlight.toLowerCase().trim();
        if (textToHighlight.length() == 0) {
            return text;
        }
        final int matchLen = textToHighlight.length();
        final String lText = text.toLowerCase();
        int i = lText.indexOf(textToHighlight);
        int matchCount = 0;
        while (i != -1) {
            int mappedI = matchCount * ExtraLength + i;
            text = String.format(HighLightTemplate,
                    text.substring(0, mappedI),//start
                    text.substring(mappedI, mappedI + matchLen),//middle
                    text.substring(mappedI + matchLen)//end
            );
            i = lText.indexOf(textToHighlight, i + matchLen);
            matchCount++;
        }

        return "<html>" + text + "</html>";
    }
}