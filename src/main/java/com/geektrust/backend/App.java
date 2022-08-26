package com.geektrust.backend;

import com.geektrust.backend.appConfig.ApplicationConfig;
import com.geektrust.backend.commands.CommandInvoker;
import com.geektrust.backend.exceptions.NoSuchCommandException;
import com.geektrust.backend.repositories.HousematesRepository;
import com.geektrust.backend.repositories.IHousematesRepository;
import com.geektrust.backend.repositories.IMemberOwesRepository;
import com.geektrust.backend.repositories.MemberOwesRepository;
import com.geektrust.backend.services.ExpenseService;
import com.geektrust.backend.services.HousematesService;
import com.geektrust.backend.services.IExpensesService;
import com.geektrust.backend.services.IHousematesService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class App {

	public static void main(String[] args) {
		List<String> commandLineArgs = new LinkedList<>(Arrays.asList(args));
		run(commandLineArgs);
//		IHousematesRepository housematesRepository=new HousematesRepository();
//		IMemberOwesRepository memberOwesRepository=new MemberOwesRepository(housematesRepository);
//		IHousematesService housematesService=new HousematesService(housematesRepository,memberOwesRepository,3);
//		IExpensesService expensesService=new ExpenseService(memberOwesRepository,housematesRepository);
//		System.out.println(housematesService.moveIn("ANDY"));
//		System.out.println(housematesService.moveIn("WOODY"));
//		System.out.println(housematesService.moveIn("BO"));
//		//System.out.println(memberOwesRepository.findAll());
//		System.out.println(expensesService.spendMoneyByMember(6000.0,"WOODY", Arrays.asList("ANDY","BO")));
//		//System.out.println(expensesService.spendMoneyByMember(300.0,"WOODY ", Arrays.asList("BO")));
//
//		//System.out.println(memberOwesRepository.findAll());
//		//System.out.println(expensesService.clearDuesOfMember("WOODY","ANDY",1000.0));
//		System.out.println(expensesService.spendMoneyByMember(6000.0,"ANDY", Arrays.asList("BO")));
//		System.out.println(expensesService.showAllDuesOfMember("ANDY"));
//		System.out.println(expensesService.showAllDuesOfMember("BO"));
//		//System.out.println(housematesService.moveOut("WOODY"));
//		//System.out.println(housematesRepository.findAll());
//		System.out.println(memberOwesRepository.findAll());
	}
	public static void run(List<String> commandLineArgs) {
		ApplicationConfig applicationConfig = new ApplicationConfig();
		CommandInvoker commandInvoker = applicationConfig.getCommandInvoker();
		BufferedReader reader;
		String inputFile = commandLineArgs.get(0);
		try {
			reader = new BufferedReader(new FileReader(inputFile));
			String line = reader.readLine();
			while (line != null) {
				List<String> tokens = Arrays.asList(line.split(" "));
				commandInvoker.executeCommand(tokens.get(0),tokens);
				// read next line
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException | NoSuchCommandException e) {
			e.printStackTrace();
		}

	}

}
