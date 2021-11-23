package Backend.Containers;

import Backend.Classes.*;
import Backend.DatabaseAccess.ITripDAL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class TripContainer {
    //Items

    @Autowired
    ITripDAL dal;

    private List<Trip>  trips;

    public TripContainer()
    {
        this.trips = new ArrayList<>();
    }


    //Functions (Crud)

    /*
    public boolean AddTrip(Trip t)
    {
        if(trips.add(t))
        {
            return true;
        }
        return false;
    }*/

    public boolean AddTrip(Trip t)
    {
        if(trips.add(t))
        {

            return true;
        }
        return false;
    }

    public void dbSaveTrip(Trip t)
    {
        dal.addTripinDB(t);
    }
    public Trip dbGetTrip(String id) {return dal.getTripbyIdinDB(id);}
    public List<TripEntry> dbGetLast3TripEntriesFromOngoingTripWithVehicleID(String VehID) {return dal.getLastThreeTripEntryFromTripinDB(VehID);};

    public Trip CreateTrip(String vehicleId, ZonedDateTime startTime, ZonedDateTime endTime, boolean currentlyOngoing)
    {
         return new Trip(vehicleId, startTime, endTime, currentlyOngoing);
    }
    //TODO: make this an easy create, but also add it too Trip class itself
    /*public Trip CreateTrip(String vehicleId, LocalDate startTime){
        return new Trip(vehicleId, startTime, );
    }*/

    public List<Trip> ReadTrips()
    {
        return trips;
    }

    public boolean UpdateTrips(Trip oldTrip, Trip newTrip) //replace 1 item
    {
        try
        {
            trips.remove(oldTrip);

            try //if can't add new trip re-add old trip
            {
                trips.add(newTrip);
            }
            catch(Exception ex)
            {
                trips.add(oldTrip);
                return false;
            }

            return true;
        }
        catch(Exception ex)
        {
            return false;
        }
    }

    public boolean DeleteTrip(Trip trip)
    {
        try
        {
            trips.remove(trip);
            return true;
        }
        catch(Exception ex)
        {
            return false;
        }
    }

    public boolean VehicleOnTrip(String vehicleID)
    {
        for(Trip trippu: trips )
        {
            if(trippu.getVehicleId().equals(vehicleID) && trippu.isCurrentlyOngoing())
            {
                return true;
            }
        }
        return false;
    }

    public Trip GetOngoingTripFromVehicleID(String vehicleID)
    {
        for(Trip trippu: trips )
        {
            if(trippu.getVehicleId().equals(vehicleID) && trippu.isCurrentlyOngoing())
            {
                return trippu;
            }
        }
        return null;
    }

    public List<Trip> GetPastTripsFromVehicleID(String vehicleID)
    {
        List<Trip> pastTrips=new LinkedList<Trip>();

        for( Trip trippu : trips)
        {
            if(trippu.getVehicleId().equals(vehicleID))
            {
                pastTrips.add(trippu);
            }
        }
        if(!pastTrips.isEmpty())
        {
            return pastTrips;
        }
        else
        {
            return null;
        }
    }

    public boolean AddToTripWithVehicleID(String vehicleID, TripEntry tripEntry)
    {
        for(Trip trippu: trips )
        {
            if(trippu.getVehicleId().equals(vehicleID) && trippu.isCurrentlyOngoing())
            {
                trippu.AddTripEntry(tripEntry);
                return true;
            }
        }
        return false;
    }

}
