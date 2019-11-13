package ru.crew4dev.medicinechest.di.module.application

import android.content.Context
import android.content.res.AssetManager
import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.crew4dev.medicinechest.app.device.DeviceManager
import ru.crew4dev.medicinechest.app.file.FilePickerProvider
import ru.crew4dev.medicinechest.app.file.PhotoCompressor
import ru.crew4dev.medicinechest.app.notification.NotificationProvider
import ru.crew4dev.medicinechest.app.permission.PermissionProvider
//import ru.crew4dev.medicinechest.app.print.PrintProvider
//import ru.crew4dev.medicinechest.app.push.FirebasePushManager
//import ru.crew4dev.medicinechest.data.providers.SmartEngineProviderImpl
import ru.crew4dev.medicinechest.data.providers.UserDataProviderImpl
import ru.crew4dev.medicinechest.data.repos.AppDataRepo
import ru.crew4dev.medicinechest.data.storage.ObjectDataTransformer
import ru.crew4dev.medicinechest.data.storage.file.FileDataStorage
import ru.crew4dev.medicinechest.data.storage.keyvalue.SharedPrefsStorage
import ru.crew4dev.medicinechest.data.storage.keyvalue.SharedPrefsStorageImpl
import ru.crew4dev.medicinechest.di.scope.ApplicationScope
//import ru.crew4dev.medicinechest.domain.providers.SmartEngineProvider
import ru.crew4dev.medicinechest.domain.providers.UserDataProvider
import ru.crew4dev.medicinechest.domain.repos.AppRepo
import ru.crew4dev.medicinechest.domain.storage.ObjectTransformer
import ru.crew4dev.medicinechest.domain.storage.file.FileStorage
//import ru.crew4dev.medicinechest.domain.usecases.AppUseCase
//import ru.crew4dev.medicinechest.domain.usecases.PushUseCase

@Module
abstract class ApplicationModule {

    @Binds
    @ApplicationScope
    abstract fun provideObjectTransformer(objectDataTransformer: ObjectDataTransformer): ObjectTransformer
/*
    @Binds
    @ApplicationScope
    abstract fun provideUserInfoProvider(userInfoDataProvider: UserDataProviderImpl): UserDataProvider

    @Binds
    @ApplicationScope
    abstract fun provideApplicationRepo(applicationDataRepo: AppDataRepo): AppRepo

    @Binds
    @ApplicationScope
    abstract fun provideSmartEngineProvider(smartEngineProvider: SmartEngineProviderImpl): SmartEngineProvider
*/
    @Module
    companion object {

        @JvmStatic
        @Provides
        @ApplicationScope
        fun provideSharedPrefsStorage(appContext: Context): SharedPrefsStorage {
            return SharedPrefsStorageImpl(appContext)
        }

        @JvmStatic
        @Provides
        @ApplicationScope
        fun provideAssetsManager(appContext: Context): AssetManager {
            return appContext.assets
        }

        @JvmStatic
        @Provides
        @ApplicationScope
        fun provideFileStorage(appContext: Context): FileStorage {
            return FileDataStorage(appContext)
        }
/*
        @JvmStatic
        @Provides
        @ApplicationScope
        fun providePermissionProvider(appContext: Context): PermissionProvider {
            return PermissionProvider(appContext)
        }

        @JvmStatic
        @Provides
        @ApplicationScope
        fun providePrintProvider(appContext: Context): PrintProvider {
            return PrintProvider(appContext)
        }

        @JvmStatic
        @Provides
        @ApplicationScope
        fun providePushManager(): FirebasePushManager {
            return FirebasePushManager()
        }

        @JvmStatic
        @Provides
        @ApplicationScope
        fun provideDeviceManager(appContext: Context): DeviceManager {
            return DeviceManager(appContext)
        }

        @JvmStatic
        @Provides
        @ApplicationScope
        fun provideNotificationProvider(appContext: Context): NotificationProvider {
            return NotificationProvider(appContext)
        }

        @JvmStatic
        @Provides
        @ApplicationScope
        fun provideFilePickerProvider(): FilePickerProvider {
            return FilePickerProvider()
        }

        @JvmStatic
        @Provides
        @ApplicationScope
        fun provideAppUseCase(
            appRepo: AppRepo/*,
            deviceManager: DeviceManager,
            userDataProvider: UserDataProvider,
            smartEngineProvider: SmartEngineProvider*/
        ): AppUseCase {
            return AppUseCase(appRepo/*, deviceManager, userDataProvider, smartEngineProvider*/)
        }

        @JvmStatic
        @Provides
        @ApplicationScope
        fun providePushUseCase(
            appRepo: AppRepo
        ): PushUseCase {
            return PushUseCase(appRepo)
        }

        @JvmStatic
        @Provides
        @ApplicationScope
        fun providePhotoCompressor(
            appContext: Context,
            fileStorage: FileStorage
        ): PhotoCompressor {
            return PhotoCompressor(appContext, fileStorage)
        }
 */
    }
}