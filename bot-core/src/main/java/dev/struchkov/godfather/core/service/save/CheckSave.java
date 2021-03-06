package dev.struchkov.godfather.core.service.save;

import dev.struchkov.godfather.context.domain.content.Message;
import dev.struchkov.godfather.core.domain.unit.MainUnit;

/**
 * TODO: Добавить описание интерфейса.
 *
 * @author upagge [04/08/2019]
 */
@FunctionalInterface
public interface CheckSave<D extends Message> {

    MainUnit check(D content);

}
