package rtdc.core.controller;

import com.google.common.collect.ImmutableSet;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import rtdc.core.event.FetchUnitsEvent;
import rtdc.core.model.Unit;
import rtdc.core.service.Service;
import rtdc.core.view.UnitListView;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Service.class)
public class UnitListControllerTest {

    private UnitListController unitListController;
    private UnitListView mockView;
    private FetchUnitsEvent mockEvent;

    private Unit unit123,unit456,unit789;

    @Captor
    private ArgumentCaptor<ArrayList<Unit>> setUnitsArgument;

    @Before
    public void setup(){
        // Mock classes
        PowerMockito.mockStatic(Service.class);

        mockView = mock(UnitListView.class);
        mockEvent = mock(FetchUnitsEvent.class);

        // Stub Method call
        unit123 = buildUnit(123,"Emergency", 16, 3, 4, 2, 10, 8);
        unit456 = buildUnit(456,"Surgery", 40, 6, 3, 4, 13, 10);
        unit789 = buildUnit(789,"Medicine", 32, 10, 2, 0, 9, 5);

        ImmutableSet<Unit> unitSet = ImmutableSet.of(unit123,unit456,unit789);
        when(mockEvent.getUnits()).thenReturn(unitSet);

        // Create controller
        unitListController = new UnitListController(mockView);
    }


    @Test
    public void onUnitsFetched_normalUnitList(){
        unitListController.onUnitsFetched(mockEvent);
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
        unitListController.onUnitsFetched(mockEmptyListEvent);
        verify(mockView).setUnits(setUnitsArgument.capture());
        assertTrue(setUnitsArgument.getValue().isEmpty());
    }

    @Test
    public void sortUnit_byId_ascending(){
        // Setup original list
        unitListController.onUnitsFetched(mockEvent);

        // Expected result
        ArrayList<Unit> expectedList = new ArrayList<>();
        expectedList.add(0,unit123);
        expectedList.add(1,unit456);
        expectedList.add(2,unit789);

        // Call method & capture call to setter
        unitListController.sortUnits(Unit.Properties.id, true);
        verify(mockView, atLeastOnce()).setUnits(setUnitsArgument.capture());
        ArrayList<Unit> actualList = setUnitsArgument.getValue();

        // Compare expected & actual
        Assert.assertEquals(expectedList, actualList);
    }

    @Test
    public void sortUnit_byName_ascending(){
        // Setup original list
        unitListController.onUnitsFetched(mockEvent);

        // Expected result
        ArrayList<Unit> expectedList = new ArrayList<>();
        expectedList.add(0,unit123);
        expectedList.add(1,unit789);
        expectedList.add(2,unit456);

        // Call method & capture call to setter
        unitListController.sortUnits(Unit.Properties.name, true);
        verify(mockView, atLeastOnce()).setUnits(setUnitsArgument.capture());
        ArrayList<Unit> actualList = setUnitsArgument.getValue();

        // Compare expected & actual
        Assert.assertEquals(expectedList, actualList);
    }

    @Test
    public void sortUnit_byTotalBeds_ascending(){
        // Setup original list
        unitListController.onUnitsFetched(mockEvent);

        // Expected result
        ArrayList<Unit> expectedList = new ArrayList<>();
        expectedList.add(0,unit123);
        expectedList.add(1,unit789);
        expectedList.add(2,unit456);

        // Call method & capture call to setter
        unitListController.sortUnits(Unit.Properties.totalBeds, true);
        verify(mockView, atLeastOnce()).setUnits(setUnitsArgument.capture());
        ArrayList<Unit> actualList = setUnitsArgument.getValue();

        // Compare expected & actual
        Assert.assertEquals(expectedList, actualList);
    }

    @Test
    public void sortUnit_byAvailableBeds_ascending(){
        // Setup original list
        unitListController.onUnitsFetched(mockEvent);

        // Expected result
        ArrayList<Unit> expectedList = new ArrayList<>();
        expectedList.add(0,unit123);
        expectedList.add(1,unit456);
        expectedList.add(2,unit789);

        // Call method & capture call to setter
        unitListController.sortUnits(Unit.Properties.availableBeds, true);
        verify(mockView, atLeastOnce()).setUnits(setUnitsArgument.capture());
        ArrayList<Unit> actualList = setUnitsArgument.getValue();

        // Compare expected & actual
        Assert.assertEquals(expectedList, actualList);
    }

    @Test
    public void sortUnit_byPotentialDC_ascending(){
        // Setup original list
        unitListController.onUnitsFetched(mockEvent);

        // Expected result
        ArrayList<Unit> expectedList = new ArrayList<>();
        expectedList.add(0,unit789);
        expectedList.add(1,unit456);
        expectedList.add(2,unit123);

        // Call method & capture call to setter
        unitListController.sortUnits(Unit.Properties.potentialDc, true);
        verify(mockView, atLeastOnce()).setUnits(setUnitsArgument.capture());
        ArrayList<Unit> actualList = setUnitsArgument.getValue();

        // Compare expected & actual
        Assert.assertEquals(expectedList, actualList);
    }

