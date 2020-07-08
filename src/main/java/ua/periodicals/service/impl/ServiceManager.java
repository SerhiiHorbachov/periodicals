package ua.periodicals.service.impl;

import ua.periodicals.database.ConnectionManager;
import ua.periodicals.database.ConnectionManagerImpl;
import ua.periodicals.service.InvoiceService;
import ua.periodicals.service.PeriodicalService;
import ua.periodicals.service.UserService;

/**
 * Service Manager.
 * All Service classes should be instantiated and configured in this class.
 *
 * @author Serhii Hor
 */
public class ServiceManager {

    private static ServiceManager serviceManager;
    private ConnectionManager connectionManager;

    private ServiceManager() {
        this.connectionManager = new ConnectionManagerImpl();
    }

    public static synchronized ServiceManager getInstance() {
        if (serviceManager == null) {
            serviceManager = new ServiceManager();
        }

        return serviceManager;
    }

    /**
     * @return InvoiceService
     */
    public InvoiceService getInvoiceService() {
        return new InvoiceServiceImpl(connectionManager);
    }

    /**
     * @return PeriodicalService
     */
    public PeriodicalService getPeriodicalService() {
        return new PeriodicalServiceImpl(connectionManager);
    }

    /**
     * @return UserService
     */
    public UserService getUserService() {
        return new UserServiceImpl(connectionManager);
    }

}
