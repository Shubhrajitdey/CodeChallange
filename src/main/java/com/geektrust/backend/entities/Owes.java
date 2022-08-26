package com.geektrust.backend.entities;

public class Owes extends BaseEntity{
    private final Member member;
    private Double amountOwes;

    //private Double MIN_AMOUNT =0.0;

    public Owes(String id, Member member, Double amountOwes) {
        this.member = member;
        this.amountOwes = amountOwes;
        this.id = id;
    }

    public Member getPerson() {
        return member;
    }

    public Double getAmountOwes() {
        return amountOwes;
    }

    public void setAmountOwes(Double amountOwes) {
        if(amountOwes>=0){
            this.amountOwes = amountOwes;
        }
    }

    @Override
    public String toString() {
        return "Owes{" +
                "person=" + member +
                ", amountOwes=" + amountOwes +
                ", id='" + id + '\'' +
                '}';
    }
}
