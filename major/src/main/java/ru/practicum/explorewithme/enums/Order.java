package ru.practicum.explorewithme.enums;

public enum Order {


    TIME_ASC("timeAsc"), TIME_DESC("timeDesc"),
    LIKES_ASC("likesAsc"), LIKES_DESC("likesDesc"),
    DISLIKES_ASC("dislikesAsc"), DISLIKES_DESC("dislikesDesc");

    private final String order;

    Order(String order) {
        this.order = order;
    }

    public String getOrder() {
        return order;
    }
}
