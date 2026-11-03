# TODO / Review findings

- Vaadin SASS compiler is unmaintained (archived May 2022). `SCSSErrorHandler.get()` returns null on newer JDKs, causing NPEs.
- SASS support is SASS 3.0-era; modern SCSS features unsupported.
- Build uses Java 8 source/target; consider upgrading.
- Consider replacing with dart-sass (external tool) or pre-generating CSS.
- No pure Java maintained alternative exists.
