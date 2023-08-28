/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fungsi;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 *
 * @author hp2k
 */
public class HUtil {

    public static <T> void consume(T val, Consumer<T> consumer) {
        consumer.accept(val);
    }

    public static <R, T> R let(T val, Function<T, R> func) {
        return func.apply(val);
    }

    public static boolean coba(int n, Supplier<Boolean> supplier) {
        return coba(n, supplier, () -> {

        });
    }

    public static boolean coba(int n, Supplier<Boolean> supplier, Runnable on_fail) {
        for (int i = 0; i < n; i++) {
            if (supplier.get()) {
                return true;
            }
            on_fail.run();
        }
        return false;
    }

    public static double fpb(double a, double b) {
        if (b == 0) {
            return a;
        }
        return fpb(b, a % b);
    }

    public static String padLeft(String text, int n, char pad) {
        return String.format("%1$" + n + "s", text).replace(' ', pad);
    }

    public static String padLeft(String text, int n) {
        return padLeft(text, n, ' ');
    }

}
