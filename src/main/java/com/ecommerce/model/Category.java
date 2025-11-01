package com.ecommerce.model;

public enum Category {
    ELECTRONICS("Eletrônicos", "fa-mobile-alt"),
    CLOTHING("Roupas", "fa-tshirt"),
    HOME_GARDEN("Casa & Jardim", "fa-chair"),
    SPORTS("Esportes", "fa-running"),
    TOYS("Brinquedos", "fa-puzzle-piece"),
    BEAUTY("Beleza", "fa-magic"),
    AUTOMOTIVE("Automotivo", "fa-car");

    private final String description;
    private final String icon;

    Category(String description, String icon) {
        this.description = description;
        this.icon = icon;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }
}
