package sk.tuke.kpi.oop.game.items;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.ActorContainer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Backpack implements ActorContainer<Collectible> {
    private List<Collectible> content;
    private String name;
    private int capacity;
    public Backpack(String name, int capacity) {
        content = new ArrayList<>();
        this.name = name;
        this.capacity = capacity;
    }

    @Override
    public int getCapacity() {
        return capacity;
    }

    @NotNull
    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<Collectible> getContent() {
        return List.copyOf(content);
    }

    @Override
    public int getSize() {
        return content.size();
    }

    @Override
    public void add(Collectible actor) {
        if(this.getSize() == this.getCapacity()) {
            throw new IllegalStateException(this.getName() + " is full");
        }

        this.content.add(actor);

    }

    @Override
    public void remove(Collectible actor) {
        if(this.getSize() == 0 || actor == null || !content.contains(actor)) {
            return;
        }

        this.content.remove(actor);
    }

    @NotNull
    @Override
    public Iterator<Collectible> iterator() {
        return this.content.iterator();
    }

    @Override
    public @Nullable Collectible peek() {
        return (!this.content.isEmpty()) ? this.content.get(this.content.size() - 1) : null;
    }

    @Override
    public void shift() {
        Collections.rotate(this.content, 1);
    }
}
