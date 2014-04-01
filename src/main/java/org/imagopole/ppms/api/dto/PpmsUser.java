/**
 *
 */
package org.imagopole.ppms.api.dto;

/**
 * A PPMS user entity as returned by PUMAPI.
 *
 * @author seb
 *
 */
public class PpmsUser {

    private String login;
    private String lname;
    private String fname;
    private String email;
    private String phone;
    private String bcode;
    private String affiliation;
    private String unitlogin;
    private Boolean mustchpwd;
    private Boolean mustchbcode;
    private Boolean active;

    /**
     *
     */
    public PpmsUser() {
        super();
    }

    /**
     * Returns login.
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * Sets login.
     * @param login the login to set
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Returns lname.
     * @return the lname
     */
    public String getLname() {
        return lname;
    }

    /**
     * Sets lname.
     * @param lname the lname to set
     */
    public void setLname(String lname) {
        this.lname = lname;
    }

    /**
     * Returns fname.
     * @return the fname
     */
    public String getFname() {
        return fname;
    }

    /**
     * Sets fname.
     * @param fname the fname to set
     */
    public void setFname(String fname) {
        this.fname = fname;
    }

    /**
     * Returns email.
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns phone.
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets phone.
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Returns bcode.
     * @return the bcode
     */
    public String getBcode() {
        return bcode;
    }

    /**
     * Sets bcode.
     * @param bcode the bcode to set
     */
    public void setBcode(String bcode) {
        this.bcode = bcode;
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
     * Returns mustchpwd.
     * @return the mustchpwd
     */
    public Boolean getMustchpwd() {
        return mustchpwd;
    }

    /**
     * Sets mustchpwd.
     * @param mustchpwd the mustchpwd to set
     */
    public void setMustchpwd(Boolean mustchpwd) {
        this.mustchpwd = mustchpwd;
    }

    /**
     * Returns mustchbcode.
     * @return the mustchbcode
     */
    public Boolean getMustchbcode() {
        return mustchbcode;
    }

    /**
     * Sets mustchbcode.
     * @param mustchbcode the mustchbcode to set
     */
    public void setMustchbcode(Boolean mustchbcode) {
        this.mustchbcode = mustchbcode;
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
