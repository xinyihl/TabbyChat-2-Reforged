package mnm.mods.tabbychat.util;

import net.minecraft.client.resources.I18n;

import java.util.regex.Pattern;

public enum ChannelPatterns {

    ANGLES(Translation.DELIMS_ANGLES, "^\\<%s\\>"),
    BRACES(Translation.DELIMS_BRACES, "^\\{%s\\}"),
    BRACKETS(Translation.DELIMS_BRACKETS, "^\\[%s\\]"),
    PARENS(Translation.DELIMS_PARENTHESIS, "^\\(%s\\)"),
    INVERTEDCOMMA(Translation.DELIMS_INVERTEDCOMMA, "^\\u300e%s\\u300f"),
    ANGLESPARENS(Translation.DELIMS_ANGLES_PARENS, "^<\\(%s\\) ?.{3,30}>"),
    ANGLESBRACKETS(Translation.DELIMS_ANGLES_BRAKETS, "^<\\[%s\\] ?.{3,30}>");

    private final String translation;
    private final Pattern pattern;

    ChannelPatterns(String title, String pattern) {
        this.translation = title;
        this.pattern = Pattern.compile(String.format(pattern, "(.{1,16}?)"));
    }

    public Pattern getPattern() {
        return pattern;
    }

    @Override
    public String toString() {
        return I18n.format(translation);
    }
}
