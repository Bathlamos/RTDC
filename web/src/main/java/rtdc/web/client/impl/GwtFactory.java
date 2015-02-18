package rtdc.web.client.impl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.validation.client.AbstractGwtValidatorFactory;
import com.google.gwt.validation.client.GwtValidation;
import com.google.gwt.validation.client.impl.AbstractGwtValidator;
import rtdc.core.impl.Factory;
import rtdc.core.impl.HttpRequest;
import rtdc.core.model.User;

import javax.validation.Validation;
import javax.validation.Validator;

public final class GwtFactory extends AbstractGwtValidatorFactory implements Factory {

    /**
     * Validator marker for the Validation Sample project. Only the classes and groups listed
     * in the {@link GwtValidation} annotation can be validated.
     */
    @GwtValidation(User.class)
    public interface GwtValidator extends Validator {}

    @Override
    public AbstractGwtValidator createValidator() {
        return GWT.create(GwtValidator.class);
    }

    @Override
    public HttpRequest newHttpRequest(String url, HttpRequest.RequestMethod requestMethod) {
        return new GwtHttpRequest(url, requestMethod);
    }

    @Override
    public Validator newValidator() {
        return Validation.buildDefaultValidatorFactory().getValidator();
    }

}
