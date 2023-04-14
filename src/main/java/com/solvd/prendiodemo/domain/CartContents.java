package com.solvd.prendiodemo.domain;

import java.util.List;
import java.util.Objects;

public class CartContents {

    private List<ItemContents> items;

    public CartContents(List<ItemContents> items) {
        this.items = items;
    }

    public List<ItemContents> getItems() {
        return items;
    }

    public void setItems(List<ItemContents> items) {
        this.items = items;
    }

    @Override
    public int hashCode() {
        return items != null ? items.hashCode() : 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CartContents that = (CartContents) o;

        return Objects.equals(items, that.items);
    }

    @Override
    public String toString() {
        return "CartContents{" +
                "items=" + items +
                '}';
    }
}
