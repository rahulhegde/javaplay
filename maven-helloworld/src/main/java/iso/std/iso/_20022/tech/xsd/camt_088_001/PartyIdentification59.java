//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2022.02.03 at 05:41:00 PM EST 
//


package iso.std.iso._20022.tech.xsd.camt_088_001;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PartyIdentification59 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PartyIdentification59">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PtyNm" type="{urn:iso:std:iso:20022:tech:xsd:camt.088.001.01}Max34Text" minOccurs="0"/>
 *         &lt;element name="AnyBIC" type="{urn:iso:std:iso:20022:tech:xsd:camt.088.001.01}PartyIdentification44" minOccurs="0"/>
 *         &lt;element name="AcctNb" type="{urn:iso:std:iso:20022:tech:xsd:camt.088.001.01}Max34Text" minOccurs="0"/>
 *         &lt;element name="Adr" type="{urn:iso:std:iso:20022:tech:xsd:camt.088.001.01}Max105Text" minOccurs="0"/>
 *         &lt;element name="ClrSysId" type="{urn:iso:std:iso:20022:tech:xsd:camt.088.001.01}ClearingSystemIdentification2Choice" minOccurs="0"/>
 *         &lt;element name="LglNttyIdr" type="{urn:iso:std:iso:20022:tech:xsd:camt.088.001.01}LEIIdentifier" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PartyIdentification59", propOrder = {
    "ptyNm",
    "anyBIC",
    "acctNb",
    "adr",
    "clrSysId",
    "lglNttyIdr"
})
public class PartyIdentification59 {

    @XmlElement(name = "PtyNm")
    protected String ptyNm;
    @XmlElement(name = "AnyBIC")
    protected PartyIdentification44 anyBIC;
    @XmlElement(name = "AcctNb")
    protected String acctNb;
    @XmlElement(name = "Adr")
    protected String adr;
    @XmlElement(name = "ClrSysId")
    protected ClearingSystemIdentification2Choice clrSysId;
    @XmlElement(name = "LglNttyIdr")
    protected String lglNttyIdr;

    /**
     * Gets the value of the ptyNm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPtyNm() {
        return ptyNm;
    }

    /**
     * Sets the value of the ptyNm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPtyNm(String value) {
        this.ptyNm = value;
    }

    /**
     * Gets the value of the anyBIC property.
     * 
     * @return
     *     possible object is
     *     {@link PartyIdentification44 }
     *     
     */
    public PartyIdentification44 getAnyBIC() {
        return anyBIC;
    }

    /**
     * Sets the value of the anyBIC property.
     * 
     * @param value
     *     allowed object is
     *     {@link PartyIdentification44 }
     *     
     */
    public void setAnyBIC(PartyIdentification44 value) {
        this.anyBIC = value;
    }

    /**
     * Gets the value of the acctNb property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAcctNb() {
        return acctNb;
    }

    /**
     * Sets the value of the acctNb property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAcctNb(String value) {
        this.acctNb = value;
    }

    /**
     * Gets the value of the adr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdr() {
        return adr;
    }

    /**
     * Sets the value of the adr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdr(String value) {
        this.adr = value;
    }

    /**
     * Gets the value of the clrSysId property.
     * 
     * @return
     *     possible object is
     *     {@link ClearingSystemIdentification2Choice }
     *     
     */
    public ClearingSystemIdentification2Choice getClrSysId() {
        return clrSysId;
    }

    /**
     * Sets the value of the clrSysId property.
     * 
     * @param value
     *     allowed object is
     *     {@link ClearingSystemIdentification2Choice }
     *     
     */
    public void setClrSysId(ClearingSystemIdentification2Choice value) {
        this.clrSysId = value;
    }

    /**
     * Gets the value of the lglNttyIdr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLglNttyIdr() {
        return lglNttyIdr;
    }

    /**
     * Sets the value of the lglNttyIdr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLglNttyIdr(String value) {
        this.lglNttyIdr = value;
    }

}
