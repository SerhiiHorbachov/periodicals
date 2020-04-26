package ua.periodicals;

import ua.periodicals.model.Periodical;
import ua.periodicals.service.impl.PeriodicalLogicImpl;

public class Main {
    public static void main(String[] args) {
        PeriodicalLogicImpl periodicalLogicImpl = new PeriodicalLogicImpl();

        Periodical periodical = new Periodical("Top Gear", "", 1230);

        System.out.println(periodicalLogicImpl.findAll());
        System.out.println("Creating new..." + periodicalLogicImpl.create(periodical));
        System.out.println(periodicalLogicImpl.findAll());
        System.out.println("------");
        Periodical toUpdate = periodicalLogicImpl.findById(1);
        System.out.println(toUpdate);
        toUpdate.setDescription("Best magazine about new releases");
        System.out.println(periodicalLogicImpl.update(toUpdate));
        System.out.println("------");
        System.out.println(periodicalLogicImpl.findAll());


    }
}
