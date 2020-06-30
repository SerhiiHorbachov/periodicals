package ua.periodicals.service.impl;

import ua.periodicals.database.ConnectionManager;
import ua.periodicals.database.ConnectionManagerImpl;
import ua.periodicals.service.InvoiceService;
import ua.periodicals.service.PeriodicalService;
import ua.periodicals.service.UserService;

public class ServiceManager {

    private static ServiceManager serviceManager;

    private ServiceManager() {
        this.connectionManager = new ConnectionManagerImpl();
    }

    public static synchronized ServiceManager getInstance() {
        if (serviceManager == null) {
            serviceManager = new ServiceManager();
        }

        return serviceManager;
    }

    ConnectionManager connectionManager;

    public InvoiceService getInvoiceService() {
        return new InvoiceServiceImpl(connectionManager);
    }

    public PeriodicalService getPeriodicalService() {
        return new PeriodicalServiceImpl(connectionManager);
    }

    public UserService getUserService() {
        return new UserServiceImpl(connectionManager);
    }


}
