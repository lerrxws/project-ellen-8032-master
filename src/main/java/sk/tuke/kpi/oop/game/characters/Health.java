package sk.tuke.kpi.oop.game.characters;

import java.util.ArrayList;
import java.util.List;

public class Health {
    private int maxHealth;
    private int currentHealth;
    private List<FatigueEffect> effects;

    public Health(int currentHealth, int maxHealth) {
        this.maxHealth = maxHealth;
        this.currentHealth = currentHealth;
        effects = new ArrayList<>();
    }

    public Health(int maxHealth) {
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        effects = new ArrayList<>();
    }

    public int getMaxHealth() {
        return this.maxHealth;
    }

    public int getValue() {
        return this.currentHealth;
    }

    public void refill(int amount) {
        this.currentHealth = (this.currentHealth + amount > this.maxHealth) ? this.maxHealth : this.currentHealth + amount;
    }

    public void restore() {
        this.currentHealth = this.maxHealth;
    }

    public void drain(int amount) {
        if(this.currentHealth != 0){
            if(this. currentHealth - amount > 0){
                this.currentHealth = this.currentHealth - amount;
            }
            else {
                this.exhaust();
            }
        }
    }

    public void exhaust() {
        if(this.currentHealth != 0){
            this.currentHealth = 0;
            if(effects == null){
                return;
            }
            else{
                effects.forEach(FatigueEffect::apply);
            }
        }
    }

    @FunctionalInterface
    public interface FatigueEffect {
        void apply();
    }

    public void onFatigued(FatigueEffect effect){
        if(effect == null){
            return;
        }
        effects.add(effect);
    }
}
