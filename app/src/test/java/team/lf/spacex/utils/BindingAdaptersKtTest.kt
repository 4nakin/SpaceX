package team.lf.spacex.utils

import org.junit.Test

import org.junit.Assert.*

class BindingAdaptersKtTest {

    //todo
    @Test
    fun shortText_returnsShortText() {
        val testString = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec a aliquet urna. Sed lacus est, vehicula at laoreet vitae, pharetra at justo. Nullam erat erat, aliquam id lacus in, condimentum imperdiet nibh. Phasellus eget nunc auctor, iaculis ex sit amet, vehicula est. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus a sollicitudin orci, eget facilisis mauris. Sed volutpat sem sed erat hendrerit iaculis."
        val shortText = shortText(20,testString)
        assertEquals(shortText.length, 23) /// + "..."
    }
}