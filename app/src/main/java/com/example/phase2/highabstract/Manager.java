package com.example.phase2.highabstract;

import java.io.Serializable;

public abstract class Manager implements Serializable{

    /**
     * Getter for the identifier of this Use Case class
     * @return A string representing the Manager's identifier
     */
    public abstract String getIdentifier();
}
