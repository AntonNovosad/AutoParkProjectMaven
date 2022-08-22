package by.devincubator.vehicle.collection;

import by.devincubator.entity.Rents;
import by.devincubator.entity.Types;
import by.devincubator.entity.Vehicles;
import by.devincubator.infrastructure.core.annotations.Autowired;
import by.devincubator.infrastructure.core.annotations.InitMethod;
import by.devincubator.parser.ParserVehicleInterface;
import by.devincubator.vehicle.service.MechanicService;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class VehicleCollection {
    private List<Types> vehicleTypes = new ArrayList<>();
    private List<Vehicles> vehicleList = new ArrayList<>();
    private List<Rents> rentList = new ArrayList<>();
    @Autowired
    private ParserVehicleInterface parser;

    public VehicleCollection() {
    }

    @InitMethod
    public void init() {
        vehicleTypes = parser.loadTypes();
        rentList = parser.loadRents();
        vehicleList = parser.loadVehicles();
    }

    public List<Vehicles> getListBrokenVehicle(MechanicService mechanicService) {
        return vehicleList
                .stream()
                .filter(x -> !mechanicService.detectBreaking(x).isEmpty())
                .collect(Collectors.toList());
    }

    public void insert(int index, Vehicles v) {
        if (isIndex(index)) {
            vehicleList.add(index, v);
        } else {
            vehicleList.add(v);
        }
    }

    private boolean isIndex(int index) {
        return 0 <= index && index <= vehicleList.size();
    }
}
