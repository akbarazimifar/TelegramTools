package me.nextgeneric.telegram.dump.state;


import java.util.Collections;
import java.util.List;

/**
 * Represents a current state of the channel Dump.
 *
 * @see DoneState
 * @see FailureState
 * @see FetchingMessagesState
 * @see FileCreatingState
 * @see NoNewMessagesState
 * @see SearchingChannelState
 */
public interface ChannelDumpState {

    /**
     * A service (system) description of the state. Unlike public one, it's generally not intended to be presented
     * to the users and used for internal purposes, though sometimes can be shown in a view layer to indicate a problem
     * so that it could be reported to admins further.
     *
     * @return service description of the state.
     */
    String errorDescription();

    /**
     * Internalized public description of the state than is intended only for presenting to users. It contains user-friendly
     * and completely readable description of the task that can be clearly understood by service user.
     *
     * @return public, user-friendly description of the state
     */
    String publicDescription();

    /**
     * List of state variables that are substituted to a internalized message.
     *
     * @return variables to be displayed in an internalized view
     */
    default List<Object> getStateVariables() {
        return Collections.emptyList();
    }

    default boolean isDone() {
        return false;
    }

    default String getFinalDescription() {
        return isDone() ? publicDescription() : errorDescription();
    }
}
