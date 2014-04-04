/**
 *
 */
package org.imagopole.ppms.api.dto;

/**
 * A PPMS group entity as returned by PUMAPI.
 *
 * @author seb
 *
 */
public class PpmsGroup {

    private String unitlogin;
    private String unitname;
    private String headname;
    private String heademail;
    private String unitbcode;
    private String department;
    private String institution;
    private String address;
    private String affiliation;
    private Boolean ext;
    private Boolean active;

    /**
     *
     */
    public PpmsGroup() {
        super();
    }

    /**
     * Returns unitlogin.
     * @return the unitlogin
     */
    public String getUnitlogin() {
        return unitlogin;
    }

    /**
     * Sets unitlogin.
     * @param unitlogin the unitlogin to set
     */
    public void setUnitlogin(String unitlogin) {
        this.unitlogin = unitlogin;
    }

    /**
     * Returns unitname.
     * @return the unitname
     */
    public String getUnitname() {
        return unitname;
    }

    /**
     * Sets unitname.
     * @param unitname the unitname to set
     */
    public void setUnitname(String unitname) {
        this.unitname = unitname;
    }

    /**
     * Returns headname.
     * @return the headname
     */
    public String getHeadname() {
        return headname;
    }

    /**
     * Sets headname.
     * @param headname the headname to set
     */
    public void setHeadname(String headname) {
        this.headname = headname;
    }

    /**
     * Returns heademail.
     * @return the heademail
     */
    public String getHeademail() {
        return heademail;
    }

    /**
     * Sets heademail.
     * @param heademail the heademail to set
     */
    public void setHeademail(String heademail) {
        this.heademail = heademail;
    }

    /**
     * Returns unitbcode.
     * @return the unitbcode
     */
    public String getUnitbcode() {
        return unitbcode;
    }

    /**
     * Sets unitbcode.
     * @param unitbcode the unitbcode to set
     */
    public void setUnitbcode(String unitbcode) {
        this.unitbcode = unitbcode;
    }

    /**
     * Returns department.
     * @return the department
     */
    public String getDepartment() {
        return department;
    }

    /**
     * Sets department.
     * @param department the department to set
     */
    public void setDepartment(String department) {
        this.department = department;
    }

    /**
     * Returns institution.
     * @return the institution
     */
    public String getInstitution() {
        return institution;
    }

    /**
     * Sets institution.
     * @param institution the institution to set
     */
    public void setInstitution(String institution) {
        this.institution = institution;
    }

    /**
     * Returns address.
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets address.
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Returns affiliation.
     * @return the affiliation
     */
    public String getAffiliation() {
        return affiliation;
    }

    /**
     * Sets affiliation.
     * @param affiliation the affiliation to set
     */
    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

    /**
     * Returns ext.
     * @return the ext
     */
    public Boolean getExt() {
        return ext;
    }

    /**
     * Sets ext.
     * @param ext the ext to set
     */
    public void setExt(Boolean ext) {
        this.ext = ext;
    }

    /**
     * Returns active.
     * @return the active
     */
    public Boolean getActive() {
        return active;
    }

    /**
     * Sets active.
     * @param active the active to set
     */
    public void setActive(Boolean active) {
        this.active = active;
    }

}
