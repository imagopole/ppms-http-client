/**
 *
 */
package org.imagopole.ppms.api.dto;

import org.imagopole.ppms.util.Check;

/**
 * PUMAPI request parameters & response codes wrapper/builder with a mapping
 * around the native RPC invocations results.
 *
 * @author seb
 *
 */
public class PumapiParams {

    /**
     * Vanilla constructor
     */
    public PumapiParams() {
        super();
    }

    /**
     * @author seb
     *
     */
    public enum PpmsSystemPrivilege {

        Autonomous("A"),
        Deactivated("D"),
        Novice("N"),
        SuperUser("S");

        /** PUMAPI response identifier */
        private String pumapiName;

        private PpmsSystemPrivilege(String name) {
            this.pumapiName = name;
        }

        public String getPumapiName() {
            return pumapiName;
        }

        public final static PpmsSystemPrivilege fromString(String aName) {
            Check.notNull(aName, "aName");

            PpmsSystemPrivilege result = null;

            for (PpmsSystemPrivilege p : PpmsSystemPrivilege.values()) {
                if (p.getPumapiName().equals(aName)) {
                    result = p;
                }
            }

            Check.notNull(result, String.format("enum RPC name mapping result: %s", aName));
            return result;
        }

    }

}
