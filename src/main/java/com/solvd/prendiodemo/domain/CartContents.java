package com.solvd.prendiodemo.domain;

import java.util.List;
import java.util.Objects;

public class CartContents {

    private List<String> partNumbers;
    private List<String> descriptions;
    private List<String> totals;

    public List<String> getPartNumbers() {
        return partNumbers;
    }

    public void setPartNumbers(List<String> partNumbers) {
        this.partNumbers = partNumbers;
    }

    public List<String> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(List<String> descriptions) {
        this.descriptions = descriptions;
    }

    public List<String> getTotals() {
        return totals;
    }

    public void setTotals(List<String> totals) {
        this.totals = totals;
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

        if (!Objects.equals(partNumbers, that.partNumbers)) {
            return false;
        }
        if (!Objects.equals(descriptions, that.descriptions)) {
            return false;
        }
        return Objects.equals(totals, that.totals);
    }

    public ItemContents getItemContents(int index) {
        return new ItemContents.ItemContentsBuilder()
                .setItemNumber(partNumbers.get(index))
                .setTitle(descriptions.get(index))
                .setPrice(totals.get(index)).build();
    }

    @Override
    public String toString() {
        return "CartContents{" +
                "partNumbers=" + partNumbers +
                ", descriptions=" + descriptions +
                ", totals=" + totals +
                '}';
    }
}
