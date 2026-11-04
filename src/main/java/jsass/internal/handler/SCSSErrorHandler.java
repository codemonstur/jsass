package jsass.internal.handler;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.w3c.css.sac.CSSException;
import org.w3c.css.sac.CSSParseException;
import org.w3c.css.sac.ErrorHandler;

public class SCSSErrorHandler implements ErrorHandler {

    private static ThreadLocal<SCSSErrorHandler> current =
            ThreadLocal.withInitial(SCSSErrorHandler::new);

    public static void set(SCSSErrorHandler h) {
        current.set(h);
    }

    public static SCSSErrorHandler get() {
        return current.get();
    }

    private boolean errorsDetected = false;
    private boolean warningsAreErrors = true;

    public SCSSErrorHandler() {
    }

    @Override
    public void error(CSSParseException e) throws CSSException {
        log("Error when parsing file \n" + e.getURI() + " on line "
                + e.getLineNumber() + ", column " + e.getColumnNumber());
        log(e);
        errorsDetected = true;
    }

    @Override
    public void fatalError(CSSParseException e) throws CSSException {
        log("FATAL Error when parsing file \n" + e.getURI() + " on line "
                + e.getLineNumber() + ", column " + e.getColumnNumber());
        log(e);
        errorsDetected = true;
    }

    @Override
    public void warning(CSSParseException e) throws CSSException {
        warn("Warning when parsing file \n" + e.getURI() + " on line "
                + e.getLineNumber() + ", column " + e.getColumnNumber());
        warn(e);
        if (warningsAreErrors) {
            errorsDetected = true;
        }
    }

    private void log(String msg) {
        Logger.getLogger(SCSSDocumentHandlerImpl.class.getName()).log(
                Level.SEVERE, msg);
    }

    private void log(Exception e) {
        Logger.getLogger(SCSSDocumentHandlerImpl.class.getName()).log(
                Level.SEVERE, e.getMessage(), e);
    }

    private void severe(String msg) {
        Logger.getLogger(SCSSDocumentHandlerImpl.class.getName()).log(
                Level.SEVERE, msg);
    }

    private void severe(String msg, Exception e) {
        Logger.getLogger(SCSSDocumentHandlerImpl.class.getName()).log(
                Level.SEVERE, msg, e);
    }

    private void warn(String msg) {
        Logger.getLogger(SCSSDocumentHandlerImpl.class.getName()).log(
                Level.WARNING, msg);
    }

    private void warn(Exception e) {
        Logger.getLogger(SCSSDocumentHandlerImpl.class.getName()).log(
                Level.WARNING, e.getMessage(), e);
    }

    public void traverseError(Exception e) {
        severe(null, e);
        errorsDetected = true;
    }

    public void traverseError(String message) {
        severe(message);
        errorsDetected = true;
    }

    public boolean isErrorsDetected() {
        return errorsDetected;
    }

    public void setWarningsAreErrors(boolean warningsAreErrors) {
        this.warningsAreErrors = warningsAreErrors;

    }
}
