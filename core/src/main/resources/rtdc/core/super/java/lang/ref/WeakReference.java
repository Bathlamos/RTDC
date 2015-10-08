package java.lang.ref;

import com.google.j2objc.annotations.J2ObjCIncompatible;

//HACKY class to make Weakreferences compilable under GWT.
@J2ObjCIncompatible
public class WeakReference<T extends java.lang.Object> {

    private T obj;

    public WeakReference(T param) {
        obj = param;
    }

    public T get()
    {
        return obj;
    }

}