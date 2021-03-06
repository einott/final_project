package by.epam.rentacar.controller.command.admin;

import by.epam.rentacar.controller.command.Command;
import by.epam.rentacar.controller.util.PathHelper;
import by.epam.rentacar.controller.util.constant.PageParameters;
import by.epam.rentacar.controller.util.constant.RequestHeader;
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

public class CommandDeleteCarPhoto extends AdminCommand {

    private static final Logger logger = LogManager.getLogger(CommandDeleteCarPhoto.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        if(!identifyAdmin(request)) {
            response.sendRedirect(PageParameters.PAGE_MAIN);
            return;
        }

        int photoID = Integer.parseInt(request.getParameter(RequestParameters.KEY_ID_PHOTO));

        CarService carService = ServiceFactory.getInstance().getCarService();
        String destPage = PathHelper.getPreviousPage(request);

        try {
            carService.deletePhoto(photoID);
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "Failed to delete car photo", e);
            destPage = PageParameters.PAGE_ERROR;
        }

        response.sendRedirect(destPage);


    }
}
