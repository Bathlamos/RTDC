package rtdc.core.impl.voip;

public interface Call {

    CallParameters getCurrentParamsCopy();

    int getDuration();

    State getState();

    Address getRemoteAddress();

    Address getFrom();
    Address getTo();

    VoIPManager.Reason getReasonForError();

    enum State {
        idle,
        incomingReceived,
        outgoingInit,
        outgoingProgress,
        outgoingRinging,
        outgoingEarlyMedia,
        connected,
        streamsRunning,
        pausing,
        paused,
        resuming,
        referred,
        error,
        callEnd,
        pausedByRemote,
        callUpdatedByRemote,
        callIncomingEarlyMedia,
        callUpdating,
        callReleased,
        callEarlyUpdatedByRemote,
        callEarlyUpdating
    }
}
