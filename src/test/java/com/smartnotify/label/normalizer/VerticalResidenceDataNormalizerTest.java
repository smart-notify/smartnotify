package com.smartnotify.label.normalizer;

import com.smartnotify.condominium.model.CondominiumType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class VerticalResidenceDataNormalizerTest {
    @InjectMocks
    private VerticalResidenceDataNormalizer normalizer;

    @Test
    void shouldNormalizeApartmentAndBlock() {
        final String expectedFormat = "APTO 34 BLOCO 1";

        var nonStandardizedResidenceDetailsList = Arrays.asList(
                "APTO 34 BLOCO1",
                "Apto034 bloco 1",
                "Apto34bloco 1",
                "Apto34bloco1",
                "      Apto34bloco1",
                "      Apto 34   bloco 1     ",
                "APARTAMENTO34 BLOCO1",
                "APARTAMENTO34BLOCO1",
                "apartamento34 bl1",
                "apartamento0034 bl01",
                "Ap34bloco1",
                "Ap34 blc1",
                "Ap34 blco1",
                "Apto 34 Bloco 1");

        var standardizedResidenceDetailsList = nonStandardizedResidenceDetailsList.stream()
                .map(residenceDetails -> normalizer.normalize(residenceDetails))
                .toList();

        assertTrue(standardizedResidenceDetailsList.stream().allMatch(item -> item.equals(expectedFormat)));
    }

    @Test
    void getCondominiumType() {
        assertSame(normalizer.getCondominiumType(), CondominiumType.VERTICAL);
    }

}
