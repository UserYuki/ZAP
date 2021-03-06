package Backend.Classes;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class CorruptLocationFilter {
    private double maxSpeed = 400; // KM/H

    public CorruptLocationFilter() {}

    //calculates the distance between 2 points on a sphere
    public double calculateDistance(double lat1, double lon1, double lat2, double lon2){
        double R = 6371.137; // Radius of earth in KM
        double dLat = lat2 * Math.PI / 180 - lat1 * Math.PI / 180;
        double dLon = lon2 * Math.PI / 180 - lon1 * Math.PI / 180;
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(lat1 * Math.PI / 180) * Math.cos(lat2 * Math.PI / 180) *
                        Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double d = R * c;
        return d; // in KM
    }

    public double avgDifference(double data1, double data2, double data3)
    {
        return (data1+data2+data3)/3;
    }

    public double calculateSpeed(Duration time, double distance) // time in seconds : distance in KM
    {
        double hours = time.toHours();
        if (hours == 0)
        {
            hours = 0.005;
        }
        return distance/hours; // KM/H
    }

    public TripEntry createFalseTripEntry(TripEntry entry, double alt, double lon, double lat)
    {
        TripEntry fakeEntry = new TripEntry(entry.getVehicleID(), lat, lon, (int) alt, entry.getDateTime(), entry.getSpeed(), entry.getSpeedlimit(), entry.getRoadType(), entry.isIgnition());
        fakeEntry.setFake(true);
        return fakeEntry;
    }

    public TripEntry doFilter(TripEntry newEntry, List<TripEntry> data) //TODO: maybe add a trySwitch method which checks the speed if the long and lat were changed
    {
        //if one of the first 3 entries is corrupt it breaks this keeps the application alive even tho the corrupt entries don't get filtered
        if (data.size() < 3)
        {
            return null;
        }



        //get data from parameter
        TripEntry first = data.get(0);
        TripEntry second = data.get(1);
        TripEntry third = data.get(2);



        if (first.getFake() == true) // if working with fake data increase the margin
        {
            maxSpeed = 450;
        }

        //calculate the speed
        double distance = calculateDistance(newEntry.getLat(), newEntry.getLon(), first.getLat(), first.getLon());
        Duration time = Duration.between(newEntry.getDateTime().toLocalTime(),first.getDateTime().toLocalTime());
        double speed = calculateSpeed(time, distance);

        //if the car was too fast then the data was corrupt otherwise give back nothing
        if (speed > maxSpeed || speed < 0)
        {
            maxSpeed = 450;
            //create the fake location for the fake data with the average change
            double fakeAlt = first.getAlt()+avgDifference(first.getAlt(), second.getAlt(), third.getAlt());
            double fakeLon = first.getLon()+avgDifference(first.getLon(), second.getLon(), third.getLon());
            double fakeLat = first.getLat()+avgDifference(first.getLat(), second.getLat(), third.getLat());
            double damn = newEntry.getLat();

            return createFalseTripEntry(first, first.getAlt(), first.getLon(), first.getLat()); //return a fake entry with the fake location and the corrupt data entry
        }
        maxSpeed = 400;
        return null;
    }
}
