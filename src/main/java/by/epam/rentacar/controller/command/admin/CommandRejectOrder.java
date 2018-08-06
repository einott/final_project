package by.epam.rentacar.controller.command.admin;

import by.epam.rentacar.controller.command.Command;
import by.epam.rentacar.controller.util.constant.RequestHeader;
import by.epam.rentacar.controller.util.constant.RequestParameters;
import by.epam.rentacar.service.AdminService;
import by.epam.rentacar.service.ServiceFactory;
import by.epam.rentacar.service.exception.ServiceException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CommandRejectOrder implements Command {

    private static final Logger logger = LogManager.getLogger(CommandRejectOrder.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        int orderID = Integer.parseInt(request.getParameter(RequestParameters.KEY_ID_ORDER));

        AdminService adminService = ServiceFactory.getInstance().getAdminService();

        try {
            adminService.rejectOrder(orderID);

            String destPage = request.getHeader(RequestHeader.KEY_REFERER);
            response.sendRedirect(destPage);
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "Failed to reject order!", e);
        }

    }
}
