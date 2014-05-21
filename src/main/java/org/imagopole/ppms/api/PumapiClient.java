/**
 *
 */
package org.imagopole.ppms.api;

import java.util.List;

import org.imagopole.ppms.api.config.PumapiConfig;
import org.imagopole.ppms.api.dto.PpmsGroup;
import org.imagopole.ppms.api.dto.PpmsSystem;
import org.imagopole.ppms.api.dto.PpmsUser;
import org.imagopole.ppms.api.dto.PpmsUserPrivilege;


/**
 * Interface definition to a PPMS Management Utility API (PUMAPI) as of
 * revision <code>2014.0</code>.
 *
 * @author seb
 *
 */
public interface PumapiClient {

    /**
     * Returns the configuration in use.
     *
     * @return PumapiConfig
     */
    PumapiConfig getConfig();

    /**
     * Defines client-wide API configuration parameters (eg. authentication
     * key, proxy settings, etc).
     *
     * @param configProvider the PUMAPI configuration provider
     */
    void setConfig(PumapiConfig config);

    /**
     * Retrieves a list of PPMS usernames, possibly filtered according to their
     * activity status.
     *
     * If the <code>active</code> parameter is null, all usernames are looked up.
     *
     * @param active may be null - status filter
     * @return a list of usernames, or an empty list if none found
     * @throws PumapiException in case of an underlying error (API or technical)
     *
     * @see <code>getusers</code> PUMAPI action
     */
    List<String> getUsers(Boolean active) throws PumapiException;

    /**
     * Retrieves a PPMS user by login.
     * If the user is not found, returns null.
     *
     * @param login the username / PPMS identifier
     * @return the user attributes or null if not found.
     * @throws PumapiException in case of an underlying error (API or technical)
     */
    PpmsUser getUser(String login) throws PumapiException;

    /**
     * Retrieves a list of PPMS instruments (a.k.a systems) available to a given
     * username, keyed by privilege level on that system (eg. autonomy).
     *
     * @param login the PPMS username
     * @return a list of instrument identifiers indexed by privilege level, or an empty list if none found
     * @throws PumapiException in case of an underlying error (API or technical)
     *
     * @see <code>getuserrights</code> PUMAPI action
     */
    List<PpmsUserPrivilege> getUserRights(String login) throws PumapiException;

    //List<String> getUserProjects(String login) throws PumapiException;

    /**
     * Retrieves a list of PPMS groups (a.k.a unit) identifiers (a.k.a unitlogin), possibly filtered
     * according to their activity status.
     *
     * If the <code>active</code> parameter is null, all group unitlogins are looked up.
     *
     * @param active may be null - status filter
     * @return a list of group unitlogins, or an empty list if none found
     * @throws PumapiException in case of an underlying error (API or technical)
     *
     * @see <code>getgroups</code> PUMAPI action
     */
    List<String> getGroups(Boolean active) throws PumapiException;

    /**
     * Retrieves a PPMS group (a.k.a unit) by unit login.
     * If the group/unit is not found, returns null.
     *
     * @param unitLogin the PPMS group/unit identifier
     * @return the group/unit attributes or null if not found.
     * @throws PumapiException in case of an underlying error (API or technical)
     *
     * @see <code>getgroup</code> PUMAPI action
     */
    PpmsGroup getGroup(String unitLogin) throws PumapiException;

    PpmsSystem getSystem(Long systemId) throws PumapiException;

    /**
     * Validates password for a PPMS user.
     *
     * @param login the username / PPMS identifier
     * @param password the plain-text password
     * @return true if the password check succeeded, false otherwise
     * @throws PumapiException in case of an underlying error (API or technical)
     *
     * @see <code>auth</code> PUMAPI action
     */
    boolean authenticate(String login, String password) throws PumapiException;

}
