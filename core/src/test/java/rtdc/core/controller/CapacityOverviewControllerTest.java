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

import com.google.common.collect.ImmutableSet;

import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

import org.mockito.Captor;
import org.mockito.ArgumentCaptor;
import static org.mockito.Mockito.*;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import rtdc.core.model.Unit;
import rtdc.core.service.Service;
import rtdc.core.event.FetchUnitsEvent;
import rtdc.core.view.CapacityOverviewView;

import java.util.ArrayList;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Service.class)
public class CapacityOverviewControllerTest {

    private CapacityOverviewController capacityOverviewController;
    private CapacityOverviewView mockView;
    private FetchUnitsEvent mockEvent;

    private Unit unit123,unit456,unit789;

    @Captor
    private ArgumentCaptor<ArrayList<Unit>> setUnitsArgument;

    @Before
    public void setup(){
        // Mock classes
        PowerMockito.mockStatic(Service.class);

        mockView = mock(CapacityOverviewView.class);
        mockEvent = mock(FetchUnitsEvent.class);

        // Stub Method call
        unit123 = UnitListControllerTest.buildUnit(123,"Emergency", 16, 3, 4, 2, 10, 8);
        unit456 = UnitListControllerTest.buildUnit(456,"Surgery", 40, 6, 3, 4, 13, 10);
        unit789 = UnitListControllerTest.buildUnit(789,"Medicine", 32, 10, 2, 0, 9, 5);

        ImmutableSet<Unit> unitSet = ImmutableSet.of(unit123,unit456,unit789);
        when(mockEvent.getUnits()).thenReturn(unitSet);

        // Create controller
        capacityOverviewController = new CapacityOverviewController(mockView);
    }

    @Test
    public void onUnitsFetched_normalUnitList(){
        capacityOverviewController.onUnitsFetched(mockEvent);
        verify(mockView).setUnits(setUnitsArgument.capture());
        assertFalse(setUnitsArgument.getValue().isEmpty());
    }

    @Test
    public void onUnitsFetched_emptyUnitList(){
        // Setup empty unit list for FetchUnitEvent
        FetchUnitsEvent mockEmptyListEvent = mock(FetchUnitsEvent.class);
        ImmutableSet<Unit> unitSet = ImmutableSet.of();
        when(mockEmptyListEvent.getUnits()).thenReturn(unitSet);

        // Test
        capacityOverviewController.onUnitsFetched(mockEmptyListEvent);
        verify(mockView).setUnits(setUnitsArgument.capture());
        assertTrue(setUnitsArgument.getValue().isEmpty());
    }

    @Test
    public void sortUnit_byId_ascending(){
        // Setup original list
        capacityOverviewController.onUnitsFetched(mockEvent);

        // Expected result
        ArrayList<Unit> expectedList = new ArrayList<>();
        expectedList.add(unit123);
        expectedList.add(unit456);
        expectedList.add(unit789);

        // Call method & capture call to setter
        capacityOverviewController.sortUnits(Unit.Properties.id, true);
        verify(mockView, atLeastOnce()).setUnits(setUnitsArgument.capture());
        ArrayList<Unit> actualList = setUnitsArgument.getValue();

        // Compare expected & actual
        assertEquals(expectedList, actualList);
    }

    @Test
    public void sortUnit_byName_ascending(){
        // Setup original list
        capacityOverviewController.onUnitsFetched(mockEvent);

        // Expected result
        ArrayList<Unit> expectedList = new ArrayList<>();
        expectedList.add(unit123);
        expectedList.add(unit789);
        expectedList.add(unit456);

        // Call method & capture call to setter
        capacityOverviewController.sortUnits(Unit.Properties.name, true);
        verify(mockView, atLeastOnce()).setUnits(setUnitsArgument.capture());
        ArrayList<Unit> actualList = setUnitsArgument.getValue();

        // Compare expected & actual
        assertEquals(expectedList, actualList);
    }

    @Test
    public void sortUnit_byTotalBeds_ascending(){
        // Setup original list
        capacityOverviewController.onUnitsFetched(mockEvent);

        // Expected result
        ArrayList<Unit> expectedList = new ArrayList<>();
        expectedList.add(unit123);
        expectedList.add(unit789);
        expectedList.add(unit456);

        // Call method & capture call to setter
        capacityOverviewController.sortUnits(Unit.Properties.totalBeds, true);
        verify(mockView, atLeastOnce()).setUnits(setUnitsArgument.capture());
        ArrayList<Unit> actualList = setUnitsArgument.getValue();

        // Compare expected & actual
        assertEquals(expectedList, actualList);
    }

    @Test
    public void sortUnit_byAvailableBeds_ascending(){
        // Setup original list
        capacityOverviewController.onUnitsFetched(mockEvent);

        // Expected result
        ArrayList<Unit> expectedList = new ArrayList<>();
        expectedList.add(unit123);
        expectedList.add(unit456);
        expectedList.add(unit789);

        // Call method & capture call to setter
        capacityOverviewController.sortUnits(Unit.Properties.availableBeds, true);
        verify(mockView, atLeastOnce()).setUnits(setUnitsArgument.capture());
        ArrayList<Unit> actualList = setUnitsArgument.getValue();

        // Compare expected & actual
        assertEquals(expectedList, actualList);
    }

