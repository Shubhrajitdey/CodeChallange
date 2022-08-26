package com.geektrust.backend.repositories;

import com.geektrust.backend.entities.Member;

import java.util.*;
import java.util.stream.Collectors;

public class HousematesRepository implements IHousematesRepository{
    private final Map<String, Member> housematesMap;
    private Integer autoIncrement;
    public HousematesRepository() {
        this.housematesMap = new HashMap<String, Member>();
        this.autoIncrement =0;
    }

    @Override
    public Member save(Member entity) {
        if(entity.getId()==null){
            autoIncrement++;
            Member member =new Member(Integer.toString(autoIncrement),entity.getName());
            housematesMap.put(member.getName(), member);
            return member;
        }
        housematesMap.put(entity.getName(),entity);
        return entity;
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(housematesMap.values());
    }

    @Override
    public void delete(Member entity) {
        housematesMap.remove(entity.getName());
    }

    @Override
    public long count() {
        return housematesMap.size();
    }

    @Override
    public Member findMemberByName(String memberName) {
        return housematesMap.get(memberName);
    }

    @Override
    public boolean isExitByMembername(String memberName) {
        if(memberName!=null) {
            return housematesMap.containsKey(memberName);
        }else{
            return false;
        }
    }
}
