package API;

import Backend.Classes.Trip;
import Backend.Classes.Vehicle;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class FakeTripData {

    private List<Trip> s;

    public FakeTripData() {
        this.s = new ArrayList<>();

        ZonedDateTime zone = ZonedDateTime.now();

    }

    public List<Trip> getS() {
        return s;
    }
}