    @Test
    public void sortUnit_byDcByDeadline_ascending(){
        // Setup original list
        unitListController.onUnitsFetched(mockEvent);

        // Expected result
        ArrayList<Unit> expectedList = new ArrayList<>();
        expectedList.add(0,unit789);
        expectedList.add(1,unit123);
        expectedList.add(2,unit456);

        // Call method & capture call to setter
        unitListController.sortUnits(Unit.Properties.dcByDeadline, true);
        verify(mockView, atLeastOnce()).setUnits(setUnitsArgument.capture());
        ArrayList<Unit> actualList = setUnitsArgument.getValue();

        // Compare expected & actual
        Assert.assertEquals(expectedList, actualList);
    }

    @Test
    public void sortUnit_byTotalAdmits_ascending(){
        // Setup original list
        unitListController.onUnitsFetched(mockEvent);

        // Expected result
        ArrayList<Unit> expectedList = new ArrayList<>();
        expectedList.add(0,unit789);
        expectedList.add(1,unit123);
        expectedList.add(2,unit456);

        // Call method & capture call to setter
        unitListController.sortUnits(Unit.Properties.totalAdmits, true);
        verify(mockView, atLeastOnce()).setUnits(setUnitsArgument.capture());
        ArrayList<Unit> actualList = setUnitsArgument.getValue();

        // Compare expected & actual
        Assert.assertEquals(expectedList, actualList);
    }

    @Test
    public void sortUnit_byAdmitsAtDeadline_ascending(){
        // Setup original list
        unitListController.onUnitsFetched(mockEvent);

        // Expected result
        ArrayList<Unit> expectedList = new ArrayList<>();
        expectedList.add(0,unit789);
        expectedList.add(1,unit123);
        expectedList.add(2,unit456);

        // Call method & capture call to setter
        unitListController.sortUnits(Unit.Properties.admitsByDeadline, true);
        verify(mockView, atLeastOnce()).setUnits(setUnitsArgument.capture());
        ArrayList<Unit> actualList = setUnitsArgument.getValue();

        // Compare expected & actual
        Assert.assertEquals(expectedList, actualList);
    }

    @Test
    public void sortUnit_byStatusAtDeadline_ascending(){
        // Setup original list
        unitListController.onUnitsFetched(mockEvent);

        // Expected result
        ArrayList<Unit> expectedList = new ArrayList<>();
        expectedList.add(0,unit123); // Status = -3
        expectedList.add(1,unit456); // Status = 0
        expectedList.add(2,unit789); // Status = 5

        // Call method & capture call to setter
        unitListController.sortUnits(Unit.Properties.statusAtDeadline, true);
        verify(mockView, atLeastOnce()).setUnits(setUnitsArgument.capture());
        ArrayList<Unit> actualList = setUnitsArgument.getValue();

        // Compare expected & actual
        Assert.assertEquals(expectedList, actualList);
    }

    @Test
    public void sortUnit_byId_descending(){
        // Setup original list
        unitListController.onUnitsFetched(mockEvent);

        // Expected result
        ArrayList<Unit> expectedList = new ArrayList<>();
        expectedList.add(0,unit789);
        expectedList.add(1,unit456);
        expectedList.add(2,unit123);

        // Call method & capture call to setter
        unitListController.sortUnits(Unit.Properties.id, false);
        verify(mockView, atLeastOnce()).setUnits(setUnitsArgument.capture());
        ArrayList<Unit> actualList = setUnitsArgument.getValue();

        // Compare expected & actual
        Assert.assertEquals(expectedList, actualList);
    }

    @Test
    public void sortUnit_byName_descending(){
        // Setup original list
        unitListController.onUnitsFetched(mockEvent);

        // Expected result
        ArrayList<Unit> expectedList = new ArrayList<>();
        expectedList.add(0,unit456);
        expectedList.add(1,unit789);
        expectedList.add(2,unit123);

        // Call method & capture call to setter
        unitListController.sortUnits(Unit.Properties.name, false);
        verify(mockView, atLeastOnce()).setUnits(setUnitsArgument.capture());
        ArrayList<Unit> actualList = setUnitsArgument.getValue();

        // Compare expected & actual
        Assert.assertEquals(expectedList, actualList);
    }

    @Test
    public void sortUnit_byTotalBeds_descending(){
        // Setup original list
        unitListController.onUnitsFetched(mockEvent);

        // Expected result
        ArrayList<Unit> expectedList = new ArrayList<>();
        expectedList.add(0,unit456);
        expectedList.add(1,unit789);
        expectedList.add(2,unit123);

        // Call method & capture call to setter
        unitListController.sortUnits(Unit.Properties.totalBeds, false);
        verify(mockView, atLeastOnce()).setUnits(setUnitsArgument.capture());
        ArrayList<Unit> actualList = setUnitsArgument.getValue();

        // Compare expected & actual
        Assert.assertEquals(expectedList, actualList);
    }

