package com.smartnotify.residence.normalizer;

import com.smartnotify.condominium.model.CondominiumType;
import org.springframework.stereotype.Service;

@Service
public class HorizontalResidenceDataNormalizer implements ResidenceDataNormalizer {

    // Default pattern: CASA 1
    @Override
    public String normalize(final String residenceDetails) {
        return residenceDetails
                .trim()
                .toUpperCase()
                .replaceAll(" ", "")
                .replaceAll("(CASA)", "CASA ")
                .replaceAll(" 0+", " ");
    }

    @Override
    public CondominiumType getCondominiumType() {
        return CondominiumType.HORIZONTAL;
    }

}
