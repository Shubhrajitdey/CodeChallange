package com.geektrust.backend.services;

import com.geektrust.backend.exceptions.HousefullException;
import com.geektrust.backend.exceptions.MemberNotFoundException;
import com.geektrust.backend.exceptions.MoveoutFailureException;

public interface IHousematesService {
    public void moveIn(String memberName) throws HousefullException;
    public void moveOut(String memberName) throws MemberNotFoundException, MoveoutFailureException;
}
