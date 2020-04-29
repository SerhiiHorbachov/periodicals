package ua.periodicals.command.impl.admin;

import ua.periodicals.command.ActionCommand;
import ua.periodicals.command.NextPage;
import ua.periodicals.model.Periodical;
import ua.periodicals.service.impl.PeriodicalLogicImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ListPeriodicals implements ActionCommand {
    
    @Override
    public NextPage execute(HttpServletRequest request) {

        PeriodicalLogicImpl periodicalLogicImpl = new PeriodicalLogicImpl();
        List<Periodical> periodicals = periodicalLogicImpl.findAll();

        request.setAttribute("periodicals", periodicals);

        return new NextPage("admin/periodicals.jsp", "FORWARD");
    }
}
