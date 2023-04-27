package me.certainly1182.waterqualityapp.di

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import me.certainly1182.waterqualityapp.data.ReceiveManager
import me.certainly1182.waterqualityapp.data.ble.BLEReceiveManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import me.certainly1182.waterqualityapp.data.DataStoreManager
import javax.inject.Singleton
import me.certainly1182.waterqualityapp.util.dataStore

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideBluetoothAdapter(@ApplicationContext context: Context):BluetoothAdapter{
        val manager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        return manager.adapter
    }

    @Provides
    @Singleton
    fun provideTempHumidityReceiveManager(
        @ApplicationContext context: Context,
        bluetoothAdapter: BluetoothAdapter
    ):ReceiveManager{
        return BLEReceiveManager(bluetoothAdapter,context)
    }

    @Provides
    @Singleton
    fun provideDataStorePreferences(
        @ApplicationContext context: Context
    ):DataStore<Preferences> {
        return context.dataStore
    }

    @Provides
    @Singleton
    fun provideDataStoreManager(
        dataStore: DataStore<Preferences>
    ): DataStoreManager {
        return DataStoreManager(dataStore)
    }

    @Provides
    @Singleton
    fun provideCoroutineScope(): CoroutineScope = MainScope()
}