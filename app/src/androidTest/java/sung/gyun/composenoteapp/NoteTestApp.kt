package sung.gyun.composenoteapp

import android.app.Application
import dagger.hilt.android.testing.CustomTestApplication

@CustomTestApplication(Application::class)
interface NoteTestApp