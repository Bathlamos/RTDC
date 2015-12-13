/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Olivier Clermont, Jonathan Ermel, Mathieu Fortin-Boulay, Philippe Legault & Nicolas Ménard
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package rtdc.android.presenter.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import rtdc.android.R;

import rtdc.android.presenter.CreateUnitActivity;
import rtdc.core.controller.UnitListController;
import rtdc.core.model.Unit;
import rtdc.core.view.UnitListView;

import java.util.ArrayList;
import java.util.List;

public class ManageUnitsFragment extends AbstractFragment implements AbsListView.OnItemClickListener, UnitListView {

    private UnitListController controller;

    private List<Unit> units = new ArrayList<Unit>();
    private UnitListAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manage_units, container, false);
        setHasOptionsMenu(true);

        // Set the adapter
        AdapterView mListView = (AdapterView) view.findViewById(R.id.units_listView);
        mAdapter = new UnitListAdapter(getActivity(), units);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(this);

        if(controller == null)
            controller = new UnitListController(this);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_manage_units, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        Intent intent;
        switch (item.getItemId()) {
            case R.id.action_add:
                intent = new Intent(getActivity(), CreateUnitActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void setUnits(List<Unit> units) {
        this.units.clear();
        this.units.addAll(units);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Unit unit = (Unit) mAdapter.getItem(position);
        controller.editUnit(unit);
    }

    @Override
    public void onStart() {
        super.onStart();
        controller.updateUnits();   // Update unit list with edited unit when returning from CreateUnitActivity
    }

    @Override
    public void onStop() {
        super.onStop();
        controller.onStop();
    }
}
