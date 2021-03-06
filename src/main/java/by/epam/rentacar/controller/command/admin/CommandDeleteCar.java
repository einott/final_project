package by.epam.rentacar.controller.command.admin;

import by.epam.rentacar.controller.util.constant.PageParameters;
import by.epam.rentacar.controller.util.constant.RequestParameters;
import by.epam.rentacar.service.CarService;
import by.epam.rentacar.service.ServiceFactory;
import by.epam.rentacar.service.exception.ServiceException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The command for deleting car.
 */
public class CommandDeleteCar extends AdminCommand {

    private static final Logger logger = LogManager.getLogger(CommandDeleteCar.class);

    private static final String PAGE_CARS = "/controller?command=show_car_table";

    /**
     * Gets the id of the car which administrator wants to delete. Then processing it by
     * the service layer.
     * If data is valid redirects to the cars page.
     * If not redirect to the error page.
     *
     * @param request
     *          an {@link HttpServletRequest} object that contains client request
     * @param response
     *          an {@link HttpServletResponse} object that contains the response the servlet sends to the client
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        if(!identifyAdmin(request)) {
            response.sendRedirect(PageParameters.PAGE_MAIN);
            return;
        }

        int carID = Integer.parseInt(request.getParameter(RequestParameters.KEY_ID_CAR));

        CarService carService = ServiceFactory.getInstance().getCarService();
        String destPage;

        try {
            carService.deleteCar(carID);
            destPage = PAGE_CARS;
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "Failed to delete car!", e);
            destPage = PageParameters.PAGE_ERROR;
        }

        response.sendRedirect(destPage);

    }

}
