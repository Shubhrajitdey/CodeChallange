package com.geektrust.backend.commands;

import com.geektrust.backend.exceptions.MemberNotFoundException;
import com.geektrust.backend.exceptions.MoveoutFailureException;
import com.geektrust.backend.services.IHousematesService;

import java.util.List;

public class MoveOutCommand implements ICommand{

    private final IHousematesService housematesService;

    public MoveOutCommand(IHousematesService housematesService) {
        this.housematesService = housematesService;
    }

    @Override
    public void execute(List<String> tokens) {
        try {
            housematesService.moveOut(tokens.get(1));
            System.out.println("SUCCESS");
        } catch (MemberNotFoundException e) {
            System.out.println("MEMBER_NOT_FOUND");
        } catch (MoveoutFailureException e) {
            System.out.println("FAILURE");
        }  catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
