/**
 *
 */
package org.imagopole.ppms.api.dto;


/**
 * A PPMS system right entity as returned by PUMAPI (ie. associated user record for a given instrument).
 *
 * @author seb
 *
 */
public class PpmsSystemPrivilege {

    private String username;
    private PpmsPrivilege privilege;

    /**
     *
     */
    public PpmsSystemPrivilege() {
        super();
    }

    /**
     * @param username
     * @param privilege
     */
    public PpmsSystemPrivilege(String username, PpmsPrivilege privilege) {
        super();
        this.username = username;
        this.privilege = privilege;
    }

    /**
     * Returns userLogin.
     * @return the userLogin
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets userLogin.
     * @param userLogin the userLogin to set
     */
    public void setUsername(String userLogin) {
        this.username = userLogin;
    }

    /**
     * Returns privilege.
     * @return the privilege
     */
    public PpmsPrivilege getPrivilege() {
        return privilege;
    }

    /**
     * Sets privilege.
     * @param privilege the privilege to set
     */
    public void setPrivilege(PpmsPrivilege privilege) {
        this.privilege = privilege;
    }

}
