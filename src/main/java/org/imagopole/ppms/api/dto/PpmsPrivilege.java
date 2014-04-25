package org.imagopole.ppms.api.dto;

import org.imagopole.ppms.util.Check;

/**
 * A PPMS privilege level enumeration as returned by PUMAPI.
 *
 * @author seb
 *
 */
public enum PpmsPrivilege {

    Autonomous("A"),
    Deactivated("D"),
    Novice("N"),
    SuperUser("S");

    /** PUMAPI response identifier */
    private String pumapiName;

    private PpmsPrivilege(String name) {
        this.pumapiName = name;
    }

    public String getPumapiName() {
        return pumapiName;
    }

    public final static PpmsPrivilege fromString(String aName) {
        Check.notNull(aName, "aName");

        PpmsPrivilege result = null;

        for (PpmsPrivilege p : PpmsPrivilege.values()) {
            if (p.getPumapiName().equals(aName)) {
                result = p;
            }
        }

        Check.notNull(result, String.format("enum RPC name mapping result: %s", aName));
        return result;
    }

}
