package repository;

import repository.custom.impl.CustomerDaoImpl;

import util.DaoType;


public class DaoFactory {

    private static DaoFactory instance;

    private DaoFactory(){}

    public static DaoFactory getInstance(){return instance==null?instance=new DaoFactory():instance;}

    public <T extends superDao> T getDaoType(DaoType daotype) {

        switch (daotype){

            case CUSTOMER :return (T) new CustomerDaoImpl();

           /* case RESERVATION:return (T) new ReservatonDaoImpl();
            case ROOM:return (T) new RoomDaoImpl();
            case USER:return (T)new UserDaoImpl();
            case ACTIVITY_LOGS:return (T) new ActivityLogDaoImpl();
            */

        }
        return null;

    }
}
