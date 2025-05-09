package util;

public enum UserRole {
    ADMIN,
    MANAGER,
    STAFF,
    GUEST;


    public String toString() {
        return name();  // This will return the string "ADMIN" or "USER"
    }
}
