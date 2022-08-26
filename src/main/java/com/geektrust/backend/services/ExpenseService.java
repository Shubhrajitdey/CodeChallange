package com.geektrust.backend.services;

import com.geektrust.backend.entities.Owes;
import com.geektrust.backend.exceptions.IncorrectPaymentException;
import com.geektrust.backend.exceptions.MemberNotFoundException;
import com.geektrust.backend.repositories.IHousematesRepository;
import com.geektrust.backend.repositories.IMemberOwesRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpenseService implements IExpensesService{

    private final IMemberOwesRepository memberOwesRepository;

    private final IHousematesRepository housematesRepository;

    //private final Double MIN_AMOUNT =0.0;

    public ExpenseService(IMemberOwesRepository memberOwesRepository, IHousematesRepository housematesRepository) {
        this.memberOwesRepository = memberOwesRepository;
        this.housematesRepository = housematesRepository;
    }

    private void adjustMoney(Double amount,String spendby, List<String> spendfor){
        List<Owes> owesListForSpendBy = memberOwesRepository.findListOfOwesByPersonName(spendby);
        HashMap<String,Owes> owesForSpendByMap=new HashMap<String, Owes>();
        for(Owes o:owesListForSpendBy)
            owesForSpendByMap.put(o.getPerson().getName(),o);
        for(String spendForMember:spendfor) {
            List<Owes> owesListSpendfor = memberOwesRepository.findListOfOwesByPersonName(spendForMember);
            for(Owes o:owesListSpendfor){
                if(o.getAmountOwes()>0){
                    if(o.getPerson().getName().equals(spendby) && owesForSpendByMap.get(spendForMember).getAmountOwes()>0){
                        Double owesAmountForSpendBy=owesForSpendByMap.get(spendForMember).getAmountOwes();
                        Double owesAmountForSpendFor=o.getAmountOwes();
                        if(owesAmountForSpendBy >= owesAmountForSpendFor){
                            owesForSpendByMap.get(spendForMember).setAmountOwes(owesAmountForSpendBy-owesAmountForSpendFor);
                            o.setAmountOwes(0.0);
                        }else{
                            o.setAmountOwes(owesAmountForSpendFor-owesAmountForSpendBy);
                            owesForSpendByMap.get(spendForMember).setAmountOwes(0.0);
                        }
                    }else{
                        if(owesForSpendByMap.containsKey(o.getPerson().getName()) && owesForSpendByMap.get(o.getPerson().getName()).getAmountOwes()>0){
                            Double owesAmountForSpendBy=owesForSpendByMap.get(o.getPerson().getName()).getAmountOwes();
                            Double owesAmountForSpendFor=o.getAmountOwes();
                            if(amount>=owesAmountForSpendBy){
                                owesForSpendByMap.get(o.getPerson().getName()).setAmountOwes(0.0);
                                o.setAmountOwes(owesAmountForSpendFor+owesAmountForSpendBy);
                                owesListSpendfor.stream().filter(s->s.getPerson().getName().equals(spendby)).findAny().get().setAmountOwes(amount-owesAmountForSpendBy);
                            }else{
                                owesForSpendByMap.get(o.getPerson().getName()).setAmountOwes(owesAmountForSpendBy-amount);
                                o.setAmountOwes(owesAmountForSpendFor+amount);
                                owesListSpendfor.stream().filter(s->s.getPerson().getName().equals(spendby)).findAny().get().setAmountOwes(0.0);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void spendMoneyByMember(Double moneySpend, String spendby, List<String> spendfor) throws MemberNotFoundException {
        if(!housematesRepository.isExitByMembername(spendby)) throw new MemberNotFoundException("MEMBER_NOT_FOUND");
        for(String name:spendfor){
            if(!housematesRepository.isExitByMembername(name)) throw new MemberNotFoundException("MEMBER_NOT_FOUND");
        }
        Integer memberCount=spendfor.size()+1;
        Double amountToBeSplit=moneySpend/memberCount;
        List<Owes> owesList=memberOwesRepository.findListOfOwesByPersonName(spendby);
        Map<String,List<Owes>> memberOwesMap = new HashMap<>();
        for(Owes owes:owesList){
            memberOwesMap.put(owes.getPerson().getName(),memberOwesRepository.findListOfOwesByPersonName(owes.getPerson().getName()));
        }
        for(String spendForMember:spendfor){
            Owes memberOwes = memberOwesMap.get(spendForMember).stream().filter((p)->p.getPerson().getName().equals(spendby)).findAny().get();
            memberOwes.setAmountOwes(memberOwes.getAmountOwes()+amountToBeSplit);
        }
        adjustMoney(amountToBeSplit,spendby,spendfor);
    }

    @Override
    public List<Owes> showAllDuesOfMember(String memberName) throws MemberNotFoundException {
        if(!housematesRepository.isExitByMembername(memberName)) throw new MemberNotFoundException("MEMBER_NOT_FOUND");
        return memberOwesRepository.findListOfOwesByPersonName(memberName);
    }

    @Override
    public Double clearDuesOfMember(String memberWhoOwes, String memberWhoLed, Double amount) throws MemberNotFoundException, IncorrectPaymentException {
        if(!housematesRepository.isExitByMembername(memberWhoOwes)) throw new MemberNotFoundException("MEMBER_NOT_FOUND");
        if(!housematesRepository.isExitByMembername(memberWhoLed)) throw new MemberNotFoundException("MEMBER_NOT_FOUND");
        List<Owes> owesList = memberOwesRepository.findListOfOwesByPersonName(memberWhoOwes);
        Double currentDues =0.0;
        for(Owes owes:owesList){
            if(owes.getPerson().getName().equals(memberWhoLed)){
                if(owes.getAmountOwes()>=amount){
                    owes.setAmountOwes(owes.getAmountOwes()-amount);
                    currentDues = owes.getAmountOwes();
                }else{
                    throw new IncorrectPaymentException("INCORRECT_PAYMENT");
                }
            }
        }
        return currentDues;
    }
}
