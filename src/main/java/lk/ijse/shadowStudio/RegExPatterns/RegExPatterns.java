package lk.ijse.shadowStudio.RegExPatterns;

import lombok.Getter;

import java.util.regex.Pattern;

public class RegExPatterns {
    @Getter
    public static final Pattern validName = Pattern.compile("\\b[A-Z][a-z]*( [A-Z][a-z]*)*\\b");
    @Getter
    public static final Pattern validPassword = Pattern.compile("(.*?[0-9]){4,}");
    @Getter
    public static final Pattern validPhoneNumber = Pattern.compile("07\\d{8}");
    @Getter
    public static final Pattern validNic = Pattern.compile("^\\d{10,12}|\\d+[V]");
    @Getter
    public static final Pattern validText = Pattern.compile("^[a-zA-Z0-9 ,.]+$");
    @Getter
    public static final Pattern validPrice = Pattern.compile("\\d{2,}");
    @Getter
    public static final Pattern validCount = Pattern.compile("\\d{1,}");

}
