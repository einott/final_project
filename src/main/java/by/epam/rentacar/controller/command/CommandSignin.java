package by.epam.rentacar.controller.command;

import by.epam.rentacar.controller.util.PathHelper;
import by.epam.rentacar.domain.dto.SigninDTO;
import by.epam.rentacar.domain.entity.User;
import by.epam.rentacar.service.ServiceFactory;
import by.epam.rentacar.service.UserService;
import by.epam.rentacar.service.exception.InvalidInputDataException;
import by.epam.rentacar.service.exception.ServiceException;
import by.epam.rentacar.controller.util.constant.PageParameters;
import by.epam.rentacar.controller.util.constant.RequestParameters;
import by.epam.rentacar.controller.util.constant.SessionAttributes;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


/**
 * The command for logging in the user.
 */
public class CommandSignin implements Command {

    private static final Logger logger = LogManager.getLogger(CommandSignin.class);

    /**
     * The result messages of the command execution.
     */
    private static final String MESSAGE_INVALID_DATA = "local.signin.error.invalid-data";
    private static final String MESSAGE_INCORRECT_DATA = "local.signin.error.incorrect-data";

    /**
     * Gets username and passwords from the request. Then processing this data by service layer.
     * If data is valid and sign in successful, redirects to the main page.
     * If not, again redirects to the sign in page with appropriate error message.
     * @param request
     *          an {@link HttpServletRequest} object that contains client request.
     * @param response
     *          an {@link HttpServletResponse} object that contains the response the servlet sends to the client.
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        SigninDTO signinDTO = parseRequest(request);
        UserService userService = ServiceFactory.getInstance().getUserService();
        User user = null;

        String destPage = PageParameters.PAGE_SIGNIN;
        HttpSession session = request.getSession();

        try {
            user = userService.login(signinDTO);

            if (user != null) {
                int userID = user.getId();
                User.Role role = user.getRole();

                session.setAttribute(SessionAttributes.KEY_ID_USER, userID);
                session.setAttribute(SessionAttributes.KEY_ROLE, role);

                destPage = defineDestPage(role);
            } else {
                session.setAttribute(SessionAttributes.KEY_ERROR_MESSAGE, MESSAGE_INCORRECT_DATA);
            }

        } catch (InvalidInputDataException e) {
            session.setAttribute(SessionAttributes.KEY_ERROR_MESSAGE, MESSAGE_INVALID_DATA);
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "Signing in failed!", e);
            destPage = PageParameters.PAGE_ERROR;
        }

        response.sendRedirect(destPage);

    }

    /**
     * Parsing the request to get sign in data.
     * @return {@link SigninDTO} object, that contains user input data.
     */
    private SigninDTO parseRequest(HttpServletRequest request) {

        String username = request.getParameter(RequestParameters.KEY_USERNAME);
        String password = request.getParameter(RequestParameters.KEY_PASSWORD);

        SigninDTO signinDTO = new SigninDTO();
        signinDTO.setUsername(username);
        signinDTO.setPassword(password);

        return signinDTO;

    }

    /**
     * Depending on user role returns destination page.
     * @return if role is admin - admin page, if not - main page.
     */
    private String defineDestPage(User.Role role) {
        return (role == User.Role.ADMIN) ? PageParameters.PAGE_ADMIN_PANEL : PageParameters.PAGE_MAIN;
    }
}
