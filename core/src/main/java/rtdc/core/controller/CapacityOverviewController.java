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

package rtdc.core.controller;

import rtdc.core.Bootstrapper;
import rtdc.core.event.Event;
import rtdc.core.event.FetchUnitsEvent;
import rtdc.core.i18n.ResBundle;
import rtdc.core.model.SimpleComparator;
import rtdc.core.model.Unit;
import rtdc.core.service.Service;
import rtdc.core.util.Cache;
import rtdc.core.view.CapacityOverviewView;

import java.util.*;
import java.util.logging.Logger;

public class CapacityOverviewController extends Controller<CapacityOverviewView> implements FetchUnitsEvent.Handler {

    private static final Logger logger = Logger.getLogger(CapacityOverviewController.class.getCanonicalName());

    private ArrayList<Unit> units;

    public CapacityOverviewController(CapacityOverviewView view){
        super(view);
        Event.subscribe(FetchUnitsEvent.TYPE, this);
        Service.getUnits();
    }

    @Override
    String getTitle() {
        return ResBundle.get().capacityOverviewTitle();
    }

    public void editCapacity(Unit unit){
        Cache.getInstance().put("unit", unit);
        Bootstrapper.getFactory().newDispatcher().goToEditCapacity(this);
    }

    public void sortUnits(Unit.Properties property, boolean ascending){
        ArrayList<Unit> sortedUnits = new ArrayList<>(units);
        Collections.sort(sortedUnits, SimpleComparator.forProperty(property).setAscending(ascending).build());
        view.setUnits(sortedUnits);
    }

    @Override
    public void onUnitsFetched(FetchUnitsEvent event) {
        units = new ArrayList<>(event.getUnits());
        sortUnits(Unit.Properties.name, true);
    }

    // Unit updated from EditCapacityActivity?
    public boolean unitUpdated(){
        return Cache.getInstance().remove("unit") != null;
    }

    @Override
    public void onStop() {
        super.onStop();
        Event.unsubscribe(FetchUnitsEvent.TYPE, this);
    }

}
