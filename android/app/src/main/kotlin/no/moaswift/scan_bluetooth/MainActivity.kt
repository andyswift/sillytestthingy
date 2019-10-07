package no.moaswift.scan_bluetooth

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.le.AdvertiseCallback
import android.bluetooth.le.AdvertiseData
import android.bluetooth.le.AdvertiseSettings
import android.bluetooth.le.BluetoothLeAdvertiser
import android.content.Context
import android.os.Bundle
import android.os.ParcelUuid

import io.flutter.app.FlutterActivity
import io.flutter.plugins.GeneratedPluginRegistrant
import java.util.*

class MainActivity : FlutterActivity() {

    private val bluetoothAdapter: BluetoothAdapter? by lazy(LazyThreadSafetyMode.NONE) {
        val bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothManager.adapter
    }


    private val mBluetoothLeAdvertiser: BluetoothLeAdvertiser by lazy(LazyThreadSafetyMode.NONE) {
        bluetoothAdapter!!.bluetoothLeAdvertiser
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GeneratedPluginRegistrant.registerWith(this)
    }

    /*
     * Initialize the advertiser
     */
    private fun startAdvertising() {
        if (bluetoothAdapter == null) return

        val settings = AdvertiseSettings.Builder()
                .setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_BALANCED)
                .setConnectable(true)
                .setTimeout(0)
                .setTxPowerLevel(AdvertiseSettings.ADVERTISE_TX_POWER_MEDIUM)
                .build()

        val data = AdvertiseData.Builder()
                .setIncludeDeviceName(true)
                .addServiceUuid(ParcelUuid(UUID.randomUUID()))
                .build()

        mBluetoothLeAdvertiser.startAdvertising(settings, data, advertiseCallback)
    }

    /*
     * Terminate the advertiser
     */
    private fun stopAdvertising() {
        mBluetoothLeAdvertiser.stopAdvertising(advertiseCallback)
    }

    /*
     * Callback handles events from the framework describing
     * if we were successful in starting the advertisement requests.
     */
    private val advertiseCallback = object : AdvertiseCallback() {
        override fun onStartSuccess(settingsInEffect: AdvertiseSettings) {
            print("Peripheral Advertise Started.")
        }

        override fun onStartFailure(errorCode: Int) {
            print("Peripheral Advertise Failed: $errorCode")
        }
    }
}