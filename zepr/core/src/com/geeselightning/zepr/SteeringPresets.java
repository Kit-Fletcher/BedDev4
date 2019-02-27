package com.geeselightning.zepr;

import com.badlogic.gdx.ai.steer.behaviors.*;
import com.badlogic.gdx.ai.steer.limiters.LinearAccelerationLimiter;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

/**
 * Presets for AI movement implementing wander, seek, and arrive functionality.
 * Look in gdx-AI steering behaviour documentation for more information.
 * #changed:   Added this class to the game
 */
public class SteeringPresets {
    /**
     * Wander presets for AI movement
     * @param steeringEntity the character to move
     * @return wander preset
     */
    public static Wander<Vector2> getWander(Character steeringEntity) {
        Wander<Vector2> wander = new Wander<>(steeringEntity)
                .setFaceEnabled(false)
                .setLimiter(new LinearAccelerationLimiter(1))
                .setWanderOffset(3)
                .setWanderOrientation(5)
                .setWanderRadius(0.01f)
                .setWanderRate(MathUtils.PI2 * 4);
        return wander;
    }

    /**
     * Seek functionality for enemy movement
     * @param seeker the enemy character that will move
     * @param target the destination point
     * @return seek preset
     */
    public static Seek<Vector2> getSeek(Character seeker, Character target) {
        Seek<Vector2> seek = new Seek<>(seeker, target);
        return seek;
    }

    /**
     * Arrive functionality for enemy movement
     * @param runner the enemy character that will move
     * @param target the destination point
     * @return arrive preset
     */
    public static Arrive<Vector2> getArrive(Character runner, Character target) {
        Arrive<Vector2> arrive = new Arrive<>(runner, target)
                .setTimeToTarget(0.1f)
                .setArrivalTolerance(7f)
                .setDecelerationRadius(10f);
        return arrive;
    }
}
