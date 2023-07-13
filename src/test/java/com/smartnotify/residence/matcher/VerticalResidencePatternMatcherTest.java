package com.smartnotify.label.matcher;

import com.smartnotify.condominium.model.CondominiumType;
import com.smartnotify.config.exception.ResidenceDetailsNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class VerticalResidencePatternMatcherTest {

    private final String label1 = "Remetente\n" +
            "GRUB-Amazon Serviços de Varejo Ltda.\n" +
            "AVENIDA DOUTOR ANTONIO JOÃO ABDALA, Nº: 2010\n" +
            "Cajamar SP 07750820\n" +
            "Brazil\n" +
            "Joao da Silva\n" +
            "AVENIDA CARLOS BLOCK 2691\n" +
            "APTO 34 BLOCO1 ANHANGABAÚ\n" +
            "13205101 SP JUNDIAL\n" +
            "BRAZIL\n" +
            "GhfjtPkjF\n" +
            "1/820\n" +
            "13205-101\n" +
            "GRU8\n" +
            "A 3\n" +
            "AM047624098LO\n" +
            "LOGGI\n" +
            "N\n" +
            "10/03\n" +
            "UGO I";
    private final String label2 = "075414799-01\n" +
            "Magalu\n" +
            "Entregas\n" +
            "AGENCIA MAGALU\n" +
            "GFL\n" +
            "MALHA-DIRETA\n" +
            "SP\n" +
            "Pedido: 104926570-ZT-1\n" +
            "Nota Fiscal: 000004316\n" +
            "Data estimada: 16/03/2023\n" +
            "Remetente\n" +
            "ILHA BELA CALÇADOS\n" +
            "AV DA SAUDADE, 868- LOJA ILHA BELA\n" +
            "CALÇADOS\n" +
            "CAMPOS ELISEOS-14085000\n" +
            "ribeirao preto, SP\n" +
            "Destinatário\n" +
            "João da Silva\n" +
            "D\n" +
            "Avenida Carlos Block, 2691 - Apto34\n" +
            "bloco 1\n" +
            "anhangabaú - 13205101\n" +
            "JUNDIAI, SP";
    private final String label3 = "15/03/2023, 08:46\n" +
            "DANFE SIMPLIFICADO\n" +
            "CHAVE DE ACESSO\n" +
            "00000000000097000198550050000000000000000000\n" +
            "PROTOCOLO DE AUTORIZAÇÃO DE USO\n" +
            "135745391746410 - 15:03/2023 07:28:16\n" +
            "J-Saida NÚMERO 010.429 SÉRIE 5 EMISSÃO: 15/03/2023\n" +
            "EMITENTE\n" +
            "CONVERT IMPORTACAO E COMERCIO EIRELI\n" +
            "01326020 SÃO PAULO - SP\n" +
            "CPF/CNPJ 30.493.497/0001-98\n" +
            "- SP\n" +
            "CPF/CNPJ 123.947.637-11\n" +
            "DANFE - Tiny\n" +
            "DESTINATÁRIO\n" +
            "JOSÉ PAULO\n" +
            "AVENIDA SANTO AMARO, 9416 - APARTAMENTO 82 BLOCO4\n" +
            "ANHANGABAÚ. BAIRRO anhangabaú. 21.946-204 JUNDIAÍ\n" +
            "CÓD. DESCRIÇÃO\n" +
            "10101\n" +
            "13-5\n" +
            "Garrafa Termica Portatil 500ml Aco\n" +
            "Inox Visor Digital Led e Sensor de\n" +
            "Temperatura Quente e Frio - Preta\n" +
            "IE: 119.465.997.115\n" +
            "DADOS ADICIONAIS\n" +
            "Em Consignatário Place Soluções: 29.364.024/0001-01\n" +
            "TH:\n" +
            "UN QTD V.UNIT V.TOTAL\n" +
            "UN 1,00 53,90 53.90\n" +
            "TOTAL DA NFE: 64,11\n" +
            "TRANSPORTADOR/VOLUMES TRANSPORTADOS\n" +
            "LAB LOGÍSTICA LTDA\n" +
            "CPF/CNPJ 24.217.653/0001-95\n" +
            "Tributos aproximados: R$ 10.62 (Hederal) e R$ 9.70 (Estadual). Fonte: IBPT 835781\n" +
            "Ref. au pedido numero 702-6316109-0615455\n" +
            "https://erp.tiny.com.br/expedicao#edit/772825298 37/37";
    private final String label4 = "Loggi\n" +
            "Pedido: 1094651\n" +
            "NFe/DC: 5/10429\n" +
            "Volume: 1/1\n" +
            "* Peso(g): 400\n" +
            "* Dimensões: -\n" +
            "SMP1094651001\n" +
            "Observações:\n" +
            "TECNOLOGIA SMARTENVIOS\n" +
            "Destinatário\n" +
            "José de Silva\n" +
            "Avenida Santo Amaro, 9164 - Retiro\n" +
            "Ap92 Retiro\n" +
            "Jundial, SP\n" +
            "03957-826\n" +
            "SEM OBSERVAÇÃO\n" +
            "SMP1094651001\n" +
            "Remetente\n" +
            "CONVERT\n" +
            "Rua Joao Passalaqua, 170 - Bela Vista\n" +
            "Sao Paulo, SP\n" +
            "01326-020\n" +
            "X";
    private final String label5 = "DANFE SIMPLIFICADA\n" +
            "1- SAÍDA\n" +
            "NF:589289\n" +
            "SERIE 001\n" +
            "EMISSAO:10/03/2023\n" +
            "VALOR NF: 25,22\n" +
            "oceane\n" +
            "CHAVE\n" +
            "3223030448432100075555001000589289100619166\n" +
            "PROTOCOLO DE AUTORIZAÇÃO\n" +
            "{\\rtf1\\ansllansicpg1252\\deffo\n" +
            "32250304484321000755550010005892991006191662\n" +
            "REMETENTE\n" +
            "Promex Comercio e Importacao\n" +
            "Rodovia Darly Santos 4000\n" +
            "- Galpaavi Area 01\n" +
            "CEP: 29103091 UF: Vila Velha-ES\n" +
            "CNPJ: 04.484.321/0007-55 IE: 083669132\n" +
            "DESTINATÁRIO\n" +
            "Paula\n" +
            "Avenida Antonio Segre, 2163\n" +
            "- Bloco 5 Apto 304\n" +
            "JADLOG\n" +
            "CEP: 131301-753 UF Jundiai-SP\n" +
            "L\n" +
            "jadlog\n" +
            "N\n" +
            "Volume: 1/1\n" +
            "Peso(g) 58\n" +
            "DSP 20394727dgsp-\n" +
            "OBS: DESTINATARIO: PAULA PROMOCOES: MARKET PLACES - 20% OFF NO FRETEJA\n" +
            "ACEITACAO DESTA MERCADORIA IMPLICA AUTORIZACAO DO COMPRADOR AO VENDEDOR\n" +
            "PARA OBTER A RESTITUICAO DO DIFAL INCIDENTE NESTA VENDA, NOS TERMOS DO ART. 166\n" +
            "FONTE\n" +
            "DO CTN. IMPOSTOS PAGOS (FEDERAL R$ 2.96, ESTADUAL R$ 1.78, TOTAL R$ 4.74)\n" +
            "IBPT/EMPRESOMETRO.COM.BR VER SAO: 22.2.G CHAVE: BEA5CDI IE: 808013010110 ICMS\n" +
            "DEVIDO AO ES";

    private final String labelWithoutApartmentNumber = "Remetente\n" +
            "GRUB-Amazon Serviços de Varejo Ltda.\n" +
            "AVENIDA DOUTOR ANTONIO JOÃO ABDALA, Nº: 2010\n" +
            "Cajamar SP 07750820\n" +
            "Brazil\n" +
            "Joao da Silva\n" +
            "AVENIDA CARLOS BLOCK 2691\n" +
            "BLOCO1 ANHANGABAÚ\n" +
            "13205101 SP JUNDIAL\n" +
            "BRAZIL\n" +
            "GhfjtPkjF\n" +
            "1/820\n" +
            "13205-101\n" +
            "GRU8\n" +
            "A 3\n" +
            "AM047624098LO\n" +
            "LOGGI\n" +
            "N\n" +
            "10/03\n" +
            "UGO I";

    @InjectMocks
    private VerticalResidencePatternMatcher matcher;

    @Test
    void shouldRecognizeApartmentAndBlock() {
        final String expectedPatternLabel1 = "APTO 34 BLOCO1";
        final String expectedPatternLabel2 = "Apto34 bloco 1";
        final String expectedPatternLabel3 = "APARTAMENTO 82 BLOCO4";
        final String expectedPatternLabel4 = "Ap92";
        final String expectedPatternLabel5 = "Apto 304 Bloco 5";

        final String residenceInfoLabel1 = matcher.matchResidencePattern(label1);
        final String residenceInfoLabel2 = matcher.matchResidencePattern(label2);
        final String residenceInfoLabel3 = matcher.matchResidencePattern(label3);
        final String residenceInfoLabel4 = matcher.matchResidencePattern(label4);
        final String residenceInfoLabel5 = matcher.matchResidencePattern(label5);

        assertEquals(residenceInfoLabel1, expectedPatternLabel1);
        assertEquals(residenceInfoLabel2, expectedPatternLabel2);
        assertEquals(residenceInfoLabel3, expectedPatternLabel3);
        assertEquals(residenceInfoLabel4, expectedPatternLabel4);
        assertEquals(residenceInfoLabel5, expectedPatternLabel5);
    }

    @Test
    void shouldThrowResidenceDetailsNotFoundException() {
        assertThrows(ResidenceDetailsNotFoundException.class,
                () -> matcher.matchResidencePattern(labelWithoutApartmentNumber));
    }

    @Test
    void getCondominiumType() {
        assertSame(matcher.getCondominiumType(), CondominiumType.VERTICAL);
    }

}