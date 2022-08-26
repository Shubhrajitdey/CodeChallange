package com.geektrust.backend.commands;

import com.geektrust.backend.exceptions.MemberNotFoundException;
import com.geektrust.backend.services.IExpensesService;

import java.util.ArrayList;
import java.util.List;

public class SpendCommand implements ICommand{

    private final IExpensesService expensesService;

    public SpendCommand(IExpensesService expensesService) {
        this.expensesService = expensesService;
    }

    @Override
    public void execute(List<String> tokens) {
        List<String> spendfor=new ArrayList<String>();
        for(int i=3;i<tokens.size();i++)
            spendfor.add(tokens.get(i));
        try {
            expensesService.spendMoneyByMember(Double.valueOf(tokens.get(1)),tokens.get(2),spendfor);
            System.out.println("SUCCESS");
        } catch (MemberNotFoundException e) {
            System.out.println("MEMBER_NOT_FOUND");
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
