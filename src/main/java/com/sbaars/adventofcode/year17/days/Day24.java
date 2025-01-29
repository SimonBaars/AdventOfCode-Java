package com.sbaars.adventofcode.year17.days;

import com.sbaars.adventofcode.year17.Day2017;
import java.util.*;
import java.util.stream.Collectors;

public class Day24 extends Day2017 {
    private record Component(int port1, int port2) {
        boolean hasPort(int port) {
            return port1 == port || port2 == port;
        }

        int getOtherPort(int port) {
            return port == port1 ? port2 : port1;
        }

        int strength() {
            return port1 + port2;
        }
    }

    private record Bridge(List<Component> components, int lastPort) {
        int strength() {
            return components.stream().mapToInt(Component::strength).sum();
        }

        int length() {
            return components.size();
        }
    }

    public Day24() {
        super(24);
    }

    public static void main(String[] args) {
        new Day24().printParts();
    }

    private List<Component> parseComponents() {
        return Arrays.stream(dayStrings())
            .map(line -> {
                String[] parts = line.split("/");
                return new Component(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
            })
            .collect(Collectors.toList());
    }

    private List<Bridge> buildBridges(Bridge currentBridge, List<Component> availableComponents) {
        List<Bridge> bridges = new ArrayList<>();
        bridges.add(currentBridge);

        for (Component component : availableComponents) {
            if (component.hasPort(currentBridge.lastPort())) {
                List<Component> remainingComponents = new ArrayList<>(availableComponents);
                remainingComponents.remove(component);

                List<Component> newBridgeComponents = new ArrayList<>(currentBridge.components());
                newBridgeComponents.add(component);

                Bridge newBridge = new Bridge(newBridgeComponents, component.getOtherPort(currentBridge.lastPort()));
                bridges.addAll(buildBridges(newBridge, remainingComponents));
            }
        }

        return bridges;
    }

    @Override
    public Object part1() {
        List<Component> components = parseComponents();
        List<Bridge> bridges = buildBridges(new Bridge(new ArrayList<>(), 0), components);
        return bridges.stream()
            .mapToInt(Bridge::strength)
            .max()
            .orElse(0);
    }

    @Override
    public Object part2() {
        List<Component> components = parseComponents();
        List<Bridge> bridges = buildBridges(new Bridge(new ArrayList<>(), 0), components);
        
        int maxLength = bridges.stream()
            .mapToInt(Bridge::length)
            .max()
            .orElse(0);

        return bridges.stream()
            .filter(b -> b.length() == maxLength)
            .mapToInt(Bridge::strength)
            .max()
            .orElse(0);
    }
}
