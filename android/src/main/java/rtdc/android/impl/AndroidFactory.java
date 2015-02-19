package rtdc.android.impl;

import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.HibernateValidatorConfiguration;
import rtdc.core.impl.Dispatcher;
import rtdc.core.impl.Factory;
import rtdc.core.impl.HttpRequest;

import javax.validation.Validation;
import javax.validation.ValidationProviderResolver;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.spi.ValidationProvider;
import java.util.LinkedList;
import java.util.List;

public class AndroidFactory implements Factory{

    private static class CustomValidationProviderResolver implements ValidationProviderResolver {

        @Override
        public List<ValidationProvider<?>> getValidationProviders() {
            HibernateValidator validator = new HibernateValidator();
            LinkedList<ValidationProvider<?>> validationProviders = new LinkedList<ValidationProvider<?>>();
            validationProviders.add(validator);
            return validationProviders;
        }
    }

    @Override
    public HttpRequest newHttpRequest(String url, HttpRequest.RequestMethod requestMethod) {
        return new AndroidHttpRequest(url, requestMethod);
    }

    @Override
    public Validator newValidator() {
        HibernateValidatorConfiguration config = Validation
                .byProvider(HibernateValidator.class)
                .providerResolver(new CustomValidationProviderResolver())
                .configure();
        ValidatorFactory factory = config.buildValidatorFactory();
        return factory.getValidator();
    }

    @Override
    public Dispatcher newDispatcher() {
        return new AndroidDispatcher();
    }
}
