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
 * <p>Java class for TradeStatus6Code.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TradeStatus6Code">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="INVA"/>
 *     &lt;enumeration value="FMTC"/>
 *     &lt;enumeration value="SMAP"/>
 *     &lt;enumeration value="RJCT"/>
 *     &lt;enumeration value="RSCD"/>
 *     &lt;enumeration value="STLD"/>
 *     &lt;enumeration value="SPLI"/>
 *     &lt;enumeration value="UMTC"/>
 *     &lt;enumeration value="SMAT"/>
 *     &lt;enumeration value="FUMT"/>
 *     &lt;enumeration value="NETT"/>
 *     &lt;enumeration value="PFIX"/>
 *     &lt;enumeration value="OMTC"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TradeStatus6Code")
@XmlEnum
public enum TradeStatus6Code {

    INVA,
    FMTC,
    SMAP,
    RJCT,
    RSCD,
    STLD,
    SPLI,
    UMTC,
    SMAT,
    FUMT,
    NETT,
    PFIX,
    OMTC;

    public String value() {
        return name();
    }

    public static TradeStatus6Code fromValue(String v) {
        return valueOf(v);
    }

}
