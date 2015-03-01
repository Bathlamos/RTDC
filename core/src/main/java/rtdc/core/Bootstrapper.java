package rtdc.core;

import rtdc.core.impl.Factory;

public class Bootstrapper {

    public static Factory FACTORY;

    public static String AUTHENTICATION_TOKEN;

    public static void initialize(Factory factory){
        Bootstrapper.FACTORY = factory;
    }

    //void lol(){}
}
