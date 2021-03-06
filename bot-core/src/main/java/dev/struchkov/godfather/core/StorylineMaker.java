package dev.struchkov.godfather.core;

import dev.struchkov.godfather.context.domain.UnitDefinition;
import dev.struchkov.godfather.context.domain.annotation.Unit;
import dev.struchkov.godfather.context.exception.UnitConfigException;
import dev.struchkov.godfather.core.domain.unit.LazyUnit;
import dev.struchkov.godfather.core.domain.unit.MainUnit;
import dev.struchkov.haiti.utils.Inspector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static dev.struchkov.godfather.context.exception.UnitConfigException.unitConfigException;

public class StorylineMaker {

    private static final Logger log = LoggerFactory.getLogger(StorylineMaker.class);

    private final List<Object> configurations = new ArrayList<>();

    private final Map<String, UnitDefinition> unitDefinitions = new HashMap<>();
    private final Map<String, MainUnit> unitMap = new HashMap<>();
    private final Set<String> lazyUnits = new HashSet<>();
    private final Set<String> mainUnits = new HashSet<>();

    public StorylineMaker(List<Object> unitConfigurations) {
        this.configurations.addAll(unitConfigurations);
    }

    public Map<String, UnitDefinition> getUnitDefinitions() {
        return unitDefinitions;
    }

    public Map<String, MainUnit> getUnitMap() {
        return unitMap;
    }

    public StoryLine createStoryLine() {
        generateUnitDefinitions();
        try {
            createUnitMap();
            createLazy();
            final Set<MainUnit> mainUnit = getMainUnit();
            return new StoryLine(mainUnit, unitMap);
        } catch (IllegalAccessException | InvocationTargetException e) {
            log.error(e.getMessage(), e);
        }
        throw new UnitConfigException("???????????? ???????????????????? StoryLine");
    }

    private Set<MainUnit> getMainUnit() {
        Inspector.isNotEmpty(mainUnits, unitConfigException("???? ?????????? ???? ???????? mainUnit. ???????????????????? ???????? ???? ?????? ???????????? Unit ???????? mainUnit"));
        return mainUnits.stream()
                .map(unitMap::get)
                .collect(Collectors.toSet());
    }

    private void createLazy() throws IllegalAccessException, InvocationTargetException {
        final List<UnitDefinition> lazyDefinitions = unitDefinitions.values().stream()
                .filter(UnitDefinition::isLazy)
                .toList();
        for (UnitDefinition lazyDefinition : lazyDefinitions) {
            final MainUnit lazyUnit = createUnit(lazyDefinition);
            unitMap.put(lazyDefinition.getName(), lazyUnit);
            for (String dependentUnit : lazyDefinition.getDependentUnits()) {
                final MainUnit mainUnit = unitMap.get(dependentUnit);
                final Set<MainUnit> nextUnits = mainUnit.getNextUnits();
                if (nextUnits != null) {
                    nextUnits.add(lazyUnit);
                }
            }
        }
    }

    private void createUnitMap() throws IllegalAccessException, InvocationTargetException {
        for (UnitDefinition unitDefinition : unitDefinitions.values()) {
            if (!unitMap.containsKey(unitDefinition.getName())) {
                final Set<String> nextUnitNames = unitDefinition.getNextUnitNames();
                if (nextUnitNames.isEmpty() || unitMap.keySet().containsAll(nextUnitNames)) {
                    createUnit(unitDefinition);
                } else if (unitDefinition.isLazy()) {
                    createLazyUnit(unitDefinition);
                }
            }
        }
    }

    private void createLazyUnit(UnitDefinition unitDefinition) {
        final String unitName = unitDefinition.getName();
        unitMap.put(unitName, LazyUnit.create(unitName, unitDefinition));
    }

    private MainUnit createUnit(UnitDefinition unitDefinition) throws IllegalAccessException, InvocationTargetException {

        final Object objectConfig = unitDefinition.getObjectConfig();
        final String currentUnitName = unitDefinition.getName();
        final Method method = unitDefinition.getMethod();
        final Object[] nextUnits = Arrays.stream(method.getParameters())
                .filter(parameter -> parameter.isAnnotationPresent(Unit.class))
                .map(parameter -> parameter.getAnnotation(Unit.class))
                .map(Unit::value)
                .map(unitMap::get)
                .toArray();
        MainUnit newUnit = (MainUnit) method.invoke(objectConfig, nextUnits);
        newUnit.setName(currentUnitName);

        unitMap.put(currentUnitName, newUnit);

        final Set<String> dependentUnitsName = unitDefinition.getDependentUnits();
        dependentUnitsName.removeAll(lazyUnits);
        for (String dependentUnitName : dependentUnitsName) {
            final Set<String> dependentNextUnitNames = unitDefinitions.get(dependentUnitName).getNextUnitNames();
            if (unitMap.keySet().containsAll(dependentNextUnitNames)) {
                createUnit(unitDefinitions.get(dependentUnitName));
            }
        }

        return newUnit;
    }

    private void generateUnitDefinitions() {
        final Map<String, Set<String>> dependentUnits = new HashMap<>();

        for (Object config : configurations) {
            final Class<?> classUnitConfig = config.getClass();

            for (Method method : classUnitConfig.getDeclaredMethods()) {
                if (method.isAnnotationPresent(Unit.class)) {
                    final Unit unitConfig = method.getAnnotation(Unit.class);

                    final String unitName = unitConfig.value();

                    final UnitDefinition unitDefinition = new UnitDefinition();
                    unitDefinition.setName(unitName);
                    unitDefinition.setMethod(method);
                    unitDefinition.setObjectConfig(config);
                    unitDefinition.setLazy(unitConfig.lazy());

                    if (unitConfig.lazy()) {
                        lazyUnits.add(unitName);
                    }
                    if (unitConfig.mainUnit()) {
                        mainUnits.add(unitName);
                    }

                    final Parameter[] nextUnits = method.getParameters();
                    if (nextUnits.length > 0) {
                        for (Parameter nextUnit : nextUnits) {
                            if (nextUnit.isAnnotationPresent(Unit.class)) {
                                final Unit nextUnitConfig = nextUnit.getAnnotation(Unit.class);
                                final String nextUnitName = nextUnitConfig.value();
                                unitDefinition.setNextUnitName(nextUnitName);
                                dependentUnits.computeIfAbsent(nextUnitName, k -> new HashSet<>());
                                dependentUnits.get(nextUnitName).add(unitName);
                            }
                        }
                    }

                    unitDefinitions.put(unitDefinition.getName(), unitDefinition);
                }
            }
        }

        for (Map.Entry<String, Set<String>> entry : dependentUnits.entrySet()) {
            final UnitDefinition unitDefinition = unitDefinitions.get(entry.getKey());
            if (unitDefinition != null) {
                unitDefinition.setDependentUnits(entry.getValue());
            } else {
                throw new UnitConfigException("???????????? ?????????? ????????????. ???????????????? ?? ?????????????????? {0} ??????????. ???????????????? ???? ???? ?????????????? ?????????? ???????????????????????? ?????? ?????????? ??????????.", entry.getKey());
            }
        }
    }

}
