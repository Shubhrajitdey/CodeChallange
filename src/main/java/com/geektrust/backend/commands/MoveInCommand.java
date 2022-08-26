package com.geektrust.backend.commands;

import com.geektrust.backend.exceptions.HousefullException;
import com.geektrust.backend.services.IHousematesService;

import java.util.List;

public class MoveInCommand implements ICommand{

    private final IHousematesService housematesService;

    public MoveInCommand(IHousematesService housematesService) {
        this.housematesService = housematesService;
    }

    @Override
    public void execute(List<String> tokens) {
        try {
            housematesService.moveIn(tokens.get(1));
            System.out.println("SUCCESS");
        } catch (HousefullException e) {
            System.out.println("HOUSEFUL");
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
