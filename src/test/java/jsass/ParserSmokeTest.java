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
        SCSSErrorHandler.set(errorHandler);
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

    @Test
    public void keyframes() throws Exception {
        assertNotNull(compile("@keyframes slide { from { left: 0; } to { left: 100px; } }"));
    }

    @Test
    public void fontFace() throws Exception {
        assertNotNull(compile("@font-face { font-family: x; src: url(x.woff); }"));
    }

    @Test
    public void charset() throws Exception {
        assertNotNull(compile("@charset \"UTF-8\"; .a { color: red; }"));
    }

    @Test
    public void placeholder() throws Exception {
        assertNotNull(compile("%ph { color: red; } .a { @extend %ph; }"));
    }

    @Test
    public void parentSelector() throws Exception {
        assertNotNull(compile(".a { &:hover { color: red; } }"));
    }

    @Test
    public void nestedProps() throws Exception {
        assertNotNull(compile(".a { font: { size: 12px; weight: bold; } }"));
    }

    @Test
    public void operators() throws Exception {
        assertNotNull(compile(".a { width: 10px + 5px; height: 20px - 3px; margin: 2px * 3; padding: 10px / 2; }"));
    }

    @Test
    public void interpolation() throws Exception {
        assertNotNull(compile("$name: foo; .#{$name} { color: red; }"));
    }

    @Test
    public void lists() throws Exception {
        assertNotNull(compile("$l: 1, 2, 3; .a { z-index: nth($l, 2); }"));
    }

    @Test
    public void maps() throws Exception {
        assertNotNull(compile("$m: (a: 1, b: 2); .a { width: 1px; }"));
    }

    @Test
    public void colorFuncs() throws Exception {
        assertNotNull(compile(".a { color: lighten(red, 10%); background: darken(blue, 5%); }"));
    }

    @Test
    public void stringFuncs() throws Exception {
        assertNotNull(compile(".a { content: quote(hello); }"));
    }

    @Test
    public void atRoot() throws Exception {
        assertNotNull(compile(".a { @at-root .b { color: red; } }"));
    }

    @Test
    public void supports() throws Exception {
        assertNotNull(compile("@supports (display: grid) { .a { display: grid; } }"));
    }

    @Test
    public void document() throws Exception {
        assertNotNull(compile("@document url(http://example.com) { .a { color: red; } }"));
    }

    @Test
    public void page() throws Exception {
        assertNotNull(compile("@page { margin: 1cm; }"));
    }

    @Test
    public void viewport() throws Exception {
        assertNotNull(compile("@viewport { width: 100vw; }"));
    }
}