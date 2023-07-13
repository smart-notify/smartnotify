package com.smartnotify.residence.factory;

import com.smartnotify.condominium.model.CondominiumType;
import com.smartnotify.residence.normalizer.ResidenceDataNormalizer;

public interface ResidenceDataNormalizerFactory {

    ResidenceDataNormalizer findStrategy(CondominiumType condominiumType);

}
