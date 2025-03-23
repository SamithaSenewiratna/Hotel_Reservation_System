package service;

import service.custom.impl.*;
import util.ServiceType;

public class serviceFactory {
    private static serviceFactory instance;

    private serviceFactory(){}

    public static serviceFactory getInstance(){return instance==null?instance=new serviceFactory():instance;}



    public <T extends superService > T getServiceType(ServiceType servicetype) {

        switch (servicetype){

            case CUSTOMER :return (T) new CustomerServiceImpl();
            case USER:return (T)new UserServiceImpl();
            case ROOM:return (T) new RoomServiceImpl();
            case RESERVATION:return (T) new ResevationServiceImpl();
            case BILLING:return (T) new BillingServiceImpl();
           /*


            case ACTIVITY_LOGS:return (T) new ActivityLogServiceImpl();
            */

        }
        return null;

    }



}
