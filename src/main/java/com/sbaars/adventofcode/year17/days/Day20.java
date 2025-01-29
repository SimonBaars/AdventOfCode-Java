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

    @Override
    public Object part1() {
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
        return 0;
    }
}
