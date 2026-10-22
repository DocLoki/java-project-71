package hexlet.code;

import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(
        name = "gendiff",
        version = "gendiff 1.0",
        description = "Compares two configuration files and shows a difference.",
        mixinStandardHelpOptions = true
)
public class App implements Runnable {

    @Override
    public void run() {
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }
}