//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2022.02.03 at 05:41:49 PM EST 
//


package _2022.fxtr_030_001;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CollateralisationIndicator1Code.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="CollateralisationIndicator1Code">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="FULL"/>
 *     &lt;enumeration value="ONEW"/>
 *     &lt;enumeration value="PART"/>
 *     &lt;enumeration value="UNCO"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "CollateralisationIndicator1Code")
@XmlEnum
public enum CollateralisationIndicator1Code {

    FULL,
    ONEW,
    PART,
    UNCO;

    public String value() {
        return name();
    }

    public static CollateralisationIndicator1Code fromValue(String v) {
        return valueOf(v);
    }

}
