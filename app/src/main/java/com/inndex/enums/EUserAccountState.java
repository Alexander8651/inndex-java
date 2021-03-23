package com.inndex.enums;

public enum EUserAccountState {

    ACTIVE(1),
    INACTIVE(2);

    private Integer id;

    public Integer getId() {
	return id;
    }

    public void setId(Integer id) {
	this.id = id;
    }

    EUserAccountState(Integer id) {
        this.id = id;
    }
}
