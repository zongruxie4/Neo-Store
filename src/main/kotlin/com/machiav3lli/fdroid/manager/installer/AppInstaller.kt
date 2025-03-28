package com.machiav3lli.fdroid.manager.installer

import android.content.Context
import com.machiav3lli.fdroid.data.content.Preferences
import com.machiav3lli.fdroid.data.entity.InstallerType
import com.machiav3lli.fdroid.utils.amInstalled
import com.machiav3lli.fdroid.utils.getHasSystemInstallPermission
import com.machiav3lli.fdroid.utils.shellIsRoot
import org.koin.dsl.module

abstract class AppInstaller {
    abstract val defaultInstaller: BaseInstaller

    companion object {
        @Volatile
        private var INSTANCE: AppInstaller? = null
        fun getInstance(context: Context): AppInstaller {
            return INSTANCE ?: synchronized(this) {
                val instance = object : AppInstaller() {
                    override val defaultInstaller: BaseInstaller
                        get() {
                            val installer = Preferences[Preferences.Key.Installer].installer
                            return when {
                                installer == InstallerType.SYSTEM && context.getHasSystemInstallPermission()
                                     -> SystemInstaller(context)

                                installer == InstallerType.ROOT && shellIsRoot
                                     -> RootInstaller(context)

                                installer == InstallerType.LEGACY
                                     -> LegacyInstaller(context)

                                installer == InstallerType.AM && context.amInstalled
                                     -> AppManagerInstaller(context)

                                else -> SessionInstaller(context)
                            }
                        }
                }
                INSTANCE = instance
                instance
            }
        }
    }
}


val installerModule = module {
    single { AppInstaller.getInstance(get()) }
    factory { SystemInstaller(get()) }
    factory { RootInstaller(get()) }
    factory { LegacyInstaller(get()) }
    factory { AppManagerInstaller(get()) }
    factory { SessionInstaller(get()) }
}
