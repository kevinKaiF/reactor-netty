/*
 * Copyright (c) 2011-2018 Pivotal Software Inc, All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package reactor.ipc.netty;

import java.util.Objects;
import java.util.concurrent.Callable;

import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

/**
 * An additional util's class which allows including external context into particular
 * publisher lifecycle
 *
 * @author Oleh Dokuka
 * @since 0.7.5
 */
abstract class PublisherContext {

	private PublisherContext() { }

	/**
	 * Incorporating external {@link Context} with given {@link Publisher<T>}
	 *
	 * @param publisher Nonnull Publisher instance
	 * @param context Nonnull Context instance
	 * @param <T> Type of Publisher's events
	 *
	 * @return Publisher instance incorporated with external context
	 */
	public static <T> Publisher<T> withContext(Publisher<T> publisher, Context context) {
		Objects.requireNonNull(publisher);
		Objects.requireNonNull(context);

		if (publisher instanceof Flux) {
			return ((Flux<T>) publisher).subscriberContext(context);
		}
		else if (publisher instanceof Mono) {
			return ((Mono<T>) publisher).subscriberContext(context);
		}
		else if (publisher instanceof Callable) {
			return publisher;
		}
		else {
			return Flux.from(publisher)
			           .subscriberContext(context);
		}
	}
}
