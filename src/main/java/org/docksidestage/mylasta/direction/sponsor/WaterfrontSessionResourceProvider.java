/*
 * Copyright 2015-2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.docksidestage.mylasta.direction.sponsor;

import org.dbflute.optional.OptionalThing;
import org.lastaflute.web.servlet.session.SessionResourceProvider;
import org.lastaflute.web.servlet.session.SessionSharedStorage;

/**
 * @author jflute
 */
public class WaterfrontSessionResourceProvider implements SessionResourceProvider {

    @Override
    public SessionSharedStorage provideSharedStorage() {
        return new SessionSharedStorage() { // #making: example by Cookie

            @Override
            public <ATTRIBUTE> OptionalThing<ATTRIBUTE> getAttribute(String key, Class<ATTRIBUTE> attributeType) {
                return OptionalThing.empty();
            }

            @Override
            public void setAttribute(String key, Object value) {
            }

            @Override
            public void removeAttribute(String key) {
            }

            @Override
            public void invalidate() {
            }
        };
    }
}
