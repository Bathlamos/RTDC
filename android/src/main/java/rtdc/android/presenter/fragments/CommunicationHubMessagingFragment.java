package rtdc.android.presenter.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import rtdc.android.R;
import rtdc.core.controller.CapacityOverviewController;
import rtdc.core.model.Message;
import rtdc.core.view.CommunicationHubMessagingView;

import java.util.ArrayList;
import java.util.List;

public class CommunicationHubMessagingFragment extends AbstractFragment implements CommunicationHubMessagingView {

    private ArrayAdapter<Message> adapter;
    private ArrayList<Message> messages = new ArrayList<Message>();
    private CapacityOverviewController controller;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_communication_hub_messaging, container, false);
        AdapterView unitListView = (AdapterView) view.findViewById(R.id.capacity_list_view);

        adapter = new MessageListAdapter(getActivity(), messages);
        unitListView.setAdapter(adapter);

        if (controller == null)
            controller = new CapacityOverviewController(this);

        return view;
    }

    @Override
    public void setMessages(List<Message> messages) {
        this.messages.clear();
        this.messages.addAll(messages);
        adapter.notifyDataSetChanged();
    }

    private class UnitListAdapter extends ArrayAdapter<Message> {

        private LayoutInflater inflater;

        public UnitListAdapter(List<Message> units, Context context) {
            super(context, R.layout.adapter_capacity_overview, units);
            inflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if (view == null)
                view = inflater.inflate(R.layout.adapter_capacity_overview, parent, false);

            Message currentUnit = messages.get(position);
            int status = currentUnit.getAvailableBeds() + currentUnit.getDcByDeadline() - currentUnit.getAdmitsByDeadline();

            setupColumn(view, R.id.unitName,        currentUnit.getName());
            setupColumn(view, R.id.availableBeds,   Integer.toString(currentUnit.getAvailableBeds()));
            setupColumn(view, R.id.potentialDC,     Integer.toString(currentUnit.getPotentialDc()));
            setupColumn(view, R.id.DCByDeadline,    Integer.toString(currentUnit.getDcByDeadline()));
            setupColumn(view, R.id.totalAdmits,     Integer.toString(currentUnit.getTotalAdmits()));
            setupColumn(view, R.id.admitsByDeadline,Integer.toString(currentUnit.getAdmitsByDeadline()));
            setupColumn(view, R.id.statusAtDeadline,Integer.toString(status));

            view.setTag(position);
            view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    controller.editCapacity(units.get(Integer.parseInt(v.getTag().toString())));
                }

            });

            return view;
        }

        private void setupColumn(View view, int resourceId, String text){
            TextView textView = (TextView) view.findViewById(resourceId);
            textView.setText(text);
        }
    }


    @Override
    public void onStop() {
        super.onStop();
        controller.onStop();
    }
}