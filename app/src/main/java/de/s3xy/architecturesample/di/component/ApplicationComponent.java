package de.s3xy.architecturesample.di.component;

import dagger.Component;
import de.s3xy.architecturesample.di.scope.ApplicationScope;
import de.s3xy.architecturesample.network.NetworkModule;

/**
 * @author Angelo Rüggeberg <s3xy4ngc@googlemail.com>
 */

@ApplicationScope
@Component(modules = {NetworkModule.class})
public interface ApplicationComponent {
}
