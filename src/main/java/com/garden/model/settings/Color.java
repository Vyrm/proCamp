package com.garden.model.settings;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

@XmlEnum
public enum Color {
    @XmlEnumValue(value = "red")
    RED,
    @XmlEnumValue(value = "blue")
    BLUE,
    @XmlEnumValue(value = "yellow")
    YELLOW
}
