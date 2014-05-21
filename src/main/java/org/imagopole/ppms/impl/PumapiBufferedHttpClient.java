/**
 *
 */
package org.imagopole.ppms.impl;

import java.util.List;

import org.imagopole.ppms.api.PumapiClient;
import org.imagopole.ppms.api.PumapiException;
import org.imagopole.ppms.api.PumapiHttpInvoker;
import org.imagopole.ppms.api.PumapiRequest;
import org.imagopole.ppms.api.PumapiRequest.Action;
import org.imagopole.ppms.api.PumapiRequest.Filter;
import org.imagopole.ppms.api.config.PumapiConfig;
import org.imagopole.ppms.api.convert.PumapiDataConverter;
import org.imagopole.ppms.api.convert.PumapiResponseConverterFactory;
import org.imagopole.ppms.api.dto.PpmsGroup;
import org.imagopole.ppms.api.dto.PpmsSystem;
import org.imagopole.ppms.api.dto.PpmsUser;
import org.imagopole.ppms.api.dto.PpmsUserPrivilege;
import org.imagopole.ppms.util.Check;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of a PPMS API (PUMAPI) client based on Apache HttpComponent's (fluent) HttpClient.
 *
 * @author seb
 *
 */
public class PumapiBufferedHttpClient implements PumapiClient {

    /** Application logs. */
    private final Logger log = LoggerFactory.getLogger(PumapiBufferedHttpClient.class);

    /** API configuration settings */
    private PumapiConfig config;

    /** Remote calls handler. */
    private PumapiHttpInvoker httpInvoker;

    /** Request to response type conversion/mappers lookup. */
    private PumapiResponseConverterFactory dataConverterFactory;

    /** Vanilla constructor. */
    public PumapiBufferedHttpClient() {
        super();
    }

    /**
     * Configuration-aware constructor.
     *
     * @param config API config
     */
    public PumapiBufferedHttpClient(PumapiConfig config) {
        super();

        Check.notNull(config, "config");
        this.config = config;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PumapiConfig getConfig() {
        return this.config;
    }

    /**
     * {@inheritDoc}
     */
    public void setConfig(PumapiConfig config) {
        this.config = config;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<String> getUsers(Boolean active) throws PumapiException {

        final PumapiRequest request = newRequest().forAction(Action.GetUsers);

        if (null != active) {
            request.withFilter(Filter.Active, active.toString().toLowerCase());
        }

        List<String> result = (List<String>) invokeAndConvert(request);

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PpmsUser getUser(String login) throws PumapiException {
        Check.notEmpty(login, "login");

        final PumapiRequest request =
            newRequest()
                .toCsv()
                .forAction(Action.GetUser)
                .withFilter(Filter.Login, login);

        PpmsUser result = (PpmsUser) invokeAndConvert(request);

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean authenticate(String login, String password) throws PumapiException {
        Check.notEmpty(login, "login");
        Check.notEmpty(password, "password");

        final PumapiRequest request =
            newRequest()
                .forAction(Action.Authenticate)
                .withFilter(Filter.Login, login)
                .withFilter(Filter.Password, password);

        Boolean result = (Boolean) invokeAndConvert(request);

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<PpmsUserPrivilege> getUserRights(String login) throws PumapiException {
        Check.notEmpty(login, "login");

        final PumapiRequest request =
            newRequest()
                .forAction(Action.GetUserRights)
                .withFilter(Filter.Login, login);

        // retrieve all available systems for the given user
        List<PpmsUserPrivilege> result = (List<PpmsUserPrivilege>) invokeAndConvert(request);

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<String> getGroups(Boolean active) throws PumapiException {

        final PumapiRequest request = newRequest().forAction(Action.GetGroups);

        if (null != active) {
            request.withFilter(Filter.Active, active.toString().toLowerCase());
        }

        List<String> result = (List<String>) invokeAndConvert(request);

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PpmsGroup getGroup(String unitLogin) throws PumapiException {
        Check.notEmpty(unitLogin, "unitLogin");

        final PumapiRequest request =
            newRequest()
                .toCsv()
                .forAction(Action.GetGroup)
                .withFilter(Filter.UnitLogin, unitLogin);

        PpmsGroup result = (PpmsGroup) invokeAndConvert(request);

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<PpmsSystem> getSystems() throws PumapiException {
        final PumapiRequest request = newRequest().forAction(Action.GetSystems);

        List<PpmsSystem> result = (List<PpmsSystem>) invokeAndConvert(request);

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PpmsSystem getSystem(Long systemId) throws PumapiException {
        // caution: the "getsystems" action returns _all_ systems when id <= 0
        Check.strictlyPositive(systemId, "systemId");

        final PumapiRequest request =
            newRequest()
                .toCsv()
                .forAction(Action.GetSystems)
                .withFilter(Filter.Id, systemId.toString());

        PpmsSystem result = (PpmsSystem) invokeAndConvert(request);

        return result;
    }

    /**
     * Builds a new PUMAPI request with minimal core/global parameters.
     *
     * @return a PumapiRequest with key-based authentication
     */
    private PumapiRequest newRequest() {
        return new PumapiRequest(getConfig()).withKeyAuth();
    }

    private Object invokeAndConvert(PumapiRequest pumapiRequest) throws PumapiException {
        Check.notNull(pumapiRequest, "pumapiRequest");

        PumapiDataConverter<String, ?> responseConverter =
                getDataConverterFactory().getConverter(pumapiRequest);

        return invokeAndConvert(pumapiRequest, responseConverter);
    }

    private Object invokeAndConvert(
                    PumapiRequest pumapiRequest,
                    PumapiDataConverter<String, ?> responseConverter) throws PumapiException {
        Check.notNull(pumapiRequest, "pumapiRequest");
        Check.notNull(responseConverter, "responseConverter");

        String responseContent = invoke(pumapiRequest);

        Object result = responseConverter.map(responseContent);

        return result;
    }

    /**
     * Performs the remote HTTP invocation.
     *
     * @param pumapiRequest the request
     * @return the response body as string
     * @throws PumapiException in case of an underlying failure (ie. originating
     * from the underlying HTTP client), or from an invalid API invocation (API error).
     */
    private String invoke(PumapiRequest pumapiRequest) throws PumapiException {
        Check.notNull(pumapiRequest, "pumapiRequest");

        if (log.isTraceEnabled()) {
            log.trace("[pumapi] Invoking PUMAPI request with action: {}", pumapiRequest.getAction());
        }

        String responseContent =
            getHttpInvoker().executePost(getConfig().getEndpoint(), pumapiRequest);

        log.trace("[pumapi] Received PUMAPI response: \n{}", responseContent);

        return responseContent;
    }

    /**
     * Returns httpInvoker.
     * @return the httpInvoker
     */
    public PumapiHttpInvoker getHttpInvoker() {
        return httpInvoker;
    }

    /**
     * Sets httpInvoker.
     * @param httpInvoker the httpInvoker to set
     */
    public void setHttpInvoker(PumapiHttpInvoker httpInvoker) {
        this.httpInvoker = httpInvoker;
    }

    /**
     * Returns dataConverterFactory.
     * @return the dataConverterFactory
     */
    public PumapiResponseConverterFactory getDataConverterFactory() {
        return dataConverterFactory;
    }

    /**
     * Sets dataConverterFactory.
     * @param dataConverterFactory the dataConverterFactory to set
     */
    public void setDataConverterFactory(PumapiResponseConverterFactory dataConverterFactory) {
        this.dataConverterFactory = dataConverterFactory;
    }

}
