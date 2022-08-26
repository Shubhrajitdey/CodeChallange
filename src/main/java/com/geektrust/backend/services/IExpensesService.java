package com.geektrust.backend.services;

import com.geektrust.backend.entities.Owes;
import com.geektrust.backend.exceptions.IncorrectPaymentException;
import com.geektrust.backend.exceptions.MemberNotFoundException;

import java.util.List;

public interface IExpensesService {
    public void spendMoneyByMember(Double moneySpend, String spendby, List<String> spendfor) throws MemberNotFoundException;
    public List<Owes> showAllDuesOfMember(String memberName) throws MemberNotFoundException;
    public Double clearDuesOfMember(String memberWhoOwes,String memberWhoLed,Double amount) throws MemberNotFoundException, IncorrectPaymentException;
}
