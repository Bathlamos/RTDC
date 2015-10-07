package rtdc.core.controller;

import rtdc.core.event.ErrorEvent;
import rtdc.core.event.Event;
import rtdc.core.view.View;

import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class Controller<T extends View> implements ErrorEvent.Handler {

    protected final T view;

    public Controller(T view){
        Event.subscribe(ErrorEvent.TYPE, this);
        this.view = view;
        view.setTitle(getTitle());
    }

    @Override
    public void onError(ErrorEvent event) {
        Logger.getLogger(Controller.class.getName()).log(Level.WARNING, event.toString());
        view.displayError("Error", event.getDescription());
    }

    abstract String getTitle();

    public T getView(){
        return view;
    }

    public void onStop(){
        Event.unsubscribe(ErrorEvent.TYPE, this);
    }

}
