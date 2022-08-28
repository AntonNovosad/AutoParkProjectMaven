package by.devincubator.vehicle.collection;

import by.devincubator.entity.Rents;
import by.devincubator.entity.Types;
import by.devincubator.entity.Vehicles;
import by.devincubator.infrastructure.core.annotations.Autowired;
import by.devincubator.infrastructure.core.annotations.InitMethod;
import by.devincubator.parser.ParserRentFromFile;
import by.devincubator.parser.ParserTypeFromFile;
import by.devincubator.parser.ParserVehicleFromFile;
import by.devincubator.parser.ParserVehicleInterface;
import by.devincubator.service.OrdersService;
import by.devincubator.service.RentsService;
import by.devincubator.service.TypesService;
import by.devincubator.service.VehiclesService;
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
    @Autowired
    private ParserVehicleFromFile parserVehicleFromFile;
    @Autowired
    private ParserRentFromFile parserRentFromFile;
    @Autowired
    private ParserTypeFromFile parserTypeFromFile;
    @Autowired
    private OrdersService ordersService;
    @Autowired
    private RentsService rentsService;
    @Autowired
    private TypesService typesService;
    @Autowired
    private VehiclesService vehiclesService;

    public VehicleCollection() {
    }

    @InitMethod
    public void init() {
        vehicleList = parserVehicleFromFile.loadVehicles();
        rentList = parserRentFromFile.loadRents();
        vehicleTypes = parserTypeFromFile.loadTypes();
    }

    public void saveVehiclesFromFile() {
        for (Vehicles vehicles : vehicleList) {
            vehiclesService.save(vehicles);
        }
    }

    public void saveTypesFromFile() {
        for (Types types : vehicleTypes) {
            typesService.save(types);
        }
    }

    public void saveRentsFromFile() {
        for (Rents rents : rentList) {
            rentsService.save(rents);
        }
    }

    public List<Vehicles> getListVehicleFromDB() {
        return parser.loadVehicles();
    }

    public List<Types> getListTypesFromDB() {
        return parser.loadTypes();
    }

    public List<Rents> getListRentsFromDB() {
        return parser.loadRents();
    }

    public List<Vehicles> getListBrokenVehicle(MechanicService mechanicService) {
        return vehicleList.stream()
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
