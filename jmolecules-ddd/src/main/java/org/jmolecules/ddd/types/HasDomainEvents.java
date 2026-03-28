/*
 * Copyright 2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jmolecules.ddd.types;

import org.jmolecules.stereotype.Stereotype;

import java.util.Collection;
import java.util.Collections;

/**
 * Interface to be implemented by {@link AggregateRoot}s that need to register and expose domain events for deferred
 * publication by the infrastructure.
 * <p>
 * In Domain-Driven Design, the Aggregate Root acts as a consistency boundary. Changes within that boundary often produce
 * side effects that other parts of the system need to know about. Standardizing how these events are stored and
 * retrieved keeps infrastructure concerns (such as {@link Repository} implementations or Unit-of-Work patterns) from
 * leaking into domain logic.
 * <p>
 * The domain logic calls {@link #registerEvent(Object)} when a state change occurs. The infrastructure (e.g., a
 * {@link Repository} or Transaction Interceptor) calls {@link #getDomainEvents()} after the transaction commits,
 * publishes the events, and then calls {@link #clearDomainEvents()}.
 *
 * @param <T> the concrete {@link AggregateRoot} type.
 * @param <E> the domain event base type.
 * @author Amir Youssef
 * @see <a href="https://www.domainlanguage.com/ddd/reference/">Domain-Driven Design Reference (Evans) - Domain
 * Events</a>
 * @see <a href="https://vaughnvernon.co/?p=838">Vaughn Vernon - Modeling Aggregates with Domain Events</a>
 */
@Stereotype(priority = 20)
public interface HasDomainEvents<T extends AggregateRoot<T, ?>, E> {

    /**
     * Registers the given domain event for later publication.
     *
     * @param event must not be {@literal null}.
     */
    default void registerEvent(E event) {
    }

    /**
     * Returns all domain events that have been registered since the last {@link #clearDomainEvents()} call.
     *
     * @return a collection of registered events, never {@literal null}.
     */
    default Collection<E> getDomainEvents() {
        return Collections.emptyList();
    }

    /**
     * Clears all domain events currently held. Typically invoked by the infrastructure after events have been published.
     *
     */
    default void clearDomainEvents() {
    }

}
