package com.example.phase2.items;

import java.io.Serializable;

public enum ItemStatus implements Serializable {
    AVAILABLE,
    REQUESTED,
    UNAVAILABLE,
    INACTIVE_REQ,
    INACTIVE_AVA,
    REMOVED,
    FROZEN,
    FROZEN_REQ
}
