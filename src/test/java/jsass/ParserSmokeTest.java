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

    @Test
    public void variablesDefault() throws Exception {
        assertNotNull(compile("$x: 1 !default; .a { width: $x * 1px; }"));
    }

    @Test
    public void mixinDefaults() throws Exception {
        assertNotNull(compile("@mixin m($x: 10px) { width: $x; } .a { @include m; } .b { @include m(20px); }"));
    }

    @Test
    public void functionConditional() throws Exception {
        assertNotNull(compile("@function f($x) { @if $x > 0 { @return $x; } @return 0; } .a { width: f(5px); }"));
    }

    @Test
    public void nestedMedia() throws Exception {
        assertNotNull(compile(".a { @media screen { color: red; } }"));
    }

    @Test
    public void contentBlock() throws Exception {
        assertNotNull(compile("@mixin m { @content; } .a { @include m { color: red; } }"));
    }

    @Test
    public void ternary() throws Exception {
        assertNotNull(compile(".a { width: if(true, 10px, 20px); }"));
    }

    @Test
    public void math() throws Exception {
        assertNotNull(compile(".a { width: (10px + 5px) * 2; height: 100% / 3; }"));
    }

    @Test
    public void strings() throws Exception {
        assertNotNull(compile(".a { content: \"hello\" + \" world\"; }"));
    }

    @Test
    public void colors() throws Exception {
        assertNotNull(compile(".a { color: rgb(255, 0, 0); background: rgba(0, 0, 0, 0.5); }"));
    }

    @Test
    public void units() throws Exception {
        assertNotNull(compile(".a { width: 100%; height: 1em; margin: 1rem; }"));
    }

    @Test
    public void selectors() throws Exception {
        assertNotNull(compile(".a, .b { color: red; } .a > .b { color: blue; } .a + .b { color: green; } .a ~ .b { color: yellow; }"));
    }

    @Test
    public void pseudo() throws Exception {
        assertNotNull(compile(".a { &:nth-child(2n) { color: red; } &:not(.b) { color: blue; } }"));
    }

    @Test
    public void attr() throws Exception {
        assertNotNull(compile(".a { &[type=text] { color: red; } }"));
    }

    @Test
    public void comments() throws Exception {
        assertNotNull(compile("/* block */ .a { // line\n color: red; }"));
    }

    @Test
    public void variablesGlobal() throws Exception {
        assertNotNull(compile("$x: 1 !global; .a { width: $x * 1px; }"));
    }

    @Test
    public void defaultArgs() throws Exception {
        assertNotNull(compile("@mixin m($a: 1, $b: 2) { width: $a + $b; } .x { @include m; }"));
    }

    @Test
    public void restArgs() throws Exception {
        assertNotNull(compile("@mixin m($args...) { } .x { @include m(1, 2, 3); }"));
    }

    @Test
    public void keywordArgs() throws Exception {
        assertNotNull(compile("@mixin m($x: 1) { width: $x; } .x { @include m($x: 5px); }"));
    }

    @Test
    public void nestedImport() throws Exception {
        assertNotNull(compile(".a { @import \"x\"; }"));
    }

    @Test
    public void cssImport() throws Exception {
        assertNotNull(compile("@import url(\"x.css\");"));
    }

    @Test
    public void mediaNested() throws Exception {
        assertNotNull(compile(".a { @media (min-width: 10px) { color: red; } }"));
    }

    @Test
    public void supportsNested() throws Exception {
        assertNotNull(compile(".a { @supports (color: red) { color: red; } }"));
    }

    @Test
    public void fontFaceNested() throws Exception {
        assertNotNull(compile(".a { @font-face { font-family: x; } }"));
    }

    @Test
    public void keyframesNested() throws Exception {
        assertNotNull(compile(".a { @keyframes x { 0% { opacity: 0; } 100% { opacity: 1; } } }"));
    }

    @Test
    public void pageNested() throws Exception {
        assertNotNull(compile(".a { @page { margin: 1cm; } }"));
    }

    @Test
    public void documentNested() throws Exception {
        assertNotNull(compile(".a { @document url(x) { color: red; } }"));
    }

    @Test
    public void viewportNested() throws Exception {
        assertNotNull(compile(".a { @viewport { width: 100vw; } }"));
    }

    @Test
    public void charsetNested() throws Exception {
        assertNotNull(compile(".a { @charset \"UTF-8\"; }"));
    }

    @Test
    public void debug() throws Exception {
        assertNotNull(compile(".a { @debug 1 + 2; }"));
    }

    @Test
    public void warn() throws Exception {
        assertNotNull(compile(".a { @warn \"x\"; }"));
    }

    @Test
    public void error() throws Exception {
        assertNotNull(compile(".a { @error \"x\"; }"));
    }

    @Test
    public void returnStmt() throws Exception {
        assertNotNull(compile("@function f($x) { @return $x; } .a { width: f(1px); }"));
    }

    @Test
    public void ifNested() throws Exception {
        assertNotNull(compile(".a { @if true { color: red; } @else if false { color: blue; } @else { color: green; } }"));
    }

    @Test
    public void forNested() throws Exception {
        assertNotNull(compile(".a { @for $i from 1 to 3 { .x-#{$i} { width: 10px; } } }"));
    }

    @Test
    public void whileNested() throws Exception {
        assertNotNull(compile("$i: 1; .a { @while $i < 3 { .x-#{$i} { width: 10px; } $i: $i + 1; } }"));
    }

    @Test
    public void eachNested() throws Exception {
        assertNotNull(compile(".a { @each $k, $v in (a: 1, b: 2) { .#{$k} { width: $v * 1px; } } }"));
    }

    @Test
    public void extendOptional() throws Exception {
        assertNotNull(compile(".a { color: red; } .b { @extend .a !optional; }"));
    }

}