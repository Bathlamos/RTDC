package rtdc.core.impl;

import rtdc.core.model.Unit;
import rtdc.core.model.User;

public interface JsonSerializer {

    public String toJson(User user);
    public String toJson(Unit user);

}
