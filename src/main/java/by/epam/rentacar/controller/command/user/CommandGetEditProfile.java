package by.epam.rentacar.controller.command.user;

import by.epam.rentacar.controller.command.Command;
import by.epam.rentacar.controller.util.constant.PageParameters;
import by.epam.rentacar.controller.util.constant.RequestAttributes;
import by.epam.rentacar.controller.util.constant.SessionAttributes;
import by.epam.rentacar.domain.entity.User;
import by.epam.rentacar.service.ServiceFactory;
import by.epam.rentacar.service.UserService;
import by.epam.rentacar.service.exception.ServiceException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The command for getting edit profile page.
 */
public class CommandGetEditProfile extends UserCommand {

    private static final Logger logger = LogManager.getLogger(CommandGetEditProfile.class);

    /**
     * Gets {@code userID} from the session and then gets user data from the
     * service layer. If some error occurs, redirects to the error page.
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

        if(!identifyUser(request)) {
            response.sendRedirect(PageParameters.PAGE_SIGNIN);
            return;
        }

        int userID = (int) request.getSession().getAttribute(SessionAttributes.KEY_ID_USER);

        UserService userService = ServiceFactory.getInstance().getUserService();

        try {

            User user = userService.getUser(userID);

            request.setAttribute(RequestAttributes.KEY_USER_DATA, user);
            request.getRequestDispatcher(PageParameters.PAGE_EDIT_PROFILE).forward(request, response);
            return;

        } catch (ServiceException e) {
            logger.log(Level.ERROR, "Failed to get edit profile page!", e);
            response.sendRedirect(PageParameters.PAGE_ERROR);
        }
    }
}
