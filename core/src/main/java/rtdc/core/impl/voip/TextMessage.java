package rtdc.core.impl.voip;

/**
 * Created by Nicolas Ménard on 2015-11-29.
 */
public interface TextMessage {

    String getText();

    Address getFrom();

    Address getTo();

}
