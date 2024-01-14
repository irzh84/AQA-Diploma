package ru.netology.diploma.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.diploma.data.APIHelper;
import ru.netology.diploma.data.DataHelper;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class APITest {
    @Test
    @DisplayName("")
    void should200ValidCardNumberApproved() {
        var card = DataHelper.setValidCardNumberApproved();
        var actual = APIHelper.returnResponsePaymentGate200(card);
        var expected = card.getStatus();
        assertEquals(expected, actual);
    }
}
