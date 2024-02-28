package KAgriFarm.KAgriFarm.model;

public enum EnumRole {
    USER(4),
    MANAGER(2),
    OWNER(1),
    HR(3),
    VISITORUSER(5);

    private final int value;

    EnumRole(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}