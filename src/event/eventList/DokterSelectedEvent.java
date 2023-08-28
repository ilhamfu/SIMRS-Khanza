/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package event.eventList;

import event.AbstractEvent;
import model.Dokter;

/**
 *
 * @author hp2k
 */
public class DokterSelectedEvent extends AbstractEvent<Dokter> {

    public DokterSelectedEvent(Dokter item) {
        super(item);
    }
}
