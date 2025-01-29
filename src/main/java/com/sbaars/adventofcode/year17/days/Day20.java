package com.sbaars.adventofcode.year17.days;

import com.sbaars.adventofcode.year17.Day2017;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day20 extends Day2017 {
    private static class Vector {
        long x, y, z;

        Vector(long x, long y, long z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        long manhattan() {
            return Math.abs(x) + Math.abs(y) + Math.abs(z);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Vector v = (Vector) o;
            return x == v.x && y == v.y && z == v.z;
        }

        @Override
        public int hashCode() {
            return java.util.Objects.hash(x, y, z);
        }
    }

    private static class Particle {
        Vector p, v, a;
        int id;

        Particle(int id, Vector p, Vector v, Vector a) {
            this.id = id;
            this.p = p;
            this.v = v;
            this.a = a;
        }

        void update() {
            v.x += a.x;
            v.y += a.y;
            v.z += a.z;
            p.x += v.x;
            p.y += v.y;
            p.z += v.z;
        }
    }

    public Day20() {
        super(20);
    }

    public static void main(String[] args) {
        new Day20().printParts();
    }

    private List<Particle> parseParticles() {
        List<Particle> particles = new ArrayList<>();
        Pattern pattern = Pattern.compile("p=<(-?\\d+),(-?\\d+),(-?\\d+)>, v=<(-?\\d+),(-?\\d+),(-?\\d+)>, a=<(-?\\d+),(-?\\d+),(-?\\d+)>");

        String[] lines = dayStrings();
        for (int i = 0; i < lines.length; i++) {
            Matcher m = pattern.matcher(lines[i]);
            if (m.find()) {
                Vector p = new Vector(Long.parseLong(m.group(1)), Long.parseLong(m.group(2)), Long.parseLong(m.group(3)));
                Vector v = new Vector(Long.parseLong(m.group(4)), Long.parseLong(m.group(5)), Long.parseLong(m.group(6)));
                Vector a = new Vector(Long.parseLong(m.group(7)), Long.parseLong(m.group(8)), Long.parseLong(m.group(9)));
                particles.add(new Particle(i, p, v, a));
            }
        }
        return particles;
    }

    @Override
    public Object part1() {
        List<Particle> particles = parseParticles();

        // The particle with the smallest acceleration magnitude will stay closest in the long term
        // If accelerations are equal, compare velocities, then positions
        int closestParticle = 0;
        long minAccel = Long.MAX_VALUE;
        long minVel = Long.MAX_VALUE;
        long minPos = Long.MAX_VALUE;

        for (Particle p : particles) {
            long accel = p.a.manhattan();
            long vel = p.v.manhattan();
            long pos = p.p.manhattan();

            if (accel < minAccel || 
                (accel == minAccel && vel < minVel) ||
                (accel == minAccel && vel == minVel && pos < minPos)) {
                closestParticle = p.id;
                minAccel = accel;
                minVel = vel;
                minPos = pos;
            }
        }

        return closestParticle;
    }

    @Override
    public Object part2() {
        List<Particle> particles = parseParticles();
        int noCollisionCount = 0;
        int maxNoCollisionCount = 100;  // If no collisions for this many steps, assume we're done

        while (noCollisionCount < maxNoCollisionCount) {
            // Update all particles
            for (Particle p : particles) {
                p.update();
            }

            // Check for collisions
            java.util.Map<Vector, List<Particle>> positions = new java.util.HashMap<>();
            for (Particle p : particles) {
                positions.computeIfAbsent(p.p, k -> new ArrayList<>()).add(p);
            }

            // Remove collided particles
            boolean hadCollision = false;
            for (List<Particle> collided : positions.values()) {
                if (collided.size() > 1) {
                    particles.removeAll(collided);
                    hadCollision = true;
                }
            }

            if (hadCollision) {
                noCollisionCount = 0;
            } else {
                noCollisionCount++;
            }
        }

        return particles.size();
    }
}
