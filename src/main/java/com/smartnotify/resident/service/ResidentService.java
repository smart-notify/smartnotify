package com.smartnotify.resident.service;

import com.smartnotify.condominium.model.CondominiumType;
import com.smartnotify.resident.model.Resident;

public interface ResidentService {

    Resident findResidentByResidenceDetails(String residenceDetails, String receptionId);

    CondominiumType getCondominiumType();

}
