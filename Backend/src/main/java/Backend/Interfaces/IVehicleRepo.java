package Backend.Interfaces;

import Backend.Classes.Vehicle;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface IVehicleRepo extends MongoRepository<Vehicle, String> {
    Vehicle getVehicleByLplate(String newLPlate);
    Vehicle getVehicleById(String ID);
}
