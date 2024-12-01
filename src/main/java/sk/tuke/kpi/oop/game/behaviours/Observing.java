package sk.tuke.kpi.oop.game.behaviours;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.messages.Topic;

import java.util.function.Predicate;

public class Observing<A extends Actor, T> implements Behaviour<A>{
    private A actor;
    private Topic<T> topic;
    private Predicate<T> predicate;
    private Behaviour<A> delegate;

    public Observing(Topic<T> topic, Predicate<T> predicate, Behaviour<A> delegate) {
        this.actor = null;
        this.topic = topic;
        this.predicate = predicate;
        this.delegate = delegate;
    }
    @Override
    public void setUp(A actor) {
        if(actor == null){
            return;
        }

        this.actor = actor;
        actor.getScene().getMessageBus().subscribe(this.topic, this::observing);
    }

    private void observing(T topic){
        if(!this.predicate.test(topic)){
            return;
        }
        else if(this.actor == null){
            return;
        }

        this.delegate.setUp(this.actor);
    }
}
