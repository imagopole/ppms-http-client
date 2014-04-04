/**
 *
 */
package org.imagopole.ppms.api.dto;

/**
 * A PPMS system (instrument) entity as returned by PUMAPI.
 *
 * @author seb
 *
 */
public class PpmsSystem {

    private Long coreFacilityRef;
    private Long systemId;
    private String type;
    private String name;
    private String localisation;
    private Boolean active;
    private Boolean schedules;
    private Boolean stats;
    private Boolean bookable;
    private Boolean autonomyRequired;
    private Boolean autonomyRequiredAfterHours;

    /**
     *
     */
    public PpmsSystem() {
        super();
    }

    /**
     * Returns coreFacilityRef.
     * @return the coreFacilityRef
     */
    public Long getCoreFacilityRef() {
        return coreFacilityRef;
    }

    /**
     * Sets coreFacilityRef.
     * @param coreFacilityRef the coreFacilityRef to set
     */
    public void setCoreFacilityRef(Long coreFacilityRef) {
        this.coreFacilityRef = coreFacilityRef;
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
     * Returns type.
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets type.
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Returns name.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns localisation.
     * @return the localisation
     */
    public String getLocalisation() {
        return localisation;
    }

    /**
     * Sets localisation.
     * @param localisation the localisation to set
     */
    public void setLocalisation(String localisation) {
        this.localisation = localisation;
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

    /**
     * Returns schedules.
     * @return the schedules
     */
    public Boolean getSchedules() {
        return schedules;
    }

    /**
     * Sets schedules.
     * @param schedules the schedules to set
     */
    public void setSchedules(Boolean schedules) {
        this.schedules = schedules;
    }

    /**
     * Returns stats.
     * @return the stats
     */
    public Boolean getStats() {
        return stats;
    }

    /**
     * Sets stats.
     * @param stats the stats to set
     */
    public void setStats(Boolean stats) {
        this.stats = stats;
    }

    /**
     * Returns bookable.
     * @return the bookable
     */
    public Boolean getBookable() {
        return bookable;
    }

    /**
     * Sets bookable.
     * @param bookable the bookable to set
     */
    public void setBookable(Boolean bookable) {
        this.bookable = bookable;
    }

    /**
     * Returns autonomyRequired.
     * @return the autonomyRequired
     */
    public Boolean getAutonomyRequired() {
        return autonomyRequired;
    }

    /**
     * Sets autonomyRequired.
     * @param autonomyRequired the autonomyRequired to set
     */
    public void setAutonomyRequired(Boolean autonomyRequired) {
        this.autonomyRequired = autonomyRequired;
    }

    /**
     * Returns autonomyRequiredAfterHours.
     * @return the autonomyRequiredAfterHours
     */
    public Boolean getAutonomyRequiredAfterHours() {
        return autonomyRequiredAfterHours;
    }

    /**
     * Sets autonomyRequiredAfterHours.
     * @param autonomyRequiredAfterHours the autonomyRequiredAfterHours to set
     */
    public void setAutonomyRequiredAfterHours(Boolean autonomyRequiredAfterHours) {
        this.autonomyRequiredAfterHours = autonomyRequiredAfterHours;
    }

}
