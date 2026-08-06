// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration;

import org.apache.commons.lang.StringUtils;
import ru.korusconsulting.service.IService;

public interface IEdoIntegrationService extends IService
{
    String getLogPrefix();
    
    String getIntegrationLogAspect();
    
    boolean isInstalled();
    
    String getCstmIntegrationId();
    
    public enum EDO_OPERATION_PARAMS_NAMES
    {
        CHECK_START_DATE, 
        CATEGORY, 
        IDS, 
        STATUSES, 
        GUIDS, 
        DOCUMENTS_LIMIT, 
        FILIALS;
    }
    
    public enum EDO_OPERATION_NAMES
    {
        CHECK_INCOMING_DOCUMENTS(EDO_OPERATION_PARAMS_NAMES.CHECK_START_DATE, EDO_OPERATION_PARAMS_NAMES.STATUSES), 
        PROCESS_INCOMING_DOCUMENTS, 
        GET_REMOTE_STATES(EDO_OPERATION_PARAMS_NAMES.CATEGORY), 
        PROCESS_REMOTE_STATE_CHANGES(EDO_OPERATION_PARAMS_NAMES.CATEGORY), 
        CHECK_LOCAL_STATUS(EDO_OPERATION_PARAMS_NAMES.CATEGORY), 
        CHECK_STATUS(EDO_OPERATION_PARAMS_NAMES.CATEGORY), 
        PROCESS_REMOTE_STATUS(EDO_OPERATION_PARAMS_NAMES.CATEGORY), 
        QUEUE_TEST_DOCUMENT(true), 
        PROCESS_FOR_SEND_QUEUE(EDO_OPERATION_PARAMS_NAMES.CATEGORY, EDO_OPERATION_PARAMS_NAMES.IDS), 
        CHECK_EVENTS, 
        PROCESS_EVENTS, 
        SET_EDO_USER_TAB_NUM(true), 
        GET_EDO_USER_LIST(true), 
        CHECK_COUNTERAGENT(true), 
        SLEEP(true), 
        CHECK_REMOTE_STATUS(EDO_OPERATION_PARAMS_NAMES.CATEGORY), 
        SEND_REVOKE(true), 
        INFO(true), 
        SEND_FREE_REQUEST(true), 
        CREATE_REVOKE(true), 
        GET_COUNTERAGENT_BY_INN(true), 
        GET_ORGANIZATIONS_BY_INN(true), 
        FIRE_SERVER_EVENT(true), 
        GET_REMOTE_DOCUMENT(true);
        
        private final EDO_OPERATION_PARAMS_NAMES[] paramNames;
        private boolean isServiceOperation;
        
        public boolean isServiceOperation() {
            return this.isServiceOperation;
        }
        
        public boolean isNonServiceOperation() {
            return !this.isServiceOperation;
        }
        
        private EDO_OPERATION_NAMES() {
            this.paramNames = new EDO_OPERATION_PARAMS_NAMES[0];
        }
        
        private EDO_OPERATION_NAMES(final boolean isServiceOperation) {
            this.isServiceOperation = isServiceOperation;
            this.paramNames = new EDO_OPERATION_PARAMS_NAMES[0];
        }
        
        private EDO_OPERATION_NAMES(final EDO_OPERATION_PARAMS_NAMES paramName) {
            this.paramNames = new EDO_OPERATION_PARAMS_NAMES[] { paramName };
        }
        
        private EDO_OPERATION_NAMES(final EDO_OPERATION_PARAMS_NAMES paramName1, final EDO_OPERATION_PARAMS_NAMES paramName2) {
            this.paramNames = new EDO_OPERATION_PARAMS_NAMES[] { paramName1, paramName2 };
        }
        
        public static EDO_OPERATION_NAMES parseValue(final String value) {
            for (final EDO_OPERATION_NAMES a : values()) {
                if (a.name().equalsIgnoreCase(StringUtils.trimToNull(value))) {
                    return a;
                }
            }
            return null;
        }
        
        public EDO_OPERATION_PARAMS_NAMES[] getParamNames() {
            return this.paramNames;
        }
    }
    
    public enum EXCHANGE_DIRECTION
    {
        IN, 
        OUT, 
        CHECK_STATE;
    }
    
    public enum EXCHANGE_OPERATION
    {
        QUEUE, 
        SEND, 
        UPLOAD_CONTENT, 
        GET_DOCFLOWS, 
        CHECK_STATE, 
        CHANGES_DETECTED, 
        CHECK_INCOMING, 
        CREAT_INCOMING, 
        CREATE_INCOMING;
    }
}
