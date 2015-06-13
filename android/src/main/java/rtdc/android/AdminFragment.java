package rtdc.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.*;
import android.widget.TabHost;
import rtdc.android.presenter.ActionPlanFragment;
import rtdc.android.presenter.CapacityOverviewFragment;
import rtdc.android.presenter.CreateUnitActivity;
import rtdc.android.presenter.CreateUserActivity;
import rtdc.android.presenter.fragments.AbstractFragment;

import java.lang.reflect.Field;

public class AdminFragment extends AbstractFragment {

    private final static int TAB_ACCOUNTS = 0;
    private final static int TAB_UNITS = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_admin, container, false);

        TabHost tabHost = (TabHost) view.findViewById(R.id.tabHost);

        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("accounts");
        tabSpec.setContent(R.id.tabAccounts);
        tabSpec.setIndicator("Manage Accounts");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("units");
        tabSpec.setContent(R.id.tabUnits);
        tabSpec.setIndicator("Manage Units");
        tabHost.addTab(tabSpec);

        // This is to force the display of the overlay button in the action bar
        try {
            ViewConfiguration config = ViewConfiguration.get(getActivity());
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");

            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        }
        catch (Exception e) {
            // presumably, not relevant
        }

        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main_activity, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        Intent intent;
        switch (item.getItemId()) {
            case R.id.action_add:
                TabHost tabHost = (TabHost) getView().findViewById(R.id.tabHost);
                if (tabHost.getCurrentTab() == TAB_ACCOUNTS) {
                    intent = new Intent(getActivity(), CreateUserActivity.class);
                    startActivity(intent);

                } else if (tabHost.getCurrentTab() == TAB_UNITS) {
                    intent = new Intent(getActivity(), CreateUnitActivity.class);
                    startActivity(intent);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
