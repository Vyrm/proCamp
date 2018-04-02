package com.garden.model.settings;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlEnum
public enum Fresh {
    @XmlEnumValue(value = "low")
    LOW,
    @XmlEnumValue(value = "medium")
    MEDIUM,
    @XmlEnumValue(value = "high")
    HIGH
}
