package rtdc.android.presenter.fragments;

import android.app.Activity;
import android.view.View;
import android.widget.ImageButton;
import rtdc.android.R;
import rtdc.android.impl.AndroidVoipController;
import rtdc.android.presenter.InCallActivity;

public abstract class AbstractCallFragment extends AbstractFragment{

    protected View view;
    protected InCallActivity inCallActivity;

    public abstract void onCallEstablished();
    public abstract void onCallHangup();

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        inCallActivity = (InCallActivity) activity;
    }

    @Override
    public void onStart() {
        super.onStart();
        view.findViewById(R.id.muteButton).setOnClickListener(inCallActivity);
        view.findViewById(R.id.videoButton).setOnClickListener(inCallActivity);
        view.findViewById(R.id.endCallButton).setOnClickListener(inCallActivity);

        // Update all buttons

        inCallActivity.setButtonPressed((ImageButton) view.findViewById(R.id.muteButton), AndroidVoipController.get().isMicMuted());
        inCallActivity.setButtonPressed((ImageButton) view.findViewById(R.id.videoButton), AndroidVoipController.get().isVideoEnabled());
    }
}
