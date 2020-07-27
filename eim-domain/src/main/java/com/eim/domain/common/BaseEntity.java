package com.eim.domain.common;

import java.io.Serializable;

public class BaseEntity  implements Serializable {

    private static final long serialVersionUID = 1L;

    private String dsKey;

    public String getDsKey() {
        return dsKey;
    }

    public void setDsKey(String dsKey) {
        this.dsKey = dsKey;
    }
}
