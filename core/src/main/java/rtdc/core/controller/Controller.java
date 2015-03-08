package rtdc.core.controller;

import rtdc.core.event.ErrorEvent;
import rtdc.core.view.View;

public abstract class Controller<T extends View> implements ErrorEvent.ErrorHandler{

    protected final T view;

    public Controller(T view){
        ErrorEvent.subscribe(this);
        this.view = view;
    }

    @Override
    public void onError(ErrorEvent event) {
        view.displayError("Error", event.getDescription());
    }

}
