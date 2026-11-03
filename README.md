# jsass

A pure Java implementation of the [Sass](http://sass-lang.com) compiler, forked from the
archived [Vaadin sass-compiler](https://github.com/vaadin/sass-compiler).

Vaadin's original compiler is the only pure-Java SASS implementation available, but it is
unmaintained (archived May 2022, last release 0.9.13) and breaks on newer JDKs — notably the
`SCSSErrorHandler.get()` `NullPointerException` seen when running on JDK 27.

This fork keeps the compiler building on modern JDKs (Java 8+ source/target, updated Maven
plugins) and is used as the internal SASS compiler behind
[`htmlcompiler`](https://github.com/codemonstur/htmlcompiler).

## Building

```
mvn -DskipTests clean package
mvn test
```

## License

Apache License, Version 2.0 (inherited from the Vaadin project).

## Status

Unmaintained upstream; this fork exists to keep the code compiling and to fix JDK-compat
issues. For new projects, prefer [dart-sass](https://sass-lang.com/dart-sass/).
