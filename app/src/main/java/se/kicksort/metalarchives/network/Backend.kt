package se.kicksort.metalarchives.network

import android.util.Log

import java.net.InetAddress
import java.net.NetworkInterface
import java.net.SocketException
import java.net.UnknownHostException
import java.util.Enumeration

object Backend {
    val baseUrl = "http://192.168.1.62:3001/"

    private val localIpAddress: String
        @Throws(SocketException::class, UnknownHostException::class)
        get() {
            var result: InetAddress? = null
            val interfaces = NetworkInterface.getNetworkInterfaces()
            while (interfaces.hasMoreElements()) {
                val addresses = interfaces.nextElement().inetAddresses
                while (addresses.hasMoreElements()) {
                    val address = addresses.nextElement()
                    if (!address.isLoopbackAddress) {
                        if (address.isSiteLocalAddress) {
                            return address.hostAddress
                        } else if (result == null) {
                            result = address
                        }
                    }
                }
            }

            return (if (result != null) result else InetAddress.getLocalHost()).hostAddress
        }
}
