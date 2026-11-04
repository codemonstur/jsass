package jsass;

import jsass.internal.ScssStylesheet;
import jsass.internal.handler.SCSSDocumentHandlerImpl;
import jsass.internal.handler.SCSSErrorHandler;

import org.junit.Test;

import java.io.StringReader;

import static org.junit.Assert.assertNotNull;

public class ParserSmokeTest {

    private static String compile(final String scss) throws Exception {
        final var handler = new SCSSDocumentHandlerImpl();
        final var errorHandler = new SCSSErrorHandler();
        final var parser = new jsass.internal.parser.Parser();
        parser.setErrorHandler(errorHandler);
        parser.setDocumentHandler(handler);
        parser.parseStyleSheet(new org.w3c.css.sac.InputSource(new StringReader(scss)));
        final var sheet = handler.getStyleSheet();
        sheet.addResolver(new jsass.internal.resolver.FilesystemResolver());
        sheet.addResolver(new jsass.internal.resolver.ClassloaderResolver());
        sheet.setCharset("ASCII");
        sheet.compile();
        return sheet.toString();
    }

    @Test
    public void empty() throws Exception {
        assertNotNull(compile(""));
    }

    @Test
    public void variables() throws Exception {
        assertNotNull(compile("$a: 1; $b: 2; .x { width: $a + $b; }"));
    }

    @Test
    public void nesting() throws Exception {
        assertNotNull(compile(".a { .b { color: red; } &:hover { color: blue; } }"));
    }

    @Test
    public void mixin() throws Exception {
        assertNotNull(compile("@mixin m($x) { width: $x; } .a { @include m(10px); }"));
    }

    @Test
    public void function() throws Exception {
        assertNotNull(compile("@function f($x) { @return $x * 2; } .a { width: f(5px); }"));
    }

    @Test
    public void ifElse() throws Exception {
        assertNotNull(compile(".a { @if true { color: red; } @else { color: blue; } }"));
    }

    @Test
    public void forLoop() throws Exception {
        assertNotNull(compile(".a { @for $i from 1 through 3 { .x-#{$i} { width: 10px; } } }"));
    }

    @Test
    public void whileLoop() throws Exception {
        assertNotNull(compile("$i: 1; .a { @while $i < 3 { .x-#{$i} { width: 10px; } $i: $i + 1; } }"));
    }

    @Test
    public void each() throws Exception {
        assertNotNull(compile(".a { @each $c in red, blue { .#{$c} { color: $c; } } }"));
    }

    @Test
    public void extend() throws Exception {
        assertNotNull(compile(".a { color: red; } .b { @extend .a; }"));
    }

    @Test
    public void media() throws Exception {
        assertNotNull(compile("@media screen { .a { color: red; } }"));
    }
}