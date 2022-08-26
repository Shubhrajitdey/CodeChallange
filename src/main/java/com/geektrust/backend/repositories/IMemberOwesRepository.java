package com.geektrust.backend.repositories;

import com.geektrust.backend.entities.Member;
import com.geektrust.backend.entities.MemberOwes;
import com.geektrust.backend.entities.Owes;
import java.util.List;

public interface IMemberOwesRepository extends ICRUDRepository<MemberOwes,String>{
    public List<Owes> findListOfOwesByPersonName(String personName);
    public void addNewPersonIntoOwesList(Owes newMemberOwes);
    public void deletePersonFromOwesList(String memberNameToDeleteFromALlOwes);
    public MemberOwes findMemberOwesByName(String memberName);

}
