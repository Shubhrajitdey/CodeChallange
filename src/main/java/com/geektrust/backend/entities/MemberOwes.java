package com.geektrust.backend.entities;

import java.util.List;

public class MemberOwes extends BaseEntity{
    private final Member member;
    private List<Owes> owesList;

    public MemberOwes(String id, Member member, List<Owes> owesList) {
        this.member = member;
        this.owesList = owesList;
        this.id = id;
    }

    public Member getMember() {
        return member;
    }

    public List<Owes> getOwesList() {
        return owesList;
    }


    @Override
    public String toString() {
        return "PersonOwes{" +
                "person=" + member +
                ", owesList=" + owesList +
                ", id='" + id + '\'' +
                '}';
    }
}
