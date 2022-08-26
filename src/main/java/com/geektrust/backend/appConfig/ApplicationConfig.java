package com.geektrust.backend.appConfig;

import com.geektrust.backend.commands.*;
import com.geektrust.backend.repositories.HousematesRepository;
import com.geektrust.backend.repositories.IHousematesRepository;
import com.geektrust.backend.repositories.IMemberOwesRepository;
import com.geektrust.backend.repositories.MemberOwesRepository;
import com.geektrust.backend.services.ExpenseService;
import com.geektrust.backend.services.HousematesService;
import com.geektrust.backend.services.IExpensesService;
import com.geektrust.backend.services.IHousematesService;

public class ApplicationConfig {

    private final Integer maxMemberInHouse=3;

    private  final IHousematesRepository housematesRepository=new HousematesRepository();
    private final IMemberOwesRepository memberOwesRepository=new MemberOwesRepository(housematesRepository);

    private final IExpensesService expensesService=new ExpenseService(memberOwesRepository,housematesRepository);
    private final IHousematesService housematesService=new HousematesService(housematesRepository,memberOwesRepository,maxMemberInHouse);

    private final MoveInCommand moveInCommand=new MoveInCommand(housematesService);

    private final SpendCommand spendCommand=new SpendCommand(expensesService);

    private final DuesCommand duesCommand=new DuesCommand(expensesService);

    private final ClearDuesCommand clearDuesCommand=new ClearDuesCommand(expensesService);

    private final MoveOutCommand moveOutCommand=new MoveOutCommand(housematesService);



    private final CommandInvoker commandInvoker = new CommandInvoker();

    public CommandInvoker getCommandInvoker(){
        commandInvoker.register("MOVE_IN",moveInCommand);
        commandInvoker.register("SPEND",spendCommand);
        commandInvoker.register("DUES",duesCommand);
        commandInvoker.register("CLEAR_DUE",clearDuesCommand);
        commandInvoker.register("MOVE_OUT",moveOutCommand);
        return commandInvoker;
    }
}
