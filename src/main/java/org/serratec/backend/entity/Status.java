package org.serratec.backend.entity;

import org.serratec.backend.exception.EnumException;

public enum Status {

    FATURADO,
    CANCELADO,
    PENDENTE,
    PROSSESSANDO,
    ENTREGUE,
    ENVIADO;

    public static Status verificarStatus(String status) {
        for (Status s : Status.values()) {
            if (status.equals(s.name())) {
                return s;
            }
        }
        throw new EnumException("Status inválido.");
    }

}


