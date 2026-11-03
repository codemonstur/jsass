package jsass.internal.parser.function;

import jsass.internal.ScssContext;
import jsass.internal.parser.LexicalUnitImpl;
import jsass.internal.parser.SassListItem;

/**
 * Generator class is used to handle SCSS functions. Generator is applied to the
 * function lexical unit if its method {@link #getFunctionName()} returns name
 * of the function.
 * 
 * If there are no dedicated generator for the function then default generator
 * is used.
 * 
 * @author jsass Ltd
 */
public interface SCSSFunctionGenerator {

    /**
     * Returns function names handled by this generator. Default generator
     * returns <code>null</code> and is used if there is no dedicated generator
     * for given function.
     * 
     * @return
     */
    String[] getFunctionNames();

    /**
     * Computes the value of the function. The parameters should be evaluated
     * before this method is called.
     * 
     * Both the input and the output of the method should be separate from any
     * chain of lexical units.
     * 
     * @param context
     *            current compilation context
     * @param function
     *            Function lexical unit to print its state
     * @return SassListItem the value of the function
     */
    SassListItem compute(ScssContext context, LexicalUnitImpl function);
}
