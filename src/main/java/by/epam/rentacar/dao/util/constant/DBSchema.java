package by.epam.rentacar.dao.util.constant;

/**
 * The class defines nested classes which means databases tables.
 */
public class DBSchema {

    /**
     * The class defines {@link String} constants containing column names of user_list table.
     */
    public static final class UserListTable {
        public static final String TABLE_NAME = "user_list";

        public static final String ID_USER = "id_user";
        public static final String USERNAME = "username";
        public static final String PASSWORD = "password";
        public static final String EMAIL = "email";
        public static final String NAME = "name";
        public static final String SURNAME = "surname";
        public static final String PHONE_NUMBER = "phone_number";
        public static final String PASSPORT = "passport";
        public static final String PHOTO = "photo";
    }

    /**
     * The class defines {@link String} constants containing column names of user_role table.
     */
    public static final class UserRoleTable {
        public static final String TABLE_NAME = "user_role";

        public static final String ID_ROLE = "id_role";
        public static final String ROLE_NAME = "role";
    }

    /**
     * The class defines {@link String} constants containing column names of car_list table.
     */
    public static final class CarListTable {
        public static final String TABLE_NAME = "car_list";

        public static final String ID_CAR = "id_car";
        public static final String BRAND = "brand";
        public static final String MODEL = "model";
        public static final String CLASS = "class";
        public static final String YEAR_OF_ISSUE = "year_of_issue";
        public static final String NUMBER_OF_SEATS = "number_of_seats";
        public static final String COLOR = "color";
        public static final String ENGINE_VOLUME = "engine_volume";
        public static final String IS_DELETED = "is_deleted";
        public static final String PRICE = "price";
    }

    /**
     * The class defines {@link String} constants containing column names of car_review table.
     */
    public static final class CarReviewTable {
        public static final String TABLE_NAME = "car_review";

        public static final String ID_REVIEW = "id_review";
        public static final String ID_CAR = "id_car";
        public static final String ID_USER = "id_user";
        public static final String REVIEW_TEXT = "review";
        public static final String DATE = "time";
    }

    /**
     * The class defines {@link String} constants containing column names of car_photos table.
     */
    public static final class CarPhotosTable {
        public static final String TABLE_NAME = "car_photos";

        public static final String ID_PHOTO = "id_photo";
        public static final String ID_CAR = "id_car";
        public static final String PHOTO_URL = "photo_url";
    }

    /**
     * The class defines {@link String} constants containing column names of order_list table.
     */
    public static final class OrderListTable {
        public static final String TABLE_NAME = "order_list";

        public static final String ID_ORDER = "id_order";
        public static final String ID_USER = "id_user";
        public static final String ID_CAR = "id_car";
        public static final String DATE_START = "date_start";
        public static final String DATE_END = "date_end";
        public static final String TOTAL_PRICE = "total_price";
        public static final String ID_STATUS = "id_status";
        public static final String COMMENT = "comment";
    }

    /**
     * The class defines {@link String} constants containing column names of order_status table.
     */
    public static final class OrderStatusTable {
        public static final String TABLE_NAME = "order_status";

        public static final String ID_STATUS = "id_status";
        public static final String STATUS = "status";
    }

    private DBSchema() {

    }
}
