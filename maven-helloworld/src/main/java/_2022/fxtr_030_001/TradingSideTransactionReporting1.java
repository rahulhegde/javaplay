//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2022.02.03 at 05:41:49 PM EST 
//


package _2022.fxtr_030_001;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TradingSideTransactionReporting1 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TradingSideTransactionReporting1">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RptgJursdctn" type="{urn:iso:std:iso:20022:tech:xsd:fxtr.030.001.04}Max35Text" minOccurs="0"/>
 *         &lt;element name="RptgPty" type="{urn:iso:std:iso:20022:tech:xsd:fxtr.030.001.04}PartyIdentification73Choice" minOccurs="0"/>
 *         &lt;element name="TradgSdUnqTxIdr" type="{urn:iso:std:iso:20022:tech:xsd:fxtr.030.001.04}UniqueTransactionIdentifier2" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TradingSideTransactionReporting1", propOrder = {
    "rptgJursdctn",
    "rptgPty",
    "tradgSdUnqTxIdr"
})
public class TradingSideTransactionReporting1 {

    @XmlElement(name = "RptgJursdctn")
    protected String rptgJursdctn;
    @XmlElement(name = "RptgPty")
    protected PartyIdentification73Choice rptgPty;
    @XmlElement(name = "TradgSdUnqTxIdr")
    protected List<UniqueTransactionIdentifier2> tradgSdUnqTxIdr;

    /**
     * Gets the value of the rptgJursdctn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRptgJursdctn() {
        return rptgJursdctn;
    }

    /**
     * Sets the value of the rptgJursdctn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRptgJursdctn(String value) {
        this.rptgJursdctn = value;
    }

    /**
     * Gets the value of the rptgPty property.
     * 
     * @return
     *     possible object is
     *     {@link PartyIdentification73Choice }
     *     
     */
    public PartyIdentification73Choice getRptgPty() {
        return rptgPty;
    }

    /**
     * Sets the value of the rptgPty property.
     * 
     * @param value
     *     allowed object is
     *     {@link PartyIdentification73Choice }
     *     
     */
    public void setRptgPty(PartyIdentification73Choice value) {
        this.rptgPty = value;
    }

    /**
     * Gets the value of the tradgSdUnqTxIdr property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tradgSdUnqTxIdr property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTradgSdUnqTxIdr().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link UniqueTransactionIdentifier2 }
     * 
     * 
     */
    public List<UniqueTransactionIdentifier2> getTradgSdUnqTxIdr() {
        if (tradgSdUnqTxIdr == null) {
            tradgSdUnqTxIdr = new ArrayList<UniqueTransactionIdentifier2>();
        }
        return this.tradgSdUnqTxIdr;
    }

}
