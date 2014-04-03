/**
 *
 */
package org.imagopole.ppms.api.dto;

import org.imagopole.ppms.api.dto.PumapiParams.PpmsSystemPrivilege;

/**
 * A PPMS user right entity as returned by PUMAPI.
 *
 * @author seb
 *
 */
public class PpmsUserPrivilege {

    private Long systemId;
    private PpmsSystemPrivilege privilege;

    /**
     *
     */
    public PpmsUserPrivilege() {
        super();
    }

    /**
     * @param systemId
     * @param privilege
     */
    public PpmsUserPrivilege(Long systemId, PpmsSystemPrivilege privilege) {
        super();
        this.systemId = systemId;
        this.privilege = privilege;
    }

    /**
     * Returns systemId.
     * @return the systemId
     */
    public Long getSystemId() {
        return systemId;
    }

    /**
     * Sets systemId.
     * @param systemId the systemId to set
     */
    public void setSystemId(Long systemId) {
        this.systemId = systemId;
    }

    /**
     * Returns privilege.
     * @return the privilege
     */
    public PpmsSystemPrivilege getPrivilege() {
        return privilege;
    }

    /**
     * Sets privilege.
     * @param privilege the privilege to set
     */
    public void setPrivilege(PpmsSystemPrivilege privilege) {
        this.privilege = privilege;
    }

}
