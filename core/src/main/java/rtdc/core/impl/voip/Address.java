package rtdc.core.impl.voip;

public interface Address {

    String getDisplayName();
    void setDisplayName(String displayName);

    String getUsername();
    void setUsername(String username);

    String asStringURI();
}
