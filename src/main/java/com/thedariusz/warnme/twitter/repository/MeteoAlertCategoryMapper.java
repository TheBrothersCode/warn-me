package com.thedariusz.warnme.twitter.repository;

import com.thedariusz.warnme.MeteoAlertCategory;
import com.thedariusz.warnme.MeteoAlertCategoryEntity;

import java.util.Set;
import java.util.stream.Collectors;

public class MeteoAlertCategoryMapper {
    public Set<String> toModel(Set<MeteoAlertCategoryEntity> entities) {
        return entities.stream()
                .map(entity -> entity.getName())
                .collect(Collectors.toSet());
    }

    public Set<MeteoAlertCategoryEntity> toEntity(Set<String> categories) {
        return categories.stream()
                .map(MeteoAlertCategoryEntity::new)
                .collect(Collectors.toSet());
    }
}
