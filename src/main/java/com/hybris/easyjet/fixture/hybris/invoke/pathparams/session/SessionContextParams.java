package com.hybris.easyjet.fixture.hybris.invoke.pathparams.session;

import com.hybris.easyjet.fixture.hybris.invoke.pathparams.PathParameters;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import static com.hybris.easyjet.fixture.hybris.invoke.pathparams.session.SessionContextParams.SessionContextPaths.DEFAULT;

/**
 * Created by albertowork on 5/24/17.
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class SessionContextParams extends PathParameters {

    private SessionContextPaths path;

    @Override
    public String get() {

        if (path == null) {
            path = DEFAULT;
        }

        switch (this.path) {
            case RESET_SESSION:
                return "/reset-agent-session-request";
            case KEEP_SESSION_ALIVE:
                return "/keep-session-alive-request";
            default:
                return "";
        }
    }

    public enum SessionContextPaths {
        DEFAULT,
        RESET_SESSION,
        KEEP_SESSION_ALIVE
    }
}
