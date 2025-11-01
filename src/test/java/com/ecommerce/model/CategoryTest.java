package com.ecommerce.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CategoryTest {

    @Test
    void testCategoryDescription() {
        Assertions.assertEquals("Eletrônicos", Category.ELECTRONICS.getDescription());
        Assertions.assertEquals("Roupas", Category.CLOTHING.getDescription());
        Assertions.assertEquals("Casa & Jardim", Category.HOME_GARDEN.getDescription());
        Assertions.assertEquals("Esportes", Category.SPORTS.getDescription());
        Assertions.assertEquals("Brinquedos", Category.TOYS.getDescription());
        Assertions.assertEquals("Beleza", Category.BEAUTY.getDescription());
        Assertions.assertEquals("Automotivo", Category.AUTOMOTIVE.getDescription());
    }

    @Test
    void testCategoryIcon() {
        Assertions.assertEquals("fa-mobile-alt", Category.ELECTRONICS.getIcon());
        Assertions.assertEquals("fa-tshirt", Category.CLOTHING.getIcon());
        Assertions.assertEquals("fa-chair", Category.HOME_GARDEN.getIcon());
        Assertions.assertEquals("fa-running", Category.SPORTS.getIcon());
        Assertions.assertEquals("fa-puzzle-piece", Category.TOYS.getIcon());
        Assertions.assertEquals("fa-magic", Category.BEAUTY.getIcon());
        Assertions.assertEquals("fa-car", Category.AUTOMOTIVE.getIcon());
    }

}
