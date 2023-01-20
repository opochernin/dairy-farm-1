package ua.pochernin.repository;

import ua.pochernin.model.Cow;

import java.util.List;

public interface CowRepository {

    Cow findById(Integer id);

    List<Cow> findChildren(Integer parentId);

    void save(Cow cow);

}
