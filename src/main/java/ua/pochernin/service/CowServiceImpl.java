package ua.pochernin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.pochernin.exception.CowServiceException;
import ua.pochernin.model.Cow;
import ua.pochernin.repository.CowRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class CowServiceImpl implements CowService {

    private final CowRepository cowRepository;

    @Override
    public void giveBirth(Integer parentCowId, Integer childCowId, String childNickName) {
        if (cowRepository.findById(childCowId) != null) {
            throw new CowServiceException(format("A cow with id=%s already exists.", childCowId));
        }

        final Cow parentCow = cowRepository.findById(parentCowId);

        if (parentCow == null) {
            throw new CowServiceException(format("A parent cow with id=%s does not exist.", parentCowId));
        }

        if (!parentCow.isAlive()) {
            throw new CowServiceException(format("A parent cow with id=%s is not alive.", parentCowId));
        }

        final Cow cow = new Cow();
        cow.setCowId(childCowId);
        cow.setParentCowId(parentCowId);
        cow.setNickName(childNickName);
        cowRepository.save(cow);
    }

    @Override
    public void endLifeSpan(Integer cowId) {
        final Cow cow = cowRepository.findById(cowId);

        if (!cow.isAlive()) {
            throw new CowServiceException(format("A cow with id=%s is not alive.", cowId));
        }

        cow.setAlive(false);
    }

    @Override
    public String getFarmDataAsString() {
        return getCowAsString(cowRepository.findById(0), 0).toString();
    }

    private StringBuilder getCowAsString(Cow cow, int depth) {
        final StringBuilder sb = new StringBuilder();

        String prefix = IntStream.range(0, depth)
                .mapToObj(i -> "==")
                .collect(Collectors.joining());
        if (!prefix.isEmpty()) {
            prefix = prefix + ">";
        }

        sb.append(prefix).append(cow).append(System.lineSeparator());

        final List<Cow> children = cowRepository.findChildren(cow.getCowId());

        if (children.isEmpty()) {
            return sb;
        }

        children.forEach(child -> sb.append(getCowAsString(child, depth + 1)));

        return sb;
    }

}
