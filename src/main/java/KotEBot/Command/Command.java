package KotEBot.Command;

import java.util.Arrays;
import java.util.List;

public interface Command {
    void handle(CommandContext ctx);
    String getName();
    String getHelp();

    default List<String> getAliases() {
        return Arrays.asList();
    }
}
