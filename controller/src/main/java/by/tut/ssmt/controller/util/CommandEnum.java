package by.tut.ssmt.controller.util;

public enum CommandEnum {
    ADD("add"),
    ADD_PORTIONS("add_portions"),
    BULK_CHANGE_PORTION("bulk_change_portion"),
    DEFAULT("default"),
    DELETE("delete"),
    DELETE_FROM_MENU("delete_from_menu"),
    DELETE_USER("delete_user"),
    EDIT("edit"),
    EDIT_FORM("edit_form"),
    FORM("form"),
    LOGIN("login"),
    LOGOUT("logout"),
    MENU("menu"),
    REGISTER("register"),
    USER_LIST("user_list");

    private String name;

    CommandEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
