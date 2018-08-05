package by.epam.rentacar.controller.command.user;

import by.epam.rentacar.controller.command.Command;
import by.epam.rentacar.domain.dto.EditProfileDTO;
import by.epam.rentacar.domain.entity.User;
import by.epam.rentacar.service.ServiceFactory;
import by.epam.rentacar.service.UserService;
import by.epam.rentacar.service.exception.ServiceException;
import by.epam.rentacar.controller.util.constant.PageParameters;
import by.epam.rentacar.controller.util.constant.RequestParameters;
import by.epam.rentacar.controller.util.constant.SessionAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CommandSaveProfile implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        HttpSession session = request.getSession();
        int userID = ((User) session.getAttribute(SessionAttributes.KEY_USER)).getId();

        String editName = request.getParameter(RequestParameters.KEY_EDIT_NAME);
        String editSurname = request.getParameter(RequestParameters.KEY_EDIT_SURNAME);
        String editPhone = request.getParameter(RequestParameters.KEY_EDIT_PHONE);
        String editPassport = request.getParameter(RequestParameters.KEY_EDIT_PASSPORT);

        EditProfileDTO editProfileDTO = new EditProfileDTO();
        editProfileDTO.setUserID(userID);
        editProfileDTO.setName(editName);
        editProfileDTO.setSurname(editSurname);
        editProfileDTO.setPhone(editPhone);
        editProfileDTO.setPassport(editPassport);

        UserService userService = ServiceFactory.getInstance().getUserService();

        try {

            if(userService.editProfile(editProfileDTO)) {
                request.setAttribute("profile_edited", true);
                request.getRequestDispatcher("/controller?command=profile").forward(request, response);
            }

        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }
}