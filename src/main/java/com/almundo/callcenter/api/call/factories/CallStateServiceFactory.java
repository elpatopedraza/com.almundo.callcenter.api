package com.almundo.callcenter.api.call.factories;

import com.almundo.callcenter.api.call.model.CallState;
import com.almundo.callcenter.api.call.services.ICallStateMachineService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Class that acts as a Factory for each call state transition.
 *
 * @author Andrés Felipe Pedraza Infante.
 * @version 0.0.1
 */
@Component
@Slf4j
public class CallStateServiceFactory
{
    /**
     * The initial call state service transition name.
     */
    public static final String TO_CREATED_CALL_STATE_SERVICE_BEAN_NAME =
            "toCreatedCallStateService";

    /**
     * The created to in progress call state service transition name.
     */
    public static final String CREATED_TO_IN_PROGRESS_CALL_STATE_SERVICE_BEAN_NAME =
            "createdToInProgressCallStateService";

    /**
     * The created to queued call state service transition name.
     */
    public static final String CREATED_TO_QUEUED_CALL_STATE_SERVICE_BEAN_NAME =
            "createdToQueuedCallStateService";

    /**
     * The queued to in progress state service transition name.
     */
    public static final String QUEUED_TO_IN_PROGRESS_CALL_STATE_SERVICE_BEAN_NAME =
            "queuedToInProgressCallStateService";

    /**
     * The queued to queued call state service transition name.
     */
    public static final String QUEUED_TO_QUEUED_CALL_STATE_SERVICE_BEAN_NAME =
            "queuedToIQueuedCallStateService";

    /**
     * The in progress to finished call state service transition name.
     */
    public static final String IN_PROGRESS_TO_FINISHED_CALL_STATE_SERVICE_BEAN_NAME =
            "inProgressToFinishedCallStateService";

    /**
     * The in progress to queued call state service transition name.
     */
    public static final String IN_PROGRESS_TO_QUEUED_CALL_STATE_SERVICE_BEAN_NAME =
            "inProgressToQueuedCallStateService";

    /**
     * The spring bean factory.
     */
    private final BeanFactory beanFactory;

    /**
     * Constructs a call state service factory object.
     *
     * @param beanFactory the spring bean factory to inject into the current call state service factory.
     */
    @Autowired
    public CallStateServiceFactory(BeanFactory beanFactory)
    {
        this.beanFactory = beanFactory;
    }

    /**
     * Gets the appropriate call state service according to the initial and final call state.
     *
     * @param initialState the initial state of a call.
     * @param finalState the final state of a call.
     * @return the appropriate call state service for given states.
     */
    public ICallStateMachineService getCallStateService(CallState initialState, CallState finalState)
    {
        ICallStateMachineService callStateMachineService = getCallStateServiceFromInitialAndFinalStates(
                initialState, finalState);

        if(callStateMachineService == null) {
            RuntimeException exception = new RuntimeException("Invalid call state transition");
            log.error("Invalid call state transition between [{}] and [{}] states.",
                    initialState, finalState, exception);

            throw exception;
        }

        return callStateMachineService;
    }

    /**
     * Gets the appropriate call state service according to the initial and final call state.
     *
     * @param initialState the initial state of a call.
     * @param finalState the final state of a call.
     * @return the appropriate call state service for given states.
     */
    private ICallStateMachineService getCallStateServiceFromInitialAndFinalStates(CallState initialState,
                                                                                  CallState finalState)
    {
        ICallStateMachineService callStateMachineService = null;

        if(initialState == null && finalState.equals(CallState.CREATED)) {
            callStateMachineService = beanFactory.getBean(
                    TO_CREATED_CALL_STATE_SERVICE_BEAN_NAME, ICallStateMachineService.class);
        } else {
            switch(initialState) {
                case CREATED:
                    switch(finalState) {
                        case IN_PROGRESS:
                            callStateMachineService = beanFactory.getBean(
                                    CREATED_TO_IN_PROGRESS_CALL_STATE_SERVICE_BEAN_NAME, ICallStateMachineService.class);
                            break;

                        case QUEUED:
                            callStateMachineService = beanFactory.getBean(
                                    CREATED_TO_QUEUED_CALL_STATE_SERVICE_BEAN_NAME, ICallStateMachineService.class);
                            break;
                    }

                    break;

                case QUEUED:
                    switch(finalState) {
                        case IN_PROGRESS:
                            callStateMachineService = beanFactory.getBean(
                                    QUEUED_TO_IN_PROGRESS_CALL_STATE_SERVICE_BEAN_NAME, ICallStateMachineService.class);
                            break;

                        case QUEUED:
                            callStateMachineService = beanFactory.getBean(
                                    QUEUED_TO_QUEUED_CALL_STATE_SERVICE_BEAN_NAME, ICallStateMachineService.class);
                            break;
                    }

                    break;

                case IN_PROGRESS:
                    switch(finalState) {
                        case FINISHED:
                            callStateMachineService = beanFactory.getBean(
                                    IN_PROGRESS_TO_FINISHED_CALL_STATE_SERVICE_BEAN_NAME, ICallStateMachineService.class);
                            break;

                        case QUEUED:
                            callStateMachineService = beanFactory.getBean(
                                    IN_PROGRESS_TO_QUEUED_CALL_STATE_SERVICE_BEAN_NAME, ICallStateMachineService.class);
                            break;
                    }

                    break;
            }
        }

        return callStateMachineService;
    }
}
