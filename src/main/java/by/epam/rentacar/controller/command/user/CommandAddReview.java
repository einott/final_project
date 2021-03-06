package by.epam.rentacar.controller.command.user;

import by.epam.rentacar.controller.util.PathHelper;
import by.epam.rentacar.controller.util.constant.PageParameters;
import by.epam.rentacar.controller.util.constant.RequestHeader;
import by.epam.rentacar.controller.util.constant.SessionAttributes;
import by.epam.rentacar.domain.entity.Review;
import by.epam.rentacar.service.ServiceFactory;
import by.epam.rentacar.controller.util.constant.RequestParameters;
import by.epam.rentacar.service.exception.ServiceException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The command for adding new review.
 */
public class CommandAddReview extends UserCommand {

    private static final Logger logger = LogManager.getLogger(CommandAddReview.class);

    /**
     * Gets review content from the request. Then processing this data by service layer.
     * If data is valid redirects to the previous page. Otherwise redirects to the error page.
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

        Review review = parseRequest(request);
        String destPage = PathHelper.getPreviousPage(request);

        try {
            ServiceFactory.getInstance().getReviewService().addReview(review);
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "Failed to add review!", e);
            destPage = PageParameters.PAGE_ERROR;
        }

        response.sendRedirect(destPage);

    }

    /**
     * Parses the request to get an {@link Review} object.
     *
     * @return an {@link Review} object.
     */
    private Review parseRequest(HttpServletRequest request) {

        int userID = (int) request.getSession().getAttribute(SessionAttributes.KEY_ID_USER);
        int carID = Integer.parseInt(request.getParameter(RequestParameters.KEY_ID_CAR));
        String reviewText = request.getParameter(RequestParameters.KEY_REVIEW_TEXT);
        System.out.println(reviewText);

        Review review = new Review();
        review.setCarID(carID);
        review.setUserID(userID);
        review.setReviewText(reviewText);

        return review;

    }
}
