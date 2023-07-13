package com.smartnotify.residence.matcher;

import com.smartnotify.condominium.model.CondominiumType;

public interface ResidencePatternMatcher {

    String matchResidencePattern(String labelContent);

    CondominiumType getCondominiumType();

}
