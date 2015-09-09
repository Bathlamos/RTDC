package rtdc.android.presenter.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import rtdc.android.R;
import rtdc.android.presenter.CommunicationHubInCallActivity;

public abstract class AbstractCallFragment extends AbstractFragment{

    protected View view;
    protected CommunicationHubInCallActivity inCallActivity;

    public abstract void hangupCleanup();

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        inCallActivity = (CommunicationHubInCallActivity) activity;
    }

    @Override
    public void onStart() {
        super.onStart();
        view.findViewById(R.id.muteButton).setOnClickListener(inCallActivity);
        view.findViewById(R.id.videoButton).setOnClickListener(inCallActivity);
        view.findViewById(R.id.speakerButton).setOnClickListener(inCallActivity);
        view.findViewById(R.id.endCallButton).setOnClickListener(inCallActivity);
    }
}
