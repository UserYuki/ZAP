package Backend.DatabaseAccess;

import Backend.Classes.Trip;
import Backend.Classes.TripEntry;

import java.util.List;
import java.util.Optional;

public interface ITripDAL
{

        List<Trip> getAllTripsfromDB();
        List<Trip> getAllTripswithoutTripEntriesfromDB();
        List<Trip> getAllTripswthoutTripEntriesfromDBwithOngoingStatus(boolean CurrentlyOngoing);
        Trip getTripbyIdinDB(String ID);
        Trip getTripbyVehicleIDinDB(String vehicleID);

        Trip getOngoingTripbyVehicleIDinDB(String vehicleID);

        List<TripEntry> getLastThreeTripEntryFromTripinDB(String VehicleID);


        void addTripinDB(Trip trip);


        void addEntryToActiveTripinDB(TripEntry entry, String VehicleID);
        void changeTripOngoingStatusinDB(boolean status, String tripID);

}