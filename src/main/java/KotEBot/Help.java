package KotEBot;

public class Help {
    private static String[] helpText = {
            "help",
            "leave",
            "join",
            "pause",
            "play",
            "queue",
            "remove",
            "skip",
            "stop"
    };

    public static String getHelp(int index) {
        if (index == -1) {
            return String.join("\n\n", helpText);
        } else {
            return helpText[index];
        }
    }
}
