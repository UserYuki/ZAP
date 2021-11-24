package API;

import Backend.Classes.Trip;
import Backend.Interfaces.ITripContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/Trips")
@Service

public class TripController {

    @Autowired
    ITripContainer dal;

    @GetMapping
    public ResponseEntity<List<Trip>> getAllTrips(){
        List<Trip> test = null;
        List<Trip> temp = new ArrayList<>();

        //test = dal.dbgetAllTrips();
        test = dal.dbFetchAllTripSummaries();

        System.out.println(test.size());
//
//        for ( Trip a : test ) {
//            temp.add(new Trip(a.getVehicleId(),a.getStartTime(),a.getEndTime(),a.isCurrentlyOngoing(),"s"));
//        }
        if(test != null){
            return ResponseEntity.ok().body(test);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

}