    @Test
    public void sortUnit_byPotentialDC_ascending(){
        // Setup original list
        capacityOverviewController.onUnitsFetched(mockEvent);

        // Expected result
        ArrayList<Unit> expectedList = new ArrayList<>();
        expectedList.add(unit789);
        expectedList.add(unit456);
        expectedList.add(unit123);

        // Call method & capture call to setter
        capacityOverviewController.sortUnits(Unit.Properties.potentialDc, true);
        verify(mockView, atLeastOnce()).setUnits(setUnitsArgument.capture());
        ArrayList<Unit> actualList = setUnitsArgument.getValue();

        // Compare expected & actual
        assertEquals(expectedList, actualList);
    }

    @Test
    public void sortUnit_byDcByDeadline_ascending(){
        // Setup original list
        capacityOverviewController.onUnitsFetched(mockEvent);

        // Expected result
        ArrayList<Unit> expectedList = new ArrayList<>();
        expectedList.add(unit789);
        expectedList.add(unit123);
        expectedList.add(unit456);

        // Call method & capture call to setter
        capacityOverviewController.sortUnits(Unit.Properties.dcByDeadline, true);
        verify(mockView, atLeastOnce()).setUnits(setUnitsArgument.capture());
        ArrayList<Unit> actualList = setUnitsArgument.getValue();

        // Compare expected & actual
        assertEquals(expectedList, actualList);
    }

    @Test
    public void sortUnit_byTotalAdmits_ascending(){
        // Setup original list
        capacityOverviewController.onUnitsFetched(mockEvent);

        // Expected result
        ArrayList<Unit> expectedList = new ArrayList<>();
        expectedList.add(unit789);
        expectedList.add(unit123);
        expectedList.add(unit456);

        // Call method & capture call to setter
        capacityOverviewController.sortUnits(Unit.Properties.totalAdmits, true);
        verify(mockView, atLeastOnce()).setUnits(setUnitsArgument.capture());
        ArrayList<Unit> actualList = setUnitsArgument.getValue();

        // Compare expected & actual
        assertEquals(expectedList, actualList);
    }

    @Test
    public void sortUnit_byAdmitsAtDeadline_ascending(){
        // Setup original list
        capacityOverviewController.onUnitsFetched(mockEvent);

        // Expected result
        ArrayList<Unit> expectedList = new ArrayList<>();
        expectedList.add(unit789);
        expectedList.add(unit123);
        expectedList.add(unit456);

        // Call method & capture call to setter
        capacityOverviewController.sortUnits(Unit.Properties.admitsByDeadline, true);
        verify(mockView, atLeastOnce()).setUnits(setUnitsArgument.capture());
        ArrayList<Unit> actualList = setUnitsArgument.getValue();

        // Compare expected & actual
        assertEquals(expectedList, actualList);
    }

    @Test
    public void sortUnit_byStatusAtDeadline_ascending(){
        // Setup original list
        capacityOverviewController.onUnitsFetched(mockEvent);

        // Expected result
        ArrayList<Unit> expectedList = new ArrayList<>();
        expectedList.add(unit123); // Status = -3
        expectedList.add(unit456); // Status = 0
        expectedList.add(unit789); // Status = 5

        // Call method & capture call to setter
        capacityOverviewController.sortUnits(Unit.Properties.statusAtDeadline, true);
        verify(mockView, atLeastOnce()).setUnits(setUnitsArgument.capture());
        ArrayList<Unit> actualList = setUnitsArgument.getValue();

        // Compare expected & actual
        assertEquals(expectedList, actualList);
    }

    @Test
    public void sortUnit_byId_descending(){
        // Setup original list
        capacityOverviewController.onUnitsFetched(mockEvent);

        // Expected result
        ArrayList<Unit> expectedList = new ArrayList<>();
        expectedList.add(unit789);
        expectedList.add(unit456);
        expectedList.add(unit123);

        // Call method & capture call to setter
        capacityOverviewController.sortUnits(Unit.Properties.id, false);
        verify(mockView, atLeastOnce()).setUnits(setUnitsArgument.capture());
        ArrayList<Unit> actualList = setUnitsArgument.getValue();

        // Compare expected & actual
        assertEquals(expectedList, actualList);
    }

    @Test
    public void sortUnit_byName_descending(){
        // Setup original list
        capacityOverviewController.onUnitsFetched(mockEvent);

        // Expected result
        ArrayList<Unit> expectedList = new ArrayList<>();
        expectedList.add(unit456);
        expectedList.add(unit789);
        expectedList.add(unit123);

        // Call method & capture call to setter
        capacityOverviewController.sortUnits(Unit.Properties.name, false);
        verify(mockView, atLeastOnce()).setUnits(setUnitsArgument.capture());
        ArrayList<Unit> actualList = setUnitsArgument.getValue();

        // Compare expected & actual
        assertEquals(expectedList, actualList);
    }

