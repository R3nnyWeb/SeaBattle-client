package com.r3nny.seabatlle.client.core.exeptions;

import com.r3nny.seabatlle.client.core.model.Cell;

public class CantCreateShipException extends Exception{

    public CantCreateShipException() {
    }

    public CantCreateShipException(String message) {
        super(message);
    }



}
