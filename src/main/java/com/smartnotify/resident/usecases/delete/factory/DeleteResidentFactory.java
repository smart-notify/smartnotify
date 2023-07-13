package com.smartnotify.resident.usecases.delete.factory;

import com.smartnotify.condominium.model.CondominiumType;
import com.smartnotify.resident.usecases.delete.DeleteResident;

public interface DeleteResidentFactory {

    DeleteResident findStrategy(CondominiumType condominiumType);

}
