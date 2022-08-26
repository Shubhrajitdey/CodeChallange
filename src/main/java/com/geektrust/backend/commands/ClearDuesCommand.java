package com.geektrust.backend.commands;

import com.geektrust.backend.exceptions.IncorrectPaymentException;
import com.geektrust.backend.exceptions.MemberNotFoundException;
import com.geektrust.backend.services.IExpensesService;

import java.util.List;

public class ClearDuesCommand implements ICommand{

    private final IExpensesService expensesService;

    public ClearDuesCommand(IExpensesService expensesService) {
        this.expensesService = expensesService;
    }

    @Override
    public void execute(List<String> tokens) {
        try {
            Double reamingAmount=expensesService.clearDuesOfMember(tokens.get(1),tokens.get(2),Double.valueOf(tokens.get(3)));
            System.out.println(reamingAmount.intValue());
        } catch (MemberNotFoundException e) {
            System.out.println("MEMBER_NOT_FOUND");
        } catch (IncorrectPaymentException e) {
            System.out.println("INCORRECT_PAYMENT");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
