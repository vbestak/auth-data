package hr.vbestak.authclient.model.common;

public enum RoleType {
    SUPER_ADMIN("ROLE_SUPER_ADMIN"),
    ADMIN("ROLE_ADMIN"),
    STAFF("ROLE_STAFF"),
    SUPER_USER("ROLE_SUPER_USER"),
    USER("ROLE_USER");

    private String name;
    RoleType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
