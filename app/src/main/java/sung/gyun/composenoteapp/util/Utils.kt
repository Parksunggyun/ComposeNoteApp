package sung.gyun.composenoteapp.util

import android.content.res.Configuration
import androidx.compose.ui.platform.LocalConfiguration

object Utils {

    fun screenWidth(config: Configuration) = config.screenWidthDp
    fun screenHeight(config: Configuration) = config.screenHeightDp
    fun isLargeScreen(config: Configuration) = config.screenWidthDp > 600

    fun isPortrait(orientation: Int) = orientation == Configuration.ORIENTATION_PORTRAIT

}