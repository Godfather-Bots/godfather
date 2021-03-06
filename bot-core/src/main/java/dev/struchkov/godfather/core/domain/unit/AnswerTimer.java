package dev.struchkov.godfather.core.domain.unit;

import dev.struchkov.godfather.context.domain.content.Message;
import dev.struchkov.godfather.context.service.usercode.CheckData;
import dev.struchkov.godfather.core.service.Accessibility;
import dev.struchkov.godfather.core.utils.TypeUnit;

import java.util.Set;
import java.util.regex.Pattern;

import static dev.struchkov.godfather.context.exception.UnitConfigException.unitConfigException;
import static dev.struchkov.haiti.utils.Inspector.isNotNull;

/**
 * Обработчик таймер, позволяющий отложить обработку других Unit-ов.
 *
 * @author upagge [08/07/2019]
 */
public class AnswerTimer<M extends Message> extends MainUnit {

    /**
     * Unit обработку которого необходимо отложить.
     */
    private final MainUnit unitAnswer;

    /**
     * Задержка обработки в секундах.
     */
    private final Integer timeDelaySec;

    /**
     * Время, через которое таймер будет удален в секундах.
     */
    private final Integer timeDeathSec;

    /**
     * Условие срабатывания отложенного Unit.
     */
    private final CheckData<M> checkLoop;

    private AnswerTimer(Builder<M> builder) {
        super(
                builder.name,
                builder.keyWords,
                builder.phrase,
                builder.pattern,
                builder.matchThreshold,
                builder.priority,
                null,
                builder.activeType,
                builder.accessibility,
                TypeUnit.TIMER
        );
        unitAnswer = builder.unitAnswer;
        timeDelaySec = builder.timeDelaySec;
        timeDeathSec = builder.timeDeathSec;
        checkLoop = builder.checkLoop;
    }

    public static <M extends Message> Builder<M> builder() {
        return new Builder<>();
    }

    public MainUnit getUnitAnswer() {
        return unitAnswer;
    }

    public Integer getTimeDelaySec() {
        return timeDelaySec;
    }

    public Integer getTimeDeathSec() {
        return timeDeathSec;
    }

    public CheckData<M> getCheckLoop() {
        return checkLoop;
    }

    public static final class Builder<M extends Message> {
        private String name;
        private MainUnit unitAnswer;
        private Integer timeDelaySec;
        private Integer timeDeathSec;
        private CheckData<M> checkLoop;
        private Set<String> keyWords;
        private String phrase;
        private Pattern pattern;
        private Integer matchThreshold;
        private Integer priority;
        private UnitActiveType activeType = UnitActiveType.AFTER;
        private Accessibility accessibility;

        private Builder() {
        }

        public Builder<M> name(String name) {
            this.name = name;
            return this;
        }

        public Builder<M> unitAnswer(MainUnit val) {
            unitAnswer = val;
            return this;
        }

        public Builder<M> timeDelaySec(Integer val) {
            timeDelaySec = val;
            return this;
        }

        public Builder<M> timeDeathSec(Integer val) {
            timeDeathSec = val;
            return this;
        }

        public Builder<M> checkLoop(CheckData<M> val) {
            checkLoop = val;
            return this;
        }

        public Builder<M> keyWords(Set<String> val) {
            keyWords = val;
            return this;
        }

        public Builder<M> phrase(String val) {
            phrase = val;
            return this;
        }

        public Builder<M> pattern(Pattern val) {
            pattern = val;
            return this;
        }

        public Builder<M> matchThreshold(Integer val) {
            matchThreshold = val;
            return this;
        }

        public Builder<M> priority(Integer val) {
            priority = val;
            return this;
        }

        public Builder accessibility(Accessibility val) {
            accessibility = val;
            return this;
        }

        public Builder<M> activeType(UnitActiveType val) {
            activeType = val;
            return this;
        }

        public AnswerTimer<M> build() {
            isNotNull(unitAnswer, unitConfigException("Необходимо указать юнит, обработка которого будет отложена."));
            return new AnswerTimer<>(this);
        }

    }

}
