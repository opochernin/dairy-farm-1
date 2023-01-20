package ua.pochernin.repository;

import org.springframework.stereotype.Component;
import ua.pochernin.model.Cow;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CowRepositoryImpl implements CowRepository {

    private final List<Cow> cows = new LinkedList<>();

    public CowRepositoryImpl() {
        final Cow root = new Cow();
        root.setCowId(0);
        root.setNickName("Root Cow");
        cows.add(root);
    }

    @Override
    public Cow findById(Integer id) {
        return cows.stream()
                .filter(cow -> cow.getCowId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Cow> findChildren(Integer parentId) {
        return cows.stream()
                .filter(cow -> cow.getParentCowId() != null)
                .filter(cow -> cow.getParentCowId().equals(parentId))
                .collect(Collectors.toList());
    }

    @Override
    public void save(Cow cow) {
        cows.add(cow);
    }
}
