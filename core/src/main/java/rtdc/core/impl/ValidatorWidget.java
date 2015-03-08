package rtdc.core.impl;

import rtdc.core.model.RtdcObject;

public interface ValidatorWidget<T> {

    void bind(RtdcObject object, RtdcObject.Property property);

}
