import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator {
    public List<String> findValidEmails(String input) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);

        String[] words = input.split("\\s+");

        List<String> validEmails = new ArrayList<>();

        for (String word : words) {
            if (word.contains("@")) {
                Matcher matcher = pattern.matcher(word);
                if (matcher.matches()) {
                    validEmails.add(word);
                }
            }
        }

        return validEmails;
    }

    public static String swapDomainLevels(String url) {
        String regex = "(https?://)([a-zA-Z0-9]+)\\.([a-zA-Z0-9]+)\\.([a-zA-Z0-9]+)\\.([*]+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(url);

        if (matcher.find()) {
            return matcher.group(1) + matcher.group(4) + "." + matcher.group(3) + "." + matcher.group(2) + "." + matcher.group(5);
        }

        return url;
    }
}