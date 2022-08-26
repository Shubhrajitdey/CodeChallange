package com.geektrust.backend.repositories;

import com.geektrust.backend.entities.Member;

public interface IHousematesRepository extends ICRUDRepository<Member,String>{
    public Member findMemberByName(String memberName);
    public boolean isExitByMembername(String memberName);
}
