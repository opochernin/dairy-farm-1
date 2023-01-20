package ua.pochernin.repository;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.Test;
import ua.pochernin.model.Cow;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CowRepositoryImplTest {

    private final CowRepositoryImpl cowRepository = new CowRepositoryImpl();

    @Test
    @SuppressWarnings("unchecked")
    void testFindById() throws IllegalAccessException {
        final List<Cow> cows = (List<Cow>) FieldUtils.readField(cowRepository, "cows", true);

        final Cow calve1 = new Cow();
        calve1.setCowId(1);
        calve1.setNickName("calve1");
        calve1.setParentCowId(0);
        cows.add(calve1);

        final Cow calve2 = new Cow();
        calve2.setCowId(2);
        calve2.setNickName("calve2");
        calve2.setParentCowId(0);
        cows.add(calve2);

        final Cow calve3 = new Cow();
        calve3.setCowId(3);
        calve3.setNickName("calve3");
        calve3.setParentCowId(1);
        cows.add(calve3);

        assertThat(cowRepository.findById(1)).isEqualTo(calve1);
        assertThat(cowRepository.findById(2)).isEqualTo(calve2);
        assertThat(cowRepository.findById(3)).isEqualTo(calve3);
    }

    @Test
    void testSave() {
        final Cow calve1 = new Cow();
        calve1.setCowId(1);
        calve1.setNickName("calve1");
        calve1.setParentCowId(0);
        cowRepository.save(calve1);

        final Cow calve2 = new Cow();
        calve2.setCowId(2);
        calve2.setNickName("calve2");
        calve2.setParentCowId(0);
        cowRepository.save(calve2);

        final Cow calve3 = new Cow();
        calve3.setCowId(3);
        calve3.setNickName("calve3");
        calve3.setParentCowId(1);
        cowRepository.save(calve3);

        assertThat(cowRepository.findById(1)).isEqualTo(calve1);
        assertThat(cowRepository.findById(2)).isEqualTo(calve2);
        assertThat(cowRepository.findById(3)).isEqualTo(calve3);
    }

    @Test
    void testFindChildren() {
        final Cow calve1 = new Cow();
        calve1.setCowId(1);
        calve1.setNickName("calve1");
        calve1.setParentCowId(0);
        cowRepository.save(calve1);

        final Cow calve2 = new Cow();
        calve2.setCowId(2);
        calve2.setNickName("calve2");
        calve2.setParentCowId(0);
        cowRepository.save(calve2);

        final List<Cow> children = cowRepository.findChildren(0);
        assertThat(children).hasSize(2);
        assertThat(children).contains(calve1, calve2);
    }
}
