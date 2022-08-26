package com.geektrust.backend.commands;

import com.geektrust.backend.entities.Owes;
import com.geektrust.backend.exceptions.MemberNotFoundException;
import com.geektrust.backend.services.IExpensesService;

import java.util.List;

public class DuesCommand implements ICommand{

    private final IExpensesService expensesService;

    public DuesCommand(IExpensesService expensesService) {
        this.expensesService = expensesService;
    }

    @Override
    public void execute(List<String> tokens) {
        try {
            List<Owes> owesList=expensesService.showAllDuesOfMember(tokens.get(1));
            owesList.stream().sorted((o1,o2)->{
                if(Double.compare(o2.getAmountOwes(),o1.getAmountOwes())==0)
                {
                    return o1.getPerson().getName().compareTo(o2.getPerson().getName());
                }else {
                    return Double.compare(o2.getAmountOwes(),o1.getAmountOwes());
                }
            }).forEach( o->
                    System.out.println(o.getPerson().getName()+" "+o.getAmountOwes().intValue())
            );
        } catch (MemberNotFoundException e) {
            System.out.println("MEMBER_NOT_FOUND");
        }  catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
