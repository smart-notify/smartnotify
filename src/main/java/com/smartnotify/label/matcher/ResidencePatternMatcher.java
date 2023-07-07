package com.smartnotify.label.matcher;

import com.smartnotify.condominium.model.CondominiumType;

public interface ResidencePatternMatcher {

    String matchResidencePattern(String labelContent);

    CondominiumType getCondominiumType();

}
