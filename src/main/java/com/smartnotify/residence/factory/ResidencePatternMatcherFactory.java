package com.smartnotify.residence.factory;

import com.smartnotify.condominium.model.CondominiumType;
import com.smartnotify.residence.matcher.ResidencePatternMatcher;

public interface ResidencePatternMatcherFactory {

    ResidencePatternMatcher findStrategy(CondominiumType condominiumType);

}
