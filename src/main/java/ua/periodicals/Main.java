package ua.periodicals;

import ua.periodicals.model.Periodical;
import ua.periodicals.service.PeriodicalLogic;

public class Main {
    public static void main(String[] args) {
        PeriodicalLogic periodicalLogic = new PeriodicalLogic();

        Periodical periodical = new Periodical("Top Gear", "", 1230);

        System.out.println(periodicalLogic.findAll());
        System.out.println("Creating new..." + periodicalLogic.create(periodical));
        System.out.println(periodicalLogic.findAll());
        System.out.println("------");
        Periodical toUpdate = periodicalLogic.findById(1);
        System.out.println(toUpdate);
        toUpdate.setDescription("Best magazine about new releases");
        System.out.println(periodicalLogic.update(toUpdate));
        System.out.println("------");
        System.out.println(periodicalLogic.findAll());


    }
}
