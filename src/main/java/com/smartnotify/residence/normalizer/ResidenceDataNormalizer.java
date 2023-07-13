package com.smartnotify.residence.normalizer;

import com.smartnotify.condominium.model.CondominiumType;

public interface ResidenceDataNormalizer {

    String normalize(String residenceDetails);

    CondominiumType getCondominiumType();

}
