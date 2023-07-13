package com.smartnotify.resident.usecases.delete;

import com.smartnotify.condominium.model.CondominiumType;

public interface DeleteResident {

    void execute(String email, String condominiumId);

    CondominiumType getCondominiumType();

}
