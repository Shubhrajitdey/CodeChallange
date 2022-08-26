package com.geektrust.backend.services;

import com.geektrust.backend.entities.Member;
import com.geektrust.backend.entities.MemberOwes;
import com.geektrust.backend.entities.Owes;
import com.geektrust.backend.exceptions.HousefullException;
import com.geektrust.backend.exceptions.MemberNotFoundException;
import com.geektrust.backend.exceptions.MoveoutFailureException;
import com.geektrust.backend.repositories.IHousematesRepository;
import com.geektrust.backend.repositories.IMemberOwesRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HousematesService implements IHousematesService{
    private final IHousematesRepository iHousematesRepository;
    private final IMemberOwesRepository memberOwesRepository;
    //private final Double MIN_AMOUNT =0.0;
    Integer maxMemberSize;
    public HousematesService(IHousematesRepository iHousematesRepository, IMemberOwesRepository memberOwesRepository, Integer maxMemberSize) {
        this.iHousematesRepository = iHousematesRepository;
        this.memberOwesRepository = memberOwesRepository;
        this.maxMemberSize=maxMemberSize;
    }

    @Override
    public void moveIn(String memberName) throws HousefullException {
        if(iHousematesRepository.count()+1>maxMemberSize){
            throw new HousefullException("HouseFull");
        }
        List<Member> existingMemberInHouse=iHousematesRepository.findAll();
        Member member = new Member(null,memberName);
        Member savedMember = iHousematesRepository.save(member);
        List<Owes> listOfOwesOfExistingMember=new ArrayList<Owes>();
        for(Member member1:existingMemberInHouse){
            Owes owes=new Owes(member1.getId(),member1,0.0);
            listOfOwesOfExistingMember.add(owes);
        }
        MemberOwes memberOwes=new MemberOwes(null,savedMember,listOfOwesOfExistingMember);
        memberOwesRepository.addNewPersonIntoOwesList(new Owes(savedMember.getId(),savedMember,0.0));
        memberOwesRepository.save(memberOwes);
    }

    @Override
    public void moveOut(String memberName) throws MemberNotFoundException, MoveoutFailureException {
        if(!iHousematesRepository.isExitByMembername(memberName)) throw new MemberNotFoundException("Member not found");
        Member memberToBeDelete = iHousematesRepository.findMemberByName(memberName);
        List<Owes> owesList = memberOwesRepository.findListOfOwesByPersonName(memberName);
        Map<String,Double> owesMap = new HashMap<String,Double>();
        for(Owes owes:owesList){
            if(owes.getAmountOwes()!=0){
                throw new MoveoutFailureException("member has some dues to pay");
            }
            owesMap.put(owes.getPerson().getName(),memberOwesRepository.findListOfOwesByPersonName(owes.getPerson().getName())
                                .stream()
                                .filter((p)->p.getPerson().getName().equals(memberName))
                                .map((p)->p.getAmountOwes())
                                .findFirst()
                                .get());
        }
        for(Double pendingAmount:owesMap.values()){
            if(pendingAmount>0){
                throw new MoveoutFailureException("member has some dues to recived");
            }
        }
        iHousematesRepository.delete(memberToBeDelete);
        memberOwesRepository.delete(memberOwesRepository.findMemberOwesByName(memberName));
        memberOwesRepository.deletePersonFromOwesList(memberName);

    }
}
