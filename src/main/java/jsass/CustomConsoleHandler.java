package jsass;

import java.io.PrintStream;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

/**
 * 
 * @author jsass Ltd
 */
public class CustomConsoleHandler extends ConsoleHandler {

    private ConsoleHandler stdoutHandler;

    public CustomConsoleHandler() {
        PrintStream err = System.err;
        /*
         * ConsoleHandler uses System.err to output all messages. Replace
         * System.err temporary to construct ConsoleHandler and set it back
         * after construction.
         */
        System.setErr(System.out);
        stdoutHandler = new ConsoleHandler();
        System.setErr(err);
    }

    @Override
    public void publish(LogRecord record) {
        if (!Level.SEVERE.equals(record.getLevel())) {
            stdoutHandler.publish(record);
        } else {
            super.publish(record);
        }
    }
}
