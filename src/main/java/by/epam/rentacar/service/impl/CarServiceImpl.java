package by.epam.rentacar.service.impl;

import by.epam.rentacar.dao.CarDAO;
import by.epam.rentacar.dao.DAOFactory;
import by.epam.rentacar.dao.ReviewDAO;
import by.epam.rentacar.dao.TransactionHelper;
import by.epam.rentacar.dao.exception.DAOException;
import by.epam.rentacar.dao.impl.CarDAOImpl;
import by.epam.rentacar.dao.impl.ReviewDAOImpl;
import by.epam.rentacar.domain.dto.CarSearchDTO;
import by.epam.rentacar.domain.dto.FindCarsDTO;
import by.epam.rentacar.domain.entity.Car;
import by.epam.rentacar.service.CarService;
import by.epam.rentacar.service.exception.ServiceException;
import by.epam.rentacar.service.util.PageCounter;
import com.sun.corba.se.spi.transport.TransportDefault;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CarServiceImpl implements CarService {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");

    public List<Car> getAllCars(int page, int itemsPerPage) throws ServiceException {

        List<Car> carList = new ArrayList<>();

        CarDAO carDAO = new CarDAOImpl();
        TransactionHelper transactionHelper = null;

        try {
            transactionHelper = new TransactionHelper();
            transactionHelper.beginTransaction(carDAO);

            carList = carDAO.getAllCars();

            transactionHelper.commit();
        } catch (DAOException e) {
            transactionHelper.rollback();
            e.printStackTrace();
            throw new ServiceException("Could not find car list", e);
        } finally {
            transactionHelper.endTransaction();
        }

        return carList;
    }

    @Override
    public List<Car> getAllNotDeletedCars(int page, int itemsPerPage) throws ServiceException {

        List<Car> carList = new ArrayList<>();

        CarDAO carDAO = new CarDAOImpl();
        TransactionHelper transactionHelper = null;

        try {
            transactionHelper = new TransactionHelper();
            transactionHelper.beginTransaction(carDAO);

            carList = carDAO.getAllNotDeleted(page, itemsPerPage);

            transactionHelper.commit();
        } catch (DAOException e) {
            transactionHelper.rollback();
            e.printStackTrace();
            throw new ServiceException("Could not find car list", e);
        } finally {
            transactionHelper.endTransaction();
        }

        return carList;

    }


    @Override
    public int getCarsPagesCount(int itemsPerPage) throws ServiceException {

        int pagesCount = 0;

        CarDAO carDAO = new CarDAOImpl();
        TransactionHelper transactionHelper = null;

        try {
            transactionHelper = new TransactionHelper();
            transactionHelper.beginTransaction(carDAO);

            int carsCount = carDAO.getTotalCount();
            pagesCount = PageCounter.getInstance().countPages(carsCount, itemsPerPage);

            transactionHelper.commit();

        } catch (DAOException e) {
            transactionHelper.rollback();
            throw new ServiceException("Error while counting cars pages!", e);
        } finally {
            transactionHelper.endTransaction();
        }

        return pagesCount;

    }

    @Override
    public List<Car> getCarsByDateRangeAndClass(FindCarsDTO findCarsDTO, int page, int itemsPerPage) throws ServiceException {

        String carClass = findCarsDTO.getCarClass();

        Date dateStart = null;
        Date dateEnd = null;

        try {
            dateStart = dateFormat.parse(findCarsDTO.getDateStart());
            dateEnd = dateFormat.parse(findCarsDTO.getDateEnd());
        } catch (ParseException e) {
            e.printStackTrace();
            throw new ServiceException("Error occurred while parsing date");
        }

        List<Car> cars = new ArrayList<>();

        CarDAO carDAO = new CarDAOImpl();
        TransactionHelper transactionHelper = null;

        try {
            transactionHelper = new TransactionHelper();
            transactionHelper.beginTransaction(carDAO);

            cars = (carClass == null || carClass.isEmpty()) ? carDAO.getAllByDateRange(dateStart, dateEnd, page, itemsPerPage) :
                                                             carDAO.getAllByDateRangeAndClass(carClass, dateStart, dateEnd, page, itemsPerPage);

            transactionHelper.commit();

        } catch (DAOException e) {
            transactionHelper.rollback();
            throw new ServiceException("Error while getting cars by date range", e);
        } finally {
            transactionHelper.endTransaction();
        }

        return cars;

    }

    @Override
    public int getCarsPagesCount(FindCarsDTO findCarsDTO, int itemsPerPage) throws ServiceException {

        String carClass = findCarsDTO.getCarClass();

        Date dateStart = null;
        Date dateEnd = null;

        try {
            dateStart = dateFormat.parse(findCarsDTO.getDateStart());
            dateEnd = dateFormat.parse(findCarsDTO.getDateEnd());
        } catch (ParseException e) {
            e.printStackTrace();
            throw new ServiceException("Error occurred while parsing date");
        }

        int pagesCount = 0;

        CarDAO carDAO = new CarDAOImpl();
        TransactionHelper transactionHelper = null;

        try {
            transactionHelper = new TransactionHelper();
            transactionHelper.beginTransaction(carDAO);

            int carsCount = (carClass == null || carClass.isEmpty()) ? carDAO.getTotalCountByDateRange(dateStart, dateEnd) :
                                                                       carDAO.getTotalCountByDateRangeAndClass(carClass, dateStart, dateEnd);
            pagesCount = PageCounter.getInstance().countPages(carsCount, itemsPerPage);

            transactionHelper.commit();

        } catch (DAOException e) {
            transactionHelper.rollback();
            throw new ServiceException("Error while getting count of cars pages", e);
        } finally {
            transactionHelper.endTransaction();
        }

        return pagesCount;

    }

    public Car getCar(int carID) throws ServiceException {

        Car car = null;

        CarDAO carDAO = new CarDAOImpl();
        ReviewDAO reviewDAO = new ReviewDAOImpl();
        TransactionHelper transactionHelper = null;

        try {
            transactionHelper = new TransactionHelper();
            transactionHelper.beginTransaction(carDAO, reviewDAO);

            car = carDAO.getCarByID(carID);
            car.setReviewList(reviewDAO.getCarReviews(carID));

            transactionHelper.commit();
        } catch (DAOException e) {
            transactionHelper.rollback();
            e.printStackTrace();
            throw new ServiceException("Could not find the car", e);
        } finally {
            transactionHelper.endTransaction();
        }

        return car;
    }

    @Override
    public void deleteCar(int carID) throws ServiceException {

        CarDAO carDAO = new CarDAOImpl();
        TransactionHelper transactionHelper = null;

        try {
            transactionHelper = new TransactionHelper();
            transactionHelper.beginTransaction(carDAO);

            carDAO.deleteCar(carID);

            transactionHelper.commit();

        } catch (DAOException e) {
            transactionHelper.rollback();
            e.printStackTrace();
            throw new ServiceException("Error while deleting car", e);
        } finally {
            transactionHelper.endTransaction();
        }

    }

    @Override
    public List<Car> getCarsByFilter(CarSearchDTO carSearchDTO) throws ServiceException {

        List<Car> carList = new ArrayList<>();

        CarDAO carDAO = new CarDAOImpl();
        TransactionHelper transactionHelper = null;

        try {
            transactionHelper = new TransactionHelper();
            transactionHelper.beginTransaction(carDAO);

            carList = carDAO.getCarsByFilter(carSearchDTO);

            transactionHelper.commit();
        } catch (DAOException e) {
            transactionHelper.rollback();
            e.printStackTrace();
            throw new ServiceException("Could not find cars by filter");
        } finally {
            transactionHelper.endTransaction();
        }

        return carList;
    }

    @Override
    public void addPhoto(int carID, String filename) throws ServiceException {

        CarDAO carDAO = new CarDAOImpl();
        TransactionHelper transactionHelper = null;

        try {
            transactionHelper = new TransactionHelper();
            transactionHelper.beginTransaction(carDAO);

            carDAO.addPhoto(carID, filename);

            transactionHelper.commit();

        } catch (DAOException e) {
            transactionHelper.rollback();
            throw new ServiceException("error while adding car photo", e);
        } finally {
            transactionHelper.endTransaction();
        }

    }

    @Override
    public void deletePhoto(int photoID) throws ServiceException {

        CarDAO carDAO = new CarDAOImpl();
        TransactionHelper transactionHelper = null;

        try {
            transactionHelper = new TransactionHelper();
            transactionHelper.beginTransaction(carDAO);

            carDAO.deletePhoto(photoID);

            transactionHelper.commit();

        } catch (DAOException e) {
            transactionHelper.rollback();
            e.printStackTrace();
            throw new ServiceException("Error while deleting car photo", e);
        } finally {
            transactionHelper.endTransaction();
        }

    }

}
