package Utility;

import org.apache.commons.math3.distribution.LogNormalDistribution;

public class RandomUtility {
    public static final LogNormalDistribution dist = new LogNormalDistribution();
    public static double getRandomLogNormal() {
//        double value = ((dist.sample() * 10 % 100) / 100);
//        return Math.floor(value * 100) / 100;
        return dist.sample();
    }
}
