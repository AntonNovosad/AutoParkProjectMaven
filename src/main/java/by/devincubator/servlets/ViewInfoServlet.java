package by.devincubator.servlets;

import by.devincubator.infrastructure.core.impl.ApplicationContext;
import by.devincubator.service.RentsService;
import by.devincubator.service.VehiclesService;
import by.devincubator.vehicle.service.Fixer;
import by.devincubator.vehicle.service.MechanicService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet("/info")
public class ViewInfoServlet extends HttpServlet {
    private  VehicleTypeService vehicleTypeService;

    @Override
    public void init() throws ServletException {
        super.init();
        Map<Class<?>, Class<?>> interfaceToImplementation = new HashMap<>();
        interfaceToImplementation.put(Fixer.class, MechanicService.class);
        ApplicationContext applicationContext = new ApplicationContext("by", interfaceToImplementation);
        vehicleTypeService = applicationContext.getObject(VehicleTypeService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        req.setAttribute("cars", vehicleTypeService.getVehicles().stream()
                .filter(vehicleDto -> id == vehicleDto.getId())
                .collect(Collectors.toList()));
        req.setAttribute("rents", vehicleTypeService.getRent(id));
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/jsp/viewCarInfoJSP.jsp");
        dispatcher.forward(req, resp);
    }
}
