package com.smartnotify.label.normalizer;

import com.smartnotify.condominium.model.CondominiumType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

@ExtendWith(MockitoExtension.class)
class HorizontalResidenceDataNormalizerTest {

    @InjectMocks
    private HorizontalResidenceDataNormalizer normalizer;

    @Test
    void standardize() {
        final String regularFormatExpected = "CASA 10";

        assertEquals(regularFormatExpected, normalizer.normalize("CASA 10"));
        assertEquals(regularFormatExpected, normalizer.normalize("Casa10"));
        assertEquals(regularFormatExpected, normalizer.normalize("Casa010"));
        assertEquals(regularFormatExpected, normalizer.normalize(" Casa    10 "));
        assertEquals("CASA N13", normalizer.normalize("CASAN13"));
        assertEquals("CASA A12731C", normalizer.normalize("casa A12731c"));
    }

    @Test
    void getCondominiumType() {
        assertSame(normalizer.getCondominiumType(), CondominiumType.HORIZONTAL);
    }

}
