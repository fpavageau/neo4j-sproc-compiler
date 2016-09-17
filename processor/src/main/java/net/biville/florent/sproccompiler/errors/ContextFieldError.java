package net.biville.florent.sproccompiler.errors;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;

public class ContextFieldError implements CompilationError {

    private final Element field;
    private final String errorMessage;

    public ContextFieldError(Element field, String errorMessage, CharSequence... args) {

        this.field = field;
        this.errorMessage = String.format(errorMessage, args);
    }

    @Override
    public Element getElement() {
        return field;
    }

    @Override
    public AnnotationMirror getMirror() {
        return null;
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }
}