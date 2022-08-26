package com.geektrust.backend.repositories;

import com.geektrust.backend.entities.Member;
import com.geektrust.backend.entities.MemberOwes;
import com.geektrust.backend.entities.Owes;

import java.util.*;

public class MemberOwesRepository implements IMemberOwesRepository {
    private Map<String, MemberOwes> personOwesMap;
    private Integer autoIncrement;

    private final IHousematesRepository housematesRepository;
    public MemberOwesRepository(IHousematesRepository housematesRepository) {
        this.housematesRepository = housematesRepository;
        this.personOwesMap = new HashMap<String, MemberOwes>();
        autoIncrement =0;
    }

    @Override
    public MemberOwes save(MemberOwes entity) {
        if(entity.getId()==null){
            autoIncrement++;
            MemberOwes memberOwes=new MemberOwes(Integer.toString(autoIncrement),entity.getMember(),entity.getOwesList());
            personOwesMap.put(entity.getMember().getName(), memberOwes);
            return memberOwes;
        }
        personOwesMap.put(entity.getMember().getName(),entity);
        return entity;
    }

    @Override
    public List<MemberOwes> findAll() {
        return new ArrayList<MemberOwes>(personOwesMap.values());
    }


    @Override
    public void delete(MemberOwes entity) {
        personOwesMap.remove(entity.getMember().getName());
    }
    @Override
    public long count() {
        return personOwesMap.size();
    }

    @Override
    public List<Owes> findListOfOwesByPersonName(String personName) {
        if(personName!=null){
            if(personOwesMap.containsKey(personName)){
                return personOwesMap.get(personName).getOwesList();
            }
        }
        return new ArrayList<Owes>();
    }

    @Override
    public void addNewPersonIntoOwesList(Owes newMemberOwes) {
        for(MemberOwes memberOwes:personOwesMap.values()){
            memberOwes.getOwesList().add(newMemberOwes);
        }
    }


    @Override
    public void deletePersonFromOwesList(String memberNameToDeleteFromALlOwes) {
        for(MemberOwes memberOwes:personOwesMap.values()){
            Owes owesToBeDelete = memberOwes.getOwesList().stream()
                                                          .filter((p)->p.getPerson().getName().equals(memberNameToDeleteFromALlOwes))
                                                          .findAny()
                                                          .get();
            memberOwes.getOwesList().remove(owesToBeDelete);
        }
    }

    @Override
    public MemberOwes findMemberOwesByName(String memberName) {
        if(personOwesMap.containsKey(memberName)){
            return personOwesMap.get(memberName);
        }
        return null;
    }


}
