package com.smartnotify.residence.normalizer;

import com.smartnotify.condominium.model.CondominiumType;
import org.springframework.stereotype.Service;

@Service
public class VerticalResidenceDataNormalizer implements ResidenceDataNormalizer {

    // Default pattern: APTO 1
    @Override
    public String normalize(final String residenceDetails) {
        return residenceDetails
                .trim()
                .toUpperCase()
                .replaceAll(" ", "")
                .replaceAll("\\.", "")
                .replaceAll("(APARTAMENTO|APTO|AP)", "APTO ")
                .replaceAll("(BLOCO|BLCO|BLC|BL)", " BLOCO ")
                .replaceAll(" 0+", " ");
    }

    @Override
    public CondominiumType getCondominiumType() {
        return CondominiumType.VERTICAL;
    }

}
