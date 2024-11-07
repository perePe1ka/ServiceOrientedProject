package ru.vladuss.serviceorientedproject.constants;


public enum Status {
    ORDERED(0),
    CONFIRMED(1),
    ASSEMBLING(2),
    PACKED(3),
    SHIPPED(4),
    IN_TRANSIT(5),
    AWAITING_PICKUP(6),
    DELIVERED(7),
    CANCELED(8),
    RETURNED(9),

    NO_STATUS(10);

    private final int code;

    Status(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}

