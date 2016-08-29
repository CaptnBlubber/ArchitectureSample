package de.s3xy.architecturesample.network;

import dagger.Subcomponent;
import de.s3xy.architecturesample.di.scope.AuthScope;
import de.s3xy.architecturesample.login.ui.LoginActivity;

/**
 * @author Andrey Bondarenko <andrey.cooper@gmail.com>
 */
@AuthScope
@Subcomponent(modules = {NetworkAuthModule.class, AuthPresenterModule.class})
public interface AuthComponent {
    void inject(LoginActivity loginActivity);
}
