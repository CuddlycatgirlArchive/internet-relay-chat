package gay.sukumi.irc.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Map;

@SuppressWarnings("all")
public enum EnumChatFormatting
{
    BLACK("BLACK", 0, "BLACK", '0', 0),
    DARK_BLUE("DARK_BLUE", 1, "DARK_BLUE", '1', 1),
    DARK_GREEN("DARK_GREEN", 2, "DARK_GREEN", '2', 2),
    DARK_AQUA("DARK_AQUA", 3, "DARK_AQUA", '3', 3),
    DARK_RED("DARK_RED", 4, "DARK_RED", '4', 4),
    DARK_PURPLE("DARK_PURPLE", 5, "DARK_PURPLE", '5', 5),
    GOLD("GOLD", 6, "GOLD", '6', 6),
    GRAY("GRAY", 7, "GRAY", '7', 7),
    DARK_GRAY("DARK_GRAY", 8, "DARK_GRAY", '8', 8),
    BLUE("BLUE", 9, "BLUE", '9', 9),
    GREEN("GREEN", 10, "GREEN", 'a', 10),
    AQUA("AQUA", 11, "AQUA", 'b', 11),
    RED("RED", 12, "RED", 'c', 12),
    LIGHT_PURPLE("LIGHT_PURPLE", 13, "LIGHT_PURPLE", 'd', 13),
    YELLOW("YELLOW", 14, "YELLOW", 'e', 14),
    WHITE("WHITE", 15, "WHITE", 'f', 15),
    OBFUSCATED("OBFUSCATED", 16, "OBFUSCATED", 'k', true),
    BOLD("BOLD", 17, "BOLD", 'l', true),
    STRIKETHROUGH("STRIKETHROUGH", 18, "STRIKETHROUGH", 'm', true),
    UNDERLINE("UNDERLINE", 19, "UNDERLINE", 'n', true),
    ITALIC("ITALIC", 20, "ITALIC", 'o', true),
    RESET("RESET", 21, "RESET", 'r', -1);

    private static final Map<Object, Object> nameMapping;
    private static final Pattern formattingCodePattern;
    private final String controlString;

    private static String func_175745_c(final String p_175745_0_) {
        return p_175745_0_.toLowerCase().replaceAll("[^a-z]", "");
    }

    private EnumChatFormatting(final String name, final int id, final String id3, final char code, final int id2) {
        this(name, id, id3, code, false);
    }

    private EnumChatFormatting(final String name, final int id, final String type, final char code, final boolean ihavenoideatbh) {
        this(name, id, type, code, ihavenoideatbh, -1);
    }

    private EnumChatFormatting(final String p_i46293_1_, final int p_i46293_2_, final String p_i46293_3_, final char p_i46293_4_, final boolean p_i46293_5_, final int p_i46293_6_) {
        this.controlString = "\u00A7" + p_i46293_4_;
    }

    @Override
    public String toString() {
        return this.controlString;
    }

    public static String getTextWithoutFormattingCodes(final String p_110646_0_) {
        return (p_110646_0_ == null) ? null : EnumChatFormatting.formattingCodePattern.matcher(p_110646_0_).replaceAll("");
    }

    public static String getCleanText(String text) {
        Pattern unicodeCharsPattern = Pattern.compile("\\\\u(\\p{XDigit}{4})");
        Matcher unicodeMatcher = unicodeCharsPattern.matcher(text);
        String cleanData = null;
        if (unicodeMatcher.find()) {
            cleanData = unicodeMatcher.replaceAll("");
        }
        return cleanData == null ? text : cleanData;
    }

    static {
        nameMapping = new HashMap<>();
        formattingCodePattern = Pattern.compile("(?i)\u00A7[0-9A-FK-OR]");
    }
}
