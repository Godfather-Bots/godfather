package dev.struchkov.godfather.core;

import dev.struchkov.godfather.core.domain.unit.MainUnit;
import dev.struchkov.haiti.utils.Inspector;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class StoryLine {

    private MainUnit defaultUnit;
    private final Set<MainUnit> startingUnits = new HashSet<>();
    private final Map<String, MainUnit> units = new HashMap<>();

    public StoryLine(Set<MainUnit> startingUnits, Map<String, MainUnit> units) {
        this.startingUnits.addAll(startingUnits);
        this.units.putAll(units);
    }

    public void setDefaultUnit(MainUnit defaultUnit) {
        this.defaultUnit = defaultUnit;
    }

    public Optional<MainUnit> getDefaultUnit() {
        return Optional.ofNullable(defaultUnit);
    }

    /**
     * Получить юнит по названию.
     *
     * @param unitName Название юнита.
     */
    public Optional<MainUnit> getUnit(String unitName) {
        Inspector.isNotNull(unitName);
        return Optional.ofNullable(units.get(unitName));
    }

    public Set<MainUnit> getStartingUnits() {
        return startingUnits;
    }

    //TODO [22.06.2022]: Временное решение ленивой связки юнитов, пока не будет реализован нормальный механизм.
    public void link(@NotNull String firstName, @NotNull String secondName) {
        Inspector.isNotNull(firstName, secondName);
        final MainUnit firstUnit = units.get(firstName);
        final MainUnit secondUnit = units.get(secondName);
        Inspector.isNotNull(firstUnit, secondUnit);
        firstUnit.getNextUnits().add(secondUnit);
    }

}
