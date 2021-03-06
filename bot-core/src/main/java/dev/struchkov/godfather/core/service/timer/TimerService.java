package dev.struchkov.godfather.core.service.timer;

import dev.struchkov.godfather.core.domain.Timer;

import java.util.List;

public interface TimerService {

    List<Timer> getTimerActive();

    Integer add(Timer timer);

    void remove(Integer id);

    void edit(Integer id, Timer timer);

}
