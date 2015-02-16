package rtdc.core;

import rtdc.core.impl.Factory;

public class Bootstrapper {

    public static Factory FACTORY;

    public static void initialize(Factory factory){
        Bootstrapper.FACTORY = factory;
    }

}
