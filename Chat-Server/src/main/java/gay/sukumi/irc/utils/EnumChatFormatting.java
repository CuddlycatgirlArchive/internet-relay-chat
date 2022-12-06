package gay.sukumi.irc.utils;

import java.util.regex.Pattern;

public class EnumChatFormatting {

    private static final Pattern formattingCodePattern = Pattern.compile("(?i)§[0-9A-FK-OR]");

    public static String getTextWithoutFormattingCodes(String text)
    {
        return text == null ? null : formattingCodePattern.matcher(text).replaceAll("");
    }

}
