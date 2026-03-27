package org.jmolecules.ddd.types;
import java.util.Collection;

/**
 * Internal-facing trait to be implemented by {@link AggregateRoot}s that need to manage and expose
 * Domain Events to the infrastructure for deferred publication.

 * In Domain-Driven Design, the Aggregate Root acts as a consistency boundary. Changes within that
 * boundary often result in side effects that other parts of the system need to know about.
 * Standardizing how these events are stored and retrieved prevents infrastructure concerns
 * (like Repository implementations or Unit of Work patterns) from leaking into the domain logic.

 * This interface is typically implemented by a base class or the Aggregate Root itself. The domain
 * logic calls {@code registerEvent(...)} when a state change occurs. The infrastructure (e.g.,
 * a Spring Data Repository or a Transaction Interceptor) calls {@code getDomainEvents()}
 * after the transaction completes, publishes them, and finally calls {@code clearDomainEvents()}.
 * * @param <T> the type of the aggregate root this interface is associated with.
 * @author Amir Youssef
 * @see <a href="https://www.domainlanguage.com/ddd/reference/">Domain-Driven Design Reference (Evans) - Domain Events</a>
 * @see <a href="https://vaughnvernon.co/?p=838">Vaughn Vernon - Modeling Aggregates with Domain Events</a>
 */
public interface HasDomainEvents<T extends AggregateRoot<T, ?>, E > {

    /**
     * Registers a specialized event.
     * * @param event the event of type E.
     */
    void registerEvent(E event);

    /**
     * Returns the collection of specialized events.
     * * @return a collection of type E.
     */
    Collection<E> getDomainEvents();

    /**
     * Clears the events.
     */
    void clearDomainEvents();
}
