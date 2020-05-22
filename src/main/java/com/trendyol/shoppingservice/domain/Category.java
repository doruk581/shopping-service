package com.trendyol.shoppingservice.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
public class Category {

    private CategoryId id;
    private Category category;

    public Category(CategoryId id, Category category) {
        this.id = id;
        this.category = category;
    }

    public Boolean isHaveParentCategory(final Category category) {
        return category != null;
    }

    public Category getParentCategory() {
        return category;
    }

    public Set<CategoryId> getBelongingCategories() {
        final Set<CategoryId> categorySet = new HashSet<>();
        categorySet.add(this.id);

        Category category = this;
        while (isHaveParentCategory(category)) {
            category = category.getParentCategory();
            if (category == null)
                break;
            categorySet.add(category.id);
        }
        return categorySet;
    }
}
