package rtdc.core.view;

import rtdc.core.model.Action;

import java.util.List;

public interface ActionListView extends View {

    void setActions(List<Action> actions);

}