package by.epam.rentacar.service.impl;

import by.epam.rentacar.dao.DAOFactory;
import by.epam.rentacar.dao.TransactionHelper;
import by.epam.rentacar.dao.UserDAO;
import by.epam.rentacar.dao.exception.DAOException;
import by.epam.rentacar.domain.dto.ChangePasswordDTO;
import by.epam.rentacar.domain.dto.SigninDTO;
import by.epam.rentacar.domain.dto.SignupDTO;
import by.epam.rentacar.domain.entity.User;
import by.epam.rentacar.service.UserService;
import by.epam.rentacar.service.exception.*;
import by.epam.rentacar.service.validation.Validator;
import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

public class UserServiceImpl implements UserService {

    private static final DAOFactory daoFactory = DAOFactory.getInstance();
    private static final UserDAO userDAO = daoFactory.getUserDAO();

    public User login(SigninDTO signinDTO) throws ServiceException {

        if(!Validator.isSigninDataValid(signinDTO)) {
            throw new InvalidInputDataException("Invalid sign in data!");
        }

        TransactionHelper transactionHelper = null;
        User user;

        try {

            transactionHelper = new TransactionHelper();
            transactionHelper.beginTransaction(userDAO);

            String username = signinDTO.getUsername();
            String hashedPassword = hashPassword(signinDTO.getPassword());
            user = userDAO.findUserByUsernameAndPassword(username, hashedPassword);

            transactionHelper.commit();

        } catch (DAOException e) {
            transactionHelper.rollback();
            throw new ServiceException("Could not login user", e);
        } finally {
            transactionHelper.endTransaction();
        }

        return user;
    }


    public User signup(SignupDTO signupDTO) throws ServiceException {

        String password = signupDTO.getPassword();
        String confirmPassword = signupDTO.getConfirmPassword();

        if(!Validator.isSignupDataValid(signupDTO)) {
            throw new InvalidInputDataException("Invalid sign up data!");
        }

        if(!Validator.isPasswordsEqual(password, confirmPassword)) {
            throw new PasswordsNotEqualException("Passwords are not equal!");
        }

        TransactionHelper transactionHelper = null;
        User user;

        try {

            transactionHelper = new TransactionHelper();
            transactionHelper.beginTransaction(userDAO);

            String username = signupDTO.getUsername();
            String email = signupDTO.getEmail();

            if (userDAO.isUsernameAlreadyExists(username)) {
                throw new UsernameAlreadyExistsException("Username is already exists!");
            }

            if (userDAO.isEmailAlreadyExists(email)) {
                throw new EmailAlreadyExistsException("Email is already exists!");
            }

            String hashedPassword = hashPassword(password);

            userDAO.add(username, email, hashedPassword);
            user = userDAO.findUserByUsername(username);

            transactionHelper.commit();

        } catch (DAOException e) {
            transactionHelper.rollback();
            throw new ServiceException("Could not sign up user", e);
        } finally {
            transactionHelper.endTransaction();
        }

        return user;
    }

    @Override
    public User getUser(int userID) throws ServiceException {

        User user = null;
        TransactionHelper transactionHelper = null;

        try {
            transactionHelper = new TransactionHelper();
            transactionHelper.beginTransaction(userDAO);

            user = userDAO.getByID(userID);

            transactionHelper.commit();
        } catch (DAOException e) {
            transactionHelper.rollback();
            throw new ServiceException("Error while getting user data", e);
        } finally {
            transactionHelper.endTransaction();
        }

        return user;

    }

    public void editProfile(User user) throws ServiceException {

        if (!Validator.isEditProfileDataValid(user)) {
            throw new InvalidInputDataException("Invalid edit profile data!");
        }

        TransactionHelper transactionHelper = null;

        try {

            transactionHelper = new TransactionHelper();
            transactionHelper.beginTransaction(userDAO);

            userDAO.update(user);

            transactionHelper.commit();

        } catch (DAOException e) {
            transactionHelper.rollback();
            throw new ServiceException("Could not update user info", e);
        } finally {
            transactionHelper.endTransaction();
        }

    }

    @Override
    public void changePassword(ChangePasswordDTO changePasswordDTO) throws ServiceException {

        String previousPassword = changePasswordDTO.getPreviousPassword();
        String newPassword = changePasswordDTO.getNewPassword();
        if (!Validator.isPasswordValid(previousPassword) || !Validator.isPasswordValid(newPassword)) {
            throw new InvalidInputDataException("Password is invalid");
        }

        String confirmNewPassword = changePasswordDTO.getConfirmPassword();
        if (!Validator.isPasswordsEqual(newPassword, confirmNewPassword)) {
            throw new PasswordsNotEqualException("Passwords are not equal!");
        }

        TransactionHelper transactionHelper = null;

        try {
            transactionHelper = new TransactionHelper();
            transactionHelper.beginTransaction(userDAO);

            int userID = changePasswordDTO.getUserID();
            String hashedPreviousPassword = hashPassword(previousPassword);

            if (!userDAO.isCorrectPassword(userID, hashedPreviousPassword)) {
                throw new InvalidInputDataException("Password isn't correct for this user!");
            }

            String hashedNewPassword = hashPassword(newPassword);
            userDAO.changePassword(userID, hashedNewPassword);

            transactionHelper.commit();

        } catch (DAOException e) {
            transactionHelper.rollback();
            throw new ServiceException("Error while changing password!", e);
        } finally {
            transactionHelper.endTransaction();
        }
    }

    @Override
    public void setPhoto(int userID, String filename) throws ServiceException {

        if(!Validator.isNotEmpty(filename)) {
            throw new InvalidInputDataException("Filename is empty!");
        }

        TransactionHelper transactionHelper = null;

        try {
            transactionHelper = new TransactionHelper();
            transactionHelper.beginTransaction(userDAO);

            userDAO.setPhoto(userID, filename);

            transactionHelper.commit();
        } catch (DAOException e) {
            transactionHelper.rollback();
            e.printStackTrace();
            throw new ServiceException("Error while updating user photo", e);
        } finally {
            transactionHelper.endTransaction();
        }

    }

    private String hashPassword(String password) {
        return Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
    }
}