    @Test
    public void sortUnit_byAvailableBeds_descending(){
        // Setup original list
        unitListController.onUnitsFetched(mockEvent);

        // Expected result
        ArrayList<Unit> expectedList = new ArrayList<>();
        expectedList.add(0,unit789);
        expectedList.add(1,unit456);
        expectedList.add(2,unit123);

        // Call method & capture call to setter
        unitListController.sortUnits(Unit.Properties.availableBeds, false);
        verify(mockView, atLeastOnce()).setUnits(setUnitsArgument.capture());
        ArrayList<Unit> actualList = setUnitsArgument.getValue();

        // Compare expected & actual
        Assert.assertEquals(expectedList, actualList);
    }

    @Test
    public void sortUnit_byPotentialDC_descending(){
        // Setup original list
        unitListController.onUnitsFetched(mockEvent);

        // Expected result
        ArrayList<Unit> expectedList = new ArrayList<>();
        expectedList.add(0,unit123);
        expectedList.add(1,unit456);
        expectedList.add(2,unit789);

        // Call method & capture call to setter
        unitListController.sortUnits(Unit.Properties.potentialDc, false);
        verify(mockView, atLeastOnce()).setUnits(setUnitsArgument.capture());
        ArrayList<Unit> actualList = setUnitsArgument.getValue();

        // Compare expected & actual
        Assert.assertEquals(expectedList, actualList);
    }

    @Test
    public void sortUnit_byDcByDeadline_descending(){
        // Setup original list
        unitListController.onUnitsFetched(mockEvent);

        // Expected result
        ArrayList<Unit> expectedList = new ArrayList<>();
        expectedList.add(0,unit456);
        expectedList.add(1,unit123);
        expectedList.add(2,unit789);

        // Call method & capture call to setter
        unitListController.sortUnits(Unit.Properties.dcByDeadline, false);
        verify(mockView, atLeastOnce()).setUnits(setUnitsArgument.capture());
        ArrayList<Unit> actualList = setUnitsArgument.getValue();

        // Compare expected & actual
        Assert.assertEquals(expectedList, actualList);
    }

    @Test
    public void sortUnit_byTotalAdmits_descending(){
        // Setup original list
        unitListController.onUnitsFetched(mockEvent);

        // Expected result
        ArrayList<Unit> expectedList = new ArrayList<>();
        expectedList.add(0,unit456);
        expectedList.add(1,unit123);
        expectedList.add(2,unit789);

        // Call method & capture call to setter
        unitListController.sortUnits(Unit.Properties.totalAdmits, false);
        verify(mockView, atLeastOnce()).setUnits(setUnitsArgument.capture());
        ArrayList<Unit> actualList = setUnitsArgument.getValue();

        // Compare expected & actual
        Assert.assertEquals(expectedList, actualList);
    }

    @Test
    public void sortUnit_byAdmitsAtDeadline_descending(){
        // Setup original list
        unitListController.onUnitsFetched(mockEvent);

        // Expected result
        ArrayList<Unit> expectedList = new ArrayList<>();
        expectedList.add(0,unit456);
        expectedList.add(1,unit123);
        expectedList.add(2,unit789);

        // Call method & capture call to setter
        unitListController.sortUnits(Unit.Properties.admitsByDeadline, false);
        verify(mockView, atLeastOnce()).setUnits(setUnitsArgument.capture());
        ArrayList<Unit> actualList = setUnitsArgument.getValue();

        // Compare expected & actual
        Assert.assertEquals(expectedList, actualList);
    }

    @Test
    public void sortUnit_byStatusAtDeadline_descending(){
        // Setup original list
        unitListController.onUnitsFetched(mockEvent);

        // Expected result
        ArrayList<Unit> expectedList = new ArrayList<>();
        expectedList.add(0,unit789); // Status = -3
        expectedList.add(1,unit456); // Status = 0
        expectedList.add(2,unit123); // Status = 5

        // Call method & capture call to setter
        unitListController.sortUnits(Unit.Properties.statusAtDeadline, false);
        verify(mockView, atLeastOnce()).setUnits(setUnitsArgument.capture());
        ArrayList<Unit> actualList = setUnitsArgument.getValue();

        // Compare expected & actual
        Assert.assertEquals(expectedList, actualList);
    }

    protected static Unit buildUnit(int id, String name, int totalBeds, int availableBeds, int potentialDc, int dcByDeadline,
                                  int totalAdmits, int admitsByDeadline){
        Unit unit = new Unit();
        unit.setId(id);
        unit.setName(name);
        unit.setTotalBeds(totalBeds);
        unit.setAvailableBeds(availableBeds);
        unit.setPotentialDc(potentialDc);
        unit.setDcByDeadline(dcByDeadline);
        unit.setTotalAdmits(totalAdmits);
        unit.setAdmitsByDeadline(admitsByDeadline);
        return unit;
    }
}
