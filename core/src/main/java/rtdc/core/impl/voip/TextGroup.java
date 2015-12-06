package rtdc.core.impl.voip;

public interface TextGroup {

    TextMessage createTextMessage(String message);

    void sendTextMessage(TextMessage textMessage);

}
