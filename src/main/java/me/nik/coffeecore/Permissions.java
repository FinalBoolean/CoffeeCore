package me.nik.coffeecore;

public enum Permissions {
    ADMIN("coffee.admin");

    private final String permission;

    Permissions(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return this.permission;
    }
}