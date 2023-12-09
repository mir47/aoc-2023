package util

import kotlin.test.Test
import kotlin.test.assertEquals

class MathUtilsTest {

    @Test
    fun lcm() {
        assertEquals(12, lcm(4, 6))
    }

    @Test
    fun gcd() {
        assertEquals(2, gcd(4, 6))
    }
}
