import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HorseTest {
    @Test
    public void testHorse_whenFirstArgumentIsNull() {
        assertThrows(IllegalArgumentException.class,
                () -> new Horse(null, 1, 1));
    }

    @Test
    public void testHorse_whenFirstArgumentIsNull_CheckExceptionMessage() {
        try {
            new Horse(null, 1, 1);
        } catch (IllegalArgumentException e) {
            assertEquals("Name cannot be null.", e.getMessage());
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "\t", "\n", " "})
    void testHorse_whenFirstArgumentIsSpace(String str) {
        assertThrows(IllegalArgumentException.class, () -> new Horse(str, 1, 1));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "\t", "\n", " "})
    void testHorse_whenFirstArgumentIsSpace_CheckExceptionMessage(String str) {
        try {
            new Horse(str, 1, 1);
        } catch (IllegalArgumentException e) {
            assertEquals("Name cannot be blank.", e.getMessage());
        }
    }

    @Test
    public void testHorse_whenSecondArgumentIsNotPositive() {
        assertThrows(IllegalArgumentException.class,
                () -> new Horse("Some Horse", -1, 1));
    }

    @Test
    public void testHorse_whenSecondArgumentIsNotPositive_CheckExceptionMessage() {
        try {
            new Horse("Some Horse", -1, 1);
        } catch (IllegalArgumentException e) {
            assertEquals("Speed cannot be negative.", e.getMessage());
        }
    }

    @Test
    public void testHorse_whenThirdArgumentIsNotPositive() {
        assertThrows(IllegalArgumentException.class,
                () -> new Horse("Some Horse", 1, -1));
    }

    @Test
    public void testHorse_whenThirdArgumentIsNotPositive_CheckExceptionMessage() {
        try {
            new Horse("Some Horse", 1, -1);
        } catch (IllegalArgumentException e) {
            assertEquals("Distance cannot be negative.", e.getMessage());
        }
    }

    @Test
    void testGetName() {
        String expected = "Some Horse";
        assertEquals(expected, new Horse(expected, 1, 1).getName());
    }

    @Test
    void testGetSpeed() {
        double expected = 10;
        assertEquals(expected, new Horse("Some Horse", expected, 1).getSpeed());
    }

    @Test
    void testGetDistance() {
        double expected = 10;
        assertEquals(expected, new Horse("Some Horse", 1, expected).getDistance());
    }

    @Test
    void testGetDistanceDefault() {
        assertEquals(0, new Horse("Some Horse", 1).getDistance());
    }

    @Test
    void testMoveGetRandomDouble() {
        try (MockedStatic<Horse> horseMockedStatic = Mockito.mockStatic(Horse.class)) {
            new Horse("Some Horse", 1, 1).move();
            horseMockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.1, 0.2, 0.5, 0.9, 1.1, 99, 105.9})
    void testGetRandom(double random){
        try(MockedStatic<Horse> horseMockedStatic = Mockito.mockStatic(Horse.class)) {
           Horse horse = new Horse("Some Horse", 3, 5);
            horseMockedStatic.when(()->Horse.getRandomDouble(0.2, 0.9)).thenReturn(random);
            horse.move();
            assertEquals(5+3*random, horse.getDistance());
        }
    }
}