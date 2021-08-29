package hr.vbestak.authclient.model.common;

public class Pattern {
    public final static String EMAIL = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    public final static String PASSWORD = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,24}$";
}
