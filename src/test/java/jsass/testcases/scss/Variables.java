package jsass.testcases.scss;

import java.io.IOException;

import jsass.internal.parser.LexicalUnitImpl;
import junit.framework.Assert;

import org.junit.Test;
import org.w3c.css.sac.CSSException;

import jsass.AbstractTestBase;
import jsass.internal.ScssStylesheet;
import jsass.internal.handler.SCSSDocumentHandler;
import jsass.internal.handler.SCSSDocumentHandlerImpl;
import jsass.internal.parser.Parser;
import jsass.internal.parser.SCSSLexicalUnit;
import jsass.internal.tree.BlockNode;
import jsass.internal.tree.RuleNode;
import jsass.internal.tree.VariableNode;

public class Variables extends AbstractTestBase {

    String scss = "/scss/variables.scss";
    String css = "/css/variables.css";

    @Test
    public void testParser() throws CSSException, IOException {
        Parser parser = new Parser();
        SCSSDocumentHandler handler = new SCSSDocumentHandlerImpl();
        parser.setDocumentHandler(handler);
        parser.parseStyleSheet(getClass().getResource(scss).getPath());
        ScssStylesheet root = handler.getStyleSheet();
        Assert.assertEquals(8, root.getChildren().size());

        VariableNode varNode1 = (VariableNode) root.getChildren().get(0);
        Assert.assertEquals("blue", varNode1.getName());

        VariableNode varNode3 = (VariableNode) root.getChildren().get(2);
        Assert.assertEquals("chameleon-font-family", varNode3.getName());

        VariableNode varNode2 = (VariableNode) root.getChildren().get(1);
        Assert.assertEquals("margin", varNode2.getName());
        Assert.assertEquals(8f, varNode2.getExpr().getContainedValue()
                .getFloatValue());
        Assert.assertEquals("px", varNode2.getExpr().getContainedValue()
                .getDimensionUnitText());

        BlockNode blockNode1 = (BlockNode) root.getChildren().get(6);
        Assert.assertEquals(3, blockNode1.getChildren().size());
        RuleNode ruleNode1Block1 = (RuleNode) blockNode1.getChildren().get(2);
        Assert.assertEquals("border-color", ruleNode1Block1.getVariable()
                .toString());
        Assert.assertEquals(SCSSLexicalUnit.SCSS_VARIABLE, ruleNode1Block1
                .getValue().getContainedValue().getLexicalUnitType());
        Assert.assertEquals("blue", ruleNode1Block1.getValue()
                .getContainedValue().getStringValue());

        RuleNode ruleNode2Block1 = (RuleNode) blockNode1.getChildren().get(2);
        Assert.assertEquals("border-color", ruleNode2Block1.getVariable()
                .toString());
        Assert.assertEquals(SCSSLexicalUnit.SCSS_VARIABLE, ruleNode2Block1
                .getValue().getContainedValue().getLexicalUnitType());
        Assert.assertEquals("blue", ruleNode2Block1.getValue()
                .getContainedValue().getStringValue());

        BlockNode blockNode2 = (BlockNode) root.getChildren().get(6);
        RuleNode ruleNode1Block2 = (RuleNode) blockNode2.getChildren().get(0);
        Assert.assertEquals("padding", ruleNode1Block2.getVariable().toString());
        Assert.assertEquals(SCSSLexicalUnit.SCSS_VARIABLE, ruleNode1Block2
                .getValue().getContainedValue().getLexicalUnitType());
        Assert.assertEquals("margin", ruleNode1Block2.getValue()
                .getContainedValue().getStringValue());

        RuleNode ruleNode2Block2 = (RuleNode) blockNode2.getChildren().get(1);
        Assert.assertEquals("margin", ruleNode2Block2.getVariable().toString());
        Assert.assertEquals(SCSSLexicalUnit.SCSS_VARIABLE, ruleNode2Block2
                .getValue().getContainedValue().getLexicalUnitType());
        Assert.assertEquals("margin", ruleNode2Block2.getValue()
                .getContainedValue().getStringValue());

        RuleNode ruleNode3Block2 = (RuleNode) blockNode2.getChildren().get(2);
        Assert.assertEquals("border-color", ruleNode3Block2.getVariable()
                .toString());
        Assert.assertEquals(SCSSLexicalUnit.SCSS_VARIABLE, ruleNode1Block2
                .getValue().getContainedValue().getLexicalUnitType());
        Assert.assertEquals("blue", ruleNode3Block2.getValue()
                .getContainedValue().getStringValue());

        BlockNode blockNode3 = (BlockNode) root.getChildren().get(7);
        RuleNode ruleNode1Block3 = (RuleNode) blockNode3.getChildren().get(0);
        Assert.assertEquals("background-image", ruleNode1Block3.getVariable().toString());
        Assert.assertEquals(SCSSLexicalUnit.SAC_FUNCTION, ruleNode1Block3
                .getValue().getContainedValue().getLexicalUnitType());
        Assert.assertEquals("url", ((LexicalUnitImpl)
                ruleNode1Block3.getValue()).getFunctionName());
    }

    @Test
    public void testCompiler() throws Exception {
        testCompiler(scss, css);
    }
}
