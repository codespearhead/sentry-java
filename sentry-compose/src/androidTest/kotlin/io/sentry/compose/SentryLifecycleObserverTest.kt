package io.sentry.compose

import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import io.sentry.IHub
import io.sentry.android.navigation.SentryNavigationListener
import kotlin.test.Test

internal class SentryLifecycleObserverTest {

    class Fixture {
        val navListener = mock<SentryNavigationListener>()
        val hub = mock<IHub>()
        val navController = mock<NavController>()

        fun getSut(): SentryLifecycleObserver {
            return SentryLifecycleObserver(navController, hub, navListener)
        }
    }

    private val fixture = Fixture()

    @Test
    fun `onResume adds navigation listener`() {
        val sut = fixture.getSut()

        sut.onStateChanged(mock(), Lifecycle.Event.ON_RESUME)

        verify(fixture.navController).addOnDestinationChangedListener(fixture.navListener)
    }

    @Test
    fun `onPause removes navigation listener`() {
        val sut = fixture.getSut()

        sut.onStateChanged(mock(), Lifecycle.Event.ON_PAUSE)

        verify(fixture.navController).removeOnDestinationChangedListener(fixture.navListener)
    }

    @Test
    fun `dispose removes navigation listener`() {
        val sut = fixture.getSut()

        sut.dispose()

        verify(fixture.navController).removeOnDestinationChangedListener(fixture.navListener)
    }
}