    @Test
    public void sortUnit_byTotalBeds_descending(){
        // Setup original list
        capacityOverviewController.onUnitsFetched(mockEvent);

        // Expected result
        ArrayList<Unit> expectedList = new ArrayList<>();
        expectedList.add(unit456);
        expectedList.add(unit789);
        expectedList.add(unit123);

        // Call method & capture call to setter
        capacityOverviewController.sortUnits(Unit.Properties.totalBeds, false);
        verify(mockView, atLeastOnce()).setUnits(setUnitsArgument.capture());
        ArrayList<Unit> actualList = setUnitsArgument.getValue();

        // Compare expected & actual
        assertEquals(expectedList, actualList);
    }

    @Test
    public void sortUnit_byAvailableBeds_descending(){
        // Setup original list
        capacityOverviewController.onUnitsFetched(mockEvent);

        // Expected result
        ArrayList<Unit> expectedList = new ArrayList<>();
        expectedList.add(unit789);
        expectedList.add(unit456);
        expectedList.add(unit123);

        // Call method & capture call to setter
        capacityOverviewController.sortUnits(Unit.Properties.availableBeds, false);
        verify(mockView, atLeastOnce()).setUnits(setUnitsArgument.capture());
        ArrayList<Unit> actualList = setUnitsArgument.getValue();

        // Compare expected & actual
        assertEquals(expectedList, actualList);
    }

    @Test
    public void sortUnit_byPotentialDC_descending(){
        // Setup original list
        capacityOverviewController.onUnitsFetched(mockEvent);

        // Expected result
        ArrayList<Unit> expectedList = new ArrayList<>();
        expectedList.add(unit123);
        expectedList.add(unit456);
        expectedList.add(unit789);

        // Call method & capture call to setter
        capacityOverviewController.sortUnits(Unit.Properties.potentialDc, false);
        verify(mockView, atLeastOnce()).setUnits(setUnitsArgument.capture());
        ArrayList<Unit> actualList = setUnitsArgument.getValue();

        // Compare expected & actual
        assertEquals(expectedList, actualList);
    }

    @Test
    public void sortUnit_byDcByDeadline_descending(){
        // Setup original list
        capacityOverviewController.onUnitsFetched(mockEvent);

        // Expected result
        ArrayList<Unit> expectedList = new ArrayList<>();
        expectedList.add(unit456);
        expectedList.add(unit123);
        expectedList.add(unit789);

        // Call method & capture call to setter
        capacityOverviewController.sortUnits(Unit.Properties.dcByDeadline, false);
        verify(mockView, atLeastOnce()).setUnits(setUnitsArgument.capture());
        ArrayList<Unit> actualList = setUnitsArgument.getValue();

        // Compare expected & actual
        assertEquals(expectedList, actualList);
    }

    @Test
    public void sortUnit_byTotalAdmits_descending(){
        // Setup original list
        capacityOverviewController.onUnitsFetched(mockEvent);

        // Expected result
        ArrayList<Unit> expectedList = new ArrayList<>();
        expectedList.add(unit456);
        expectedList.add(unit123);
        expectedList.add(unit789);

        // Call method & capture call to setter
        capacityOverviewController.sortUnits(Unit.Properties.totalAdmits, false);
        verify(mockView, atLeastOnce()).setUnits(setUnitsArgument.capture());
        ArrayList<Unit> actualList = setUnitsArgument.getValue();

        // Compare expected & actual
        assertEquals(expectedList, actualList);
    }

    @Test
    public void sortUnit_byAdmitsAtDeadline_descending(){
        // Setup original list
        capacityOverviewController.onUnitsFetched(mockEvent);

        // Expected result
        ArrayList<Unit> expectedList = new ArrayList<>();
        expectedList.add(unit456);
        expectedList.add(unit123);
        expectedList.add(unit789);

        // Call method & capture call to setter
        capacityOverviewController.sortUnits(Unit.Properties.admitsByDeadline, false);
        verify(mockView, atLeastOnce()).setUnits(setUnitsArgument.capture());
        ArrayList<Unit> actualList = setUnitsArgument.getValue();

        // Compare expected & actual
        assertEquals(expectedList, actualList);
    }

    @Test
    public void sortUnit_byStatusAtDeadline_descending(){
        // Setup original list
        capacityOverviewController.onUnitsFetched(mockEvent);

        // Expected result
        ArrayList<Unit> expectedList = new ArrayList<>();
        expectedList.add(unit789); // Status = -3
        expectedList.add(unit456); // Status = 0
        expectedList.add(unit123); // Status = 5

        // Call method & capture call to setter
        capacityOverviewController.sortUnits(Unit.Properties.statusAtDeadline, false);
        verify(mockView, atLeastOnce()).setUnits(setUnitsArgument.capture());
        ArrayList<Unit> actualList = setUnitsArgument.getValue();

        // Compare expected & actual
        assertEquals(expectedList, actualList);
    }

}
