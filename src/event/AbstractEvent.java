/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package event;

/**
 *
 * @author hp2k
 * @param <E>
 */
abstract public class AbstractEvent<E> {

    E item;

    public AbstractEvent(E item) {
        this.item = item;
    }

    public E getItem() {
        return item;
    }
}
