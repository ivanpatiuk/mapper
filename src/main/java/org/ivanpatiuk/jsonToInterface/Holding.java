package org.ivanpatiuk.jsonToInterface;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

interface Holding {
    LocalDate getEffectiveAsOfDate();
    LocalDateTime getAsOfDate();
}
