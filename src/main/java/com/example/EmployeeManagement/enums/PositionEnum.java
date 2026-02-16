package com.example.EmployeeManagement.enums;

public enum PositionEnum {
    BOSS("BOSHLIQ"),
    MANAGER("ISH_BOSHQARUVCHI"),
    WELDER("PAYVANDCHI"),
    PAINTER("KRASKA_SEPUVCHI"),
    COOK("OSHPAZ"),
    DRIVER("HAYDOVCHI"),
    GUARD("QOROVUL"),
    FABRICATOR("ZAGATOVKACHI"),
    CARPENTER("YOG‘OCH_USTASI"),
    COLLECTOR("YIG'UVCHI"),
    OTHER("BOSHQA ISHCHI");

    private final String uzbLabel;

    PositionEnum(String uzbLabel) {
        this.uzbLabel = uzbLabel;
    }

    public String getUzbLabel() {
        return uzbLabel;
    }
}